package by.overpass.testio.ui.screens.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.overpass.testio.MainActivity
import by.overpass.testio.domain.login.usecase.LoginUseCase
import by.overpass.testio.domain.servers.usecase.FetchServersUseCase
import by.overpass.testio.presentation.login.CredentialsValidator
import by.overpass.testio.presentation.login.LoginViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalComposeUiApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

	@get:Rule
	val composeTestRule = createAndroidComposeRule<MainActivity>()

	@get:Rule
	var hiltRule = HiltAndroidRule(this)

	@Inject
	lateinit var loginUseCase: LoginUseCase

	@Inject
	lateinit var fetchServersUseCase: FetchServersUseCase

	@Inject
	lateinit var credentialsValidator: CredentialsValidator

	@Before
	fun init() {
		hiltRule.inject()
	}

	@Test
	fun testUsernameAndPasswordFieldsAndLoginButtonExist() {
		composeTestRule.setContent {
			LoginScreen(
				modifier = Modifier.fillMaxSize(),
				viewModel = LoginViewModel(loginUseCase, fetchServersUseCase, credentialsValidator),
			)
		}

		composeTestRule.onNodeWithText("Username")
			.assertIsDisplayed()

		composeTestRule.onNodeWithText("Password")
			.assertIsDisplayed()

		composeTestRule.onNodeWithText("Log in")
			.assertIsDisplayed()
	}

	@Test
	fun testFieldErrorsDisplayedWhenLoggingInWithEmptyFields() {
		composeTestRule.setContent {
			LoginScreen(
				modifier = Modifier.fillMaxSize(),
				viewModel = LoginViewModel(loginUseCase, fetchServersUseCase, credentialsValidator),
			)
		}

		composeTestRule.onNodeWithText("Log in")
			.performClick()

		composeTestRule.onAllNodesWithText("This field shouldn't be empty")
			.assertCountEquals(2)
	}

	@Test
	fun testLoadingScreenShownWhenLoggingInWithValidCredentials() {
		composeTestRule.setContent {
			LoginScreen(
				modifier = Modifier.fillMaxSize(),
				viewModel = LoginViewModel(loginUseCase, fetchServersUseCase, credentialsValidator),
			)
		}

		composeTestRule.onNodeWithText("Username")
			.performTextInput("tesonet")

		composeTestRule.onNodeWithText("Password")
			.performTextInput("partyanimal")

		composeTestRule.onNodeWithText("Log in")
			.performClick()

		// TODO: Check that login screen is opened
	}

	@Test
	fun testLoadingScreenShownWhenLoggingInWithInvalidCredentials() {
		composeTestRule.setContent {
			LoginScreen(
				modifier = Modifier.fillMaxSize(),
				viewModel = LoginViewModel(loginUseCase, fetchServersUseCase, credentialsValidator),
			)
		}

		composeTestRule.onNodeWithText("Username")
			.performTextInput("incorrect")

		composeTestRule.onNodeWithText("Password")
			.performTextInput("data")

		composeTestRule.onNodeWithText("Log in")
			.performClick()

		// TODO: Check that login screen is opened
	}
}