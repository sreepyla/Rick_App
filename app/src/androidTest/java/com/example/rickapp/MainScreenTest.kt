package com.example.rickapp

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.rickapp.presentation.CharacterViewModel
import com.example.rickapp.presentation.MainScreen
import com.example.rickapp.repository.CharacterRepository
import com.example.rickapp.ui.theme.RickAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainScreenUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var viewModel: CharacterViewModel

    @Before
    fun setup() {
        val repo = (composeTestRule.activity.application as App).getCharacterRepository()
        viewModel = CharacterViewModel(repo)
    }

    @Test
    fun searchBar_isDisplayed() {
        composeTestRule.setContent {
            RickAppTheme {
                MainScreen(viewModel = viewModel, navController = rememberNavController())
            }
        }

        composeTestRule.onNodeWithTag("SearchTextField").assertIsDisplayed()
    }

    @Test
    fun filterButtons_areDisplayed() {
        composeTestRule.setContent {
            RickAppTheme {
                MainScreen(viewModel = viewModel, navController = rememberNavController())
            }
        }

        composeTestRule.onNodeWithText("Status").assertIsDisplayed()
        composeTestRule.onNodeWithText("Species").assertIsDisplayed()
        composeTestRule.onNodeWithText("Type").assertIsDisplayed()
    }

    @Test
    fun characterCard_displaysCharacterName() {
        composeTestRule.setContent {
            RickAppTheme {
                MainScreen(viewModel = viewModel, navController = rememberNavController())
            }
        }

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("Rick Sanchez").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Rick Sanchez").assertIsDisplayed()
    }
}
