package com.lmorda.homework.ui.details

import com.lmorda.homework.domain.model.GithubRepo

sealed interface DetailsContract {

    data class State(
        val githubRepo: GithubRepo? = null,
        val exception: Exception? = null,
    )
}
