package by.overpass.testio

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import by.overpass.testio.ui.theme.TestioTheme
import org.junit.Rule
import org.junit.Test

class GreetingTest {

	@get:Rule
	val composeTestRule = createComposeRule()

	@Test
	fun testGreetingDisplayed() {
		composeTestRule.setContent {
			TestioTheme {
				Greeting(name = "Android")
			}
		}

		composeTestRule.onNodeWithText("Hello Android!")
			.assertExists()
	}
}