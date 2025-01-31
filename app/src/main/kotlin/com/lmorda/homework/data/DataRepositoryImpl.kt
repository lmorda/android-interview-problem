package com.lmorda.homework.data

import com.lmorda.homework.data.mapper.GithubRepoMapper
import com.lmorda.homework.domain.DataRepository
import javax.inject.Inject
import javax.inject.Singleton

const val PER_PAGE = 30
const val QUERY = "android"
const val ORDER = "desc"
const val SORT = "stars"

@Singleton
class DataRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: GithubRepoMapper,
) : DataRepository {

    override suspend fun getRepos(page: Int) =
        mapper.map(
            githubReposDto = apiService.searchRepositories(
                page = page,
                perPage = PER_PAGE,
                query = QUERY,
                order = ORDER,
                sort = SORT,
            )
        )

    override suspend fun getRepo(id: Long) =
        mapper.map(
            githubRepoDto = apiService.getRepo(id = id)
        )
}
