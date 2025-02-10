package com.lmorda.homework.explore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.lmorda.homework.domain.DataRepository
import com.lmorda.homework.domain.model.mockDomainData
import com.lmorda.homework.ui.details.DetailsContract
import com.lmorda.homework.ui.details.DetailsViewModel
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
class DetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private val repository: DataRepository = mockk()
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN fetch returns mock details WHEN viewmodel is initialized THEN details are displayed`() = runTest {
        coEvery { repository.getRepo(id = 123L) } returns mockDomainData[0]
        val savedStateHandle = SavedStateHandle(mapOf("id" to 123L))
        viewModel = DetailsViewModel(savedStateHandle = savedStateHandle, dataRepository = repository)
        viewModel.state.test {
            assertEquals(DetailsContract.State(githubRepo = null), awaitItem())
            assertEquals(DetailsContract.State(githubRepo = mockDomainData[0]), awaitItem())
        }
    }
}
