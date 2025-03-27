package com.example.rickapp

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.example.rickapp.presentation.CharacterViewModel
import com.example.rickapp.presentation.MainScreen
import com.example.rickapp.ui.theme.RickAppTheme
import org.junit.Rule
import org.junit.Test

class MainScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun searchBar_isDisplayed() {
        val app = composeTestRule.activity.application as App
        val viewModel = CharacterViewModel(app.getCharacterRepository())

        composeTestRule.setContent {
            RickAppTheme {
                MainScreen(viewModel = viewModel, navController = rememberNavController())
            }
        }

        composeTestRule
            .onNodeWithTag("SearchTextField")
            .assertIsDisplayed()
    }
}
