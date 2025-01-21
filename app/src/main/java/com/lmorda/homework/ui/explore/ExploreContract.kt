package com.lmorda.homework.ui.explore

import com.lmorda.homework.domain.model.GithubRepo

interface ExploreContract {

    data class State(
        val isFirstLoad: Boolean = false,
        val isRefreshing: Boolean = false,
        val isFetchingNextPage: Boolean = false,
        val page: Int = 0,
        val githubRepos: List<GithubRepo> = emptyList(),
        val exception: Throwable? = null,
    ) {

        fun isLoading() = isFirstLoad || isRefreshing || isFetchingNextPage

        fun hideLoading() = copy(
            isFirstLoad = false,
            isRefreshing = false,
            isFetchingNextPage = false,
        )
    }
}
