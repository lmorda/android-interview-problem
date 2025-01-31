package com.lmorda.homework.ui.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.lmorda.homework.R
import com.lmorda.homework.domain.model.GithubRepo
import com.lmorda.homework.domain.model.mockDomainData
import com.lmorda.homework.ui.shared.RepositoryStats
import com.lmorda.homework.ui.theme.DayAndNightPreview
import com.lmorda.homework.ui.theme.HomeworkTheme
import com.lmorda.homework.ui.theme.PaginationEffect
import com.lmorda.homework.ui.theme.defaultSize
import com.lmorda.homework.ui.theme.largeSize
import com.lmorda.homework.ui.theme.mediumSize
import com.lmorda.homework.ui.theme.smallSize
import com.lmorda.homework.ui.theme.topAppBarColors

@Composable
fun ExploreScreenRoute(
    viewModel: ExploreViewModel,
    onNavigateToDetails: (Long) -> Unit,
) {
    val state = viewModel.state.collectAsState().value
    ExploreScreen(
        state = state,
        onNextPage = viewModel::getNextPage,
        onRefresh = viewModel::onRefresh,
        onNavigateToDetails = onNavigateToDetails,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ExploreScreen(
    state: ExploreContract.State,
    onNextPage: () -> Unit,
    onRefresh: () -> Unit,
    onNavigateToDetails: (Long) -> Unit,
) {
    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = topAppBarColors(),
                title = {
                    ExploreTitle()
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ExploreContent(
                state = state,
                listState = listState,
                onNextPage = onNextPage,
                onNavigateToDetails = onNavigateToDetails,
            )
        }
    }
}

@Composable
private fun ExploreTitle() {
    Text(
        text = stringResource(id = R.string.explore_title),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onPrimary,
    )
}

@Composable
private fun ExploreContent(
    state: ExploreContract.State,
    listState: LazyListState,
    onNextPage: () -> Unit,
    onNavigateToDetails: (Long) -> Unit,
) {
    when {
        state.exception != null -> ExploreLoadingError()
        state.isFirstLoad -> ExploreProgressIndicator()
        else -> ExploreList(
            listState = listState,
            state = state,
            onNavigateToDetails = onNavigateToDetails,
        )
    }
    if (!state.isLoading()) {
        PaginationEffect(
            listState = listState,
            buffer = 5, // Load more when 5 items away from the end
            onLoadMore = onNextPage,
        )
    }
}

@Composable
private fun ExploreList(
    listState: LazyListState,
    state: ExploreContract.State,
    onNavigateToDetails: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
    ) {
        items(state.githubRepos) { details ->
            ExploreItem(details = details, onNavigateToDetails = onNavigateToDetails)
        }
        if (state.isFetchingNextPage) {
            item { ExploreNextPageIndicator() }
        }
    }
}

@Composable
private fun ExploreProgressIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.loading)
        )
        val progress by animateLottieCompositionAsState(
            composition = composition
        )
        LottieAnimation(
            composition = composition,
            progress = { progress },
        )
    }
}

@Composable
private fun ExploreNextPageIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(mediumSize),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(width = largeSize),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
private fun ExploreLoadingError() {
    Box(modifier = Modifier.padding(defaultSize)) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.loading_error)
        )
        val progress by animateLottieCompositionAsState(
            composition = composition
        )
        Text(
            text = stringResource(id = R.string.list_error),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        LottieAnimation(
            composition = composition,
            progress = { progress },
        )
    }
}

@Composable
private fun ExploreItem(details: GithubRepo, onNavigateToDetails: (Long) -> Unit) {
    Column(
        modifier = Modifier
            .padding(all = defaultSize)
            .clickable {
                onNavigateToDetails(details.id)
            }
    ) {
        ExploreItemTitle(details = details)

        Text(
            modifier = Modifier.padding(top = smallSize),
            text = details.description.orEmpty(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 3,
        )

        RepositoryStats(details)
    }
    HorizontalDivider(
        color = MaterialTheme.colorScheme.outlineVariant,
        thickness = 1.dp,
    )
}

@Composable
private fun ExploreItemTitle(details: GithubRepo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                modifier = Modifier
                    .size(size = largeSize)
                    .clip(shape = CircleShape),
                model = details.owner.avatarUrl,
                placeholder = painterResource(id = R.mipmap.ic_launcher_foreground),
                error = painterResource(id = R.mipmap.ic_launcher_foreground),
                contentDescription = "avatar",
            )
            Text(
                modifier = Modifier.padding(start = mediumSize),
                text = details.owner.login,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
            )
        }
        Text(
            modifier = Modifier.padding(top = smallSize),
            text = details.name,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
@DayAndNightPreview
private fun ExploreScreenPreview() {
    HomeworkTheme {
        ExploreScreen(
            state = ExploreContract.State(
                githubRepos = mockDomainData,
            ),
            onNextPage = {},
            onRefresh = {},
            onNavigateToDetails = {},
        )
    }
}
