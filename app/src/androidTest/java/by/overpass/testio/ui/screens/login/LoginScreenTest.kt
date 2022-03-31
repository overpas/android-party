package by.overpass.testio.ui.screens.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import by.overpass.testio.MainActivity
import by.overpass.testio.domain.login.usecase.LoginUseCase
import by.overpass.testio.login.ui.LoginScreen
import by.overpass.testio.presentation.login.CredentialsValidator
import by.overpass.testio.presentation.login.LoginViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalComposeUiApi
@HiltAndroidTest
class LoginScreenTest {

	@get:Rule
	val composeTestRule = createAndroidComposeRule<MainActivity>()

	@get:Rule
	var hiltRule = HiltAndroidRule(this)

	@Inject
	lateinit var loginUseCase: LoginUseCase

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
				viewModel = LoginViewModel(loginUseCase, credentialsValidator)
			)
		}

		composeTestRule.onNodeWithText("Username")
			.assertExists()

		composeTestRule.onNodeWithText("Password")
			.assertExists()

		composeTestRule.onNodeWithText("Log in")
			.assertExists()
	}

	@Test
	fun testLoginWithCorrectCredentialsWorks() {
		composeTestRule.setContent {
			LoginScreen(
				modifier = Modifier.fillMaxSize(),
				viewModel = LoginViewModel(loginUseCase, credentialsValidator)
			)
		}

		composeTestRule.onNodeWithText("Username")
			.performTextInput("tesonet")

		composeTestRule.onNodeWithText("Password")
			.performTextInput("partyanimal")

		composeTestRule.onNodeWithText("Log in")
			.performClick()

		// TODO: To be asserted that the success message is shown
	}

	@Test
	fun testLoginWithIncorrectCredentialsFails() {
		composeTestRule.setContent {
			LoginScreen(
				modifier = Modifier.fillMaxSize(),
				viewModel = LoginViewModel(loginUseCase, credentialsValidator)
			)
		}

		composeTestRule.onNodeWithText("Username")
			.performTextInput("incorrect")

		composeTestRule.onNodeWithText("Password")
			.performTextInput("data")

		composeTestRule.onNodeWithText("Log in")
			.performClick()

		// TODO: To be asserted that the failure message is shown
	}
}