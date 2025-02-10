package com.lmorda.homework.data

import com.lmorda.homework.data.mapper.GithubRepoMapper
import com.lmorda.homework.data.model.mockApiData
import com.lmorda.homework.domain.model.mockDomainData
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DataRepositoryImplTest {

    private val mockApiService = mockk<ApiService>()
    private val githubRepoMapper = mockk<GithubRepoMapper> {
        every { map(githubReposDto = mockApiData) } returns mockDomainData
        every { map(githubRepoDto = mockApiData.items[0]) } returns mockDomainData[0]
    }
    private val dataRepository = DataRepositoryImpl(
        apiService = mockApiService,
        mapper = githubRepoMapper,
    )

    @Test
    fun `getRepos should return mapped repos`() = runTest {
        coEvery {
            mockApiService.searchRepositories(
                page = 1,
                perPage = PER_PAGE,
                query = QUERY,
                order = ORDER,
                sort = SORT,
            )
        } returns mockApiData
        val repos = dataRepository.getRepos(page = 1)
        assertEquals(mockDomainData, repos)
    }

    @Test
    fun `getRepo should return mapped repo`() = runTest {
        coEvery { mockApiService.getRepo(id = 0) } returns mockApiData.items[0]
        val repos = dataRepository.getRepo(id = 0)
        assertEquals(mockDomainData[0], repos)
    }
}
