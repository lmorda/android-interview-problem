package com.lmorda.homework.explore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.lmorda.homework.domain.DataRepository
import com.lmorda.homework.domain.model.mockDomainData
import com.lmorda.homework.ui.explore.ExploreContract.State
import com.lmorda.homework.ui.explore.ExploreViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExploreViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private val repository: DataRepository = mockk()
    private lateinit var viewModel: ExploreViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN first page fetch returns mock data WHEN viewmodel is initialized THEN first page of mock data is displayed`() =
        runTest {
            coEvery { repository.getRepos(page = 1) } returns mockDomainData.subList(0, 1)
            viewModel = ExploreViewModel(dataRepository = repository)
            viewModel.state.test {
                assertEquals(State(isFirstLoad = true), awaitItem())
                assertEquals(
                    State(page = 1, githubRepos = mockDomainData.subList(0, 1)),
                    awaitItem()
                )
            }
        }

    @Test
    fun `GIVEN viewmodel is initialized WHEN refresh is called THEN first page of mock data is fetched again and displayed`() =
        runTest {
            coEvery { repository.getRepos(page = 1) } returns mockDomainData.subList(0, 1)
            viewModel = ExploreViewModel(dataRepository = repository)
            viewModel.state.test {
                assertEquals(State(isFirstLoad = true), awaitItem())
                assertEquals(
                    State(page = 1, githubRepos = mockDomainData.subList(0, 1)),
                    awaitItem()
                )
            }
            viewModel.onRefresh()
            viewModel.state.test {
                assertEquals(State(isRefreshing = true), awaitItem())
                assertEquals(
                    State(page = 1, githubRepos = mockDomainData.subList(0, 1)),
                    awaitItem()
                )
            }
        }

    @Test
    fun `GIVEN fetch throws exception WHEN fetch is called from viewmodel THEN exception is caught and displayed`() =
        runTest {
            val mockException = Exception("Mock Exception")
            coEvery { repository.getRepos(page = 1) } throws mockException
            viewModel = ExploreViewModel(dataRepository = repository)
            viewModel.state.test {
                assertEquals(State(isFirstLoad = true), awaitItem())
                assertEquals(State(exception = mockException), awaitItem())
            }
        }
}
