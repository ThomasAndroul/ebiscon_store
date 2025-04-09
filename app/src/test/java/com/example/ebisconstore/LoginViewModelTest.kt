package com.example.ebisconstore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import com.example.ebisconstore.auth.FakeStoreApi
import com.example.ebisconstore.auth.LoginRequest
import com.example.ebisconstore.auth.LoginResponse
import com.example.ebisconstore.auth.LoginViewModel
import com.example.ebisconstore.auth.UserManager
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.notNull
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private lateinit var userManager: UserManager
    private lateinit var apiService: FakeStoreApi
    private lateinit var viewModel: LoginViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userManager = mock()
        apiService = mock()

        runTest {
            whenever(userManager.tokenFlow).thenReturn(flowOf(null))
        }

        viewModel = LoginViewModel(userManager, apiService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading false and token null when no token stored`() = runTest {
        whenever(userManager.tokenFlow).thenReturn(flowOf(null))

        val viewModel = LoginViewModel(userManager, apiService)
        advanceUntilIdle()

        val state = viewModel.loginState.value
        assertFalse(state.loading)
        assertNull(state.token)
        assertNull(state.error)
    }

    @Test
    fun `login success sets non-null token and calls onSuccess`() = runTest {
        whenever(apiService.login(any())).thenReturn(LoginResponse(token = "user_token"))
        whenever(userManager.tokenFlow).thenReturn(flowOf(null))

        val viewModel = LoginViewModel(userManager, apiService)

        var successCalled = false

        viewModel.login("johnd", "m38rmF$", {
            successCalled = true
        }, {
            fail("onError should not be called")
        })

        advanceUntilIdle()

        val state = viewModel.loginState.value
        assertFalse(state.loading)
        assertNotNull(state.token)
        assertTrue(successCalled)
    }


    @Test
    fun `login failure sets error and calls onError`() = runTest {
        whenever(apiService.login(any())).thenThrow(RuntimeException("Invalid credentials"))
        whenever(userManager.tokenFlow).thenReturn(flowOf(null))

        val viewModel = LoginViewModel(userManager, apiService)

        var errorCalled = false
        viewModel.login("johnd", "wrongpass", {
            fail("onSuccess should not be called")
        }, {
            errorCalled = true
        })

        advanceUntilIdle()

        val state = viewModel.loginState.value
        assertFalse(state.loading)
        assertNull(state.token)
        assertEquals("Invalid credentials", state.error)
        assertTrue(errorCalled)
    }

    @Test
    fun `clearUserToken clears stored token and resets state`() = runTest {
        whenever(userManager.tokenFlow).thenReturn(flowOf("existing_token"))

        val viewModel = LoginViewModel(userManager, apiService)
        advanceUntilIdle()

        viewModel.clearUserToken()
        advanceUntilIdle()

        verify(userManager).clearToken()
        val state = viewModel.loginState.value
        assertNull(state.token)
    }
}