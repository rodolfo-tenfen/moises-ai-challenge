package tenfen.rodolfo.moisesaichallenge.presentation.songs.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import kotlin.math.roundToInt
import org.koin.androidx.compose.koinViewModel
import tenfen.rodolfo.domain.music.entity.Album
import tenfen.rodolfo.moisesaichallenge.presentation.song.item.SongItem
import tenfen.rodolfo.moisesaichallenge.presentation.songs.viewmodel.SongsUiState
import tenfen.rodolfo.moisesaichallenge.presentation.songs.viewmodel.SongsUiState.Content as ContentState
import tenfen.rodolfo.moisesaichallenge.presentation.songs.viewmodel.SongsUiState.Empty as EmptyState
import tenfen.rodolfo.moisesaichallenge.presentation.songs.viewmodel.SongsUiState.Error as ErrorState
import tenfen.rodolfo.moisesaichallenge.presentation.songs.viewmodel.SongsUiState.Initial as InitialState
import tenfen.rodolfo.moisesaichallenge.presentation.songs.viewmodel.SongsViewModel
import tenfen.rodolfo.moisesaichallenge.presentation.theme.MoisesAIChallengeTheme

private const val TAG = "[SongsScreen]"

@Composable
internal fun SongsScreen(
    onViewAlbumClicked: (Album) -> Unit,
    viewModel: SongsViewModel = koinViewModel(),
) {
    // TODO BackHandler { isSearchInputVisible = false }

    LaunchedEffect(Unit) {
        viewModel.start()
    }

    val state by viewModel.uiState.collectAsState()

    SongsScreenView(
        state,
        viewModel::onQueryChanged,
        viewModel::onSearchTriggered,
        onViewAlbumClicked,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SongsScreenView(
    state: SongsUiState,
    onQueryChanged: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    onViewAlbumClicked: (Album) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val isCollapsed by remember { derivedStateOf { scrollBehavior.state.isCollapsed } }

    LaunchedEffect(isCollapsed) {
        if (isCollapsed) {
            focusManager.clearFocus()
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CollapsingSearchAppBar(
                scrollBehavior,
                state.let { it as? ContentState }?.query ?: "",
                onQueryChanged = onQueryChanged,
                onSearchTriggered = onSearchTriggered,
                onSearchClicked = { scrollBehavior.state.heightOffset = 0f },
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            state.also { Log.d(TAG, "SongsScreenView: state=$state") }
            when (state) {
                InitialState, EmptyState -> Unit
                is ContentState ->
                    SongsScreenPopulatedView(
                        state,
                        onViewAlbumClicked,
                        modifier = Modifier.fillMaxSize(),
                    )
                ErrorState -> ErrorView()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CollapsingSearchAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CollapsingColumn(
        scrollBehavior,
        fixed = { AppBar(scrollBehavior, onSearchClicked) },
        collapsible = {
            SearchInput(
                query,
                onQueryChanged = onQueryChanged,
                onSearchTriggered = onSearchTriggered,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            )
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CollapsingColumn(
    scrollBehavior: TopAppBarScrollBehavior,
    fixed: @Composable () -> Unit,
    collapsible: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Layout(
        content = {
            fixed()
            collapsible()
        },
        modifier = modifier,
    ) { measurables, constraints ->
        val child1 = measurables[0].measure(constraints)
        val child2 = measurables[1].measure(constraints)

        val height1 = child1.height
        val height2 = child2.height

        val scrollOffsetLimit = -height2.toFloat()
        if (scrollBehavior.state.heightOffsetLimit != scrollOffsetLimit) {
            scrollBehavior.state.heightOffsetLimit = scrollOffsetLimit
        }

        val currentScrollOffset = scrollBehavior.state.heightOffset.roundToInt()

        val currentLayoutHeight = (height1 + height2 + currentScrollOffset).coerceAtLeast(height1)

        layout(constraints.maxWidth, currentLayoutHeight) {
            child2.placeRelative(x = 0, y = height1 + currentScrollOffset)

            child1.placeRelative(x = 0, y = 0)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(scrollBehavior: TopAppBarScrollBehavior, onSearchClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                "Songs", // FIXME
                style = MaterialTheme.typography.titleLarge,
            )
        },
        actions = {
            AnimatedVisibility(
                visible = scrollBehavior.state.isCollapsed,
                enter = slideInVertically { it },
                exit = slideOutVertically { it },
            ) {
                IconButton(onClick = onSearchClicked) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search", // FIXME
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
    )
}

@Composable
private fun LoadingView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

// TODO
@Composable
private fun ErrorView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Error") // FIXME
    }
}

@Composable
private fun SongsScreenPopulatedView(
    state: ContentState,
    onViewAlbumClicked: (Album) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagedSongs = state.songs.collectAsLazyPagingItems()

    val isEmpty = pagedSongs.loadState.refresh is LoadState.NotLoading && pagedSongs.itemCount == 0

    if (isEmpty) return

    var lastLoadedQuery by remember { mutableStateOf("") }

    LaunchedEffect(pagedSongs.loadState.refresh) {
        if (pagedSongs.loadState.refresh is LoadState.NotLoading) {
            lastLoadedQuery = state.query
        }
    }

    if (pagedSongs.loadState.refresh is LoadState.Loading && state.isLoading)
        return LoadingView()

    val isRefreshing = pagedSongs.loadState.refresh is LoadState.Loading &&
        state.query == lastLoadedQuery

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = pagedSongs::refresh,
        modifier = modifier,
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(pagedSongs.itemCount) { i ->
                SongItem(pagedSongs[i] ?: return@items, onViewAlbumClicked = onViewAlbumClicked)
            }

            footer(pagedSongs.loadState.append)
        }
    }
}

private fun LazyListScope.footer(appendState: LoadState) = when (appendState) {
    is LoadState.Loading ->
        item {
            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
    is LoadState.Error -> Unit // TODO
    // item {
    //     ErrorRow(
    //         message = appendState.error.localizedMessage ?: "Error loading more",
    //         onRetry = { pagingItems.retry() },
    //     )
    // }
    else -> Unit
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun AppBarPreview() {
    MoisesAIChallengeTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AppBar(
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                onSearchClicked = {},
            )
        }
    }
}

private val TopAppBarState.isCollapsed get() = collapsedFraction > 0.5f
