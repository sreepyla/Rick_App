package com.example.rickapp.presentation

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.rickapp.data.model.Character

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(character: Character, navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = character.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(character.image),
                contentDescription = "Character image: ${character.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Species: ${character.species}", modifier = Modifier.semantics {
                contentDescription = "Species: ${character.species}"
            })

            Text("Status: ${character.status}", modifier = Modifier.semantics {
                contentDescription = "Status: ${character.status}"
            })

            Text("Origin: ${character.origin.name}", modifier = Modifier.semantics {
                contentDescription = "Origin: ${character.origin.name}"
            })

            if (character.type.isNotEmpty()) {
                Text("Type: ${character.type}", modifier = Modifier.semantics {
                    contentDescription = "Type: ${character.type}"
                })
            }

            Text("Created: ${character.created.substringBefore("T")}", modifier = Modifier.semantics {
                contentDescription = "Created: ${character.created.substringBefore("T")}"
            })

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Check out ${character.name} from Rick and Morty!\n" +
                                "Species: ${character.species}\nStatus: ${character.status}\nOrigin: ${character.origin.name}\nImage: ${character.image}"
                    )
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(sendIntent, "Share character via"))
            }) {
                Text("Share")
            }
        }
    }
}
