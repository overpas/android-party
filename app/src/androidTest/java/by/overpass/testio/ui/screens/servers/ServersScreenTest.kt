package by.overpass.testio.ui.screens.servers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.overpass.testio.MainActivity
import by.overpass.testio.domain.servers.usecase.FetchServersUseCase
import by.overpass.testio.presentation.servers.ServersViewModel
import by.overpass.testio.ui.theme.TestioTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@ExperimentalComposeUiApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ServersScreenTest {

	@get:Rule
	val composeTestRule = createAndroidComposeRule<MainActivity>()

	@get:Rule
	var hiltRule = HiltAndroidRule(this)

	@Inject
	lateinit var fetchServersUseCase: FetchServersUseCase

	@Before
	fun init() {
		hiltRule.inject()
	}

	@Test
	fun testServersDisplayed() {
		composeTestRule.setContent {
			TestioTheme {
				ServersScreen(
					modifier = Modifier.fillMaxSize(),
					viewModel = ServersViewModel(fetchServersUseCase),
				)
			}
		}

		composeTestRule.onNodeWithText("server")
			.assertIsDisplayed()

		composeTestRule.onNodeWithText("1 km")
			.assertIsDisplayed()
	}

	@Test
	fun testLogoutClicked() {
		val logoutListener = mock(LogoutListener::class.java)
		composeTestRule.setContent {
			TestioTheme {
				ServersScreen(
					modifier = Modifier.fillMaxSize(),
					viewModel = ServersViewModel(fetchServersUseCase),
					onLogout = {
						logoutListener.logout()
					},
				)
			}
		}

		composeTestRule.onNodeWithContentDescription("Logout")
			.performClick()

		verify(logoutListener).logout()
	}

	private interface LogoutListener {

		fun logout()
	}
}