package com.example.rickapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.*
import com.example.rickapp.di.AppModuleProvider
import com.example.rickapp.presentation.MainScreen
import com.example.rickapp.presentation.DetailScreen
import com.example.rickapp.presentation.CharacterViewModel
import com.example.rickapp.ui.theme.RickAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = (application as AppModuleProvider).getCharacterRepository()
        val viewModel = CharacterViewModel(repository)

        setContent {
            RickAppTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(viewModel, navController)
                    }
                    composable("detail/{id}") { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                        val character = viewModel.characters.value.find { it.id == id }
                        character?.let {
                            DetailScreen(character = it, navController = navController)
                        }
                    }
                }
            }
        }
    }
}
