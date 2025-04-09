package com.example.ebisconstore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
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

        userManager = mock(UserManager::class.java)
        apiService = mock(FakeStoreApi::class.java)

        runBlocking {
            whenever(userManager.tokenFlow).thenReturn(flowOf(null))
        }

        viewModel = LoginViewModel(userManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login with valid credentials triggers onSuccess and updates state`() = runTest {
        val fakeToken = "user_token"
        whenever(apiService.login(LoginRequest("johnd", "m38rmF$")))
            .thenReturn(LoginResponse(token = fakeToken))

        var onSuccessCalled = false

        viewModel.login("johnd", "m38rmF$", {
            onSuccessCalled = true
        }, {
            fail("onError should not be called")
        })

        advanceUntilIdle()

        val state = viewModel.loginState.value
        assertFalse(state.loading)
        assertNotNull(state.token)
        assertTrue(state.token!!.isNotBlank())
        assertNull(state.error)
        assertTrue(onSuccessCalled)
    }

    @Test
    fun `login with invalid credentials triggers error`() = runTest {
        val loginRequest = LoginRequest("johnd", "wrongpass")

        whenever(apiService.login(loginRequest)).thenThrow(RuntimeException("Invalid credentials"))

        var onErrorCalled = false

        viewModel.login("johnd", "wrongpass", {
            fail("onSuccess should not be called")
        }, {
            onErrorCalled = true
        })

        advanceUntilIdle()

        val state = viewModel.loginState.value
        assertNull(state.token)
        assertFalse(state.loading)
        assertNotNull(state.error)
        assertTrue(onErrorCalled)
    }
}