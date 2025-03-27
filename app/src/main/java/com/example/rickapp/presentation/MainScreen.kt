package com.example.rickapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.rickapp.R

@Composable
fun MainScreen(viewModel: CharacterViewModel, navController: NavController) {
    val query by viewModel.query.collectAsState()
    val characters by viewModel.characters.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.onQueryChanged(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("SearchTextField"),
            label = { Text("Search") },
            trailingIcon = {
                IconButton(onClick = { viewModel.onSearchClicked() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search"
                    )
                }
            },
            singleLine = true
        )

        FilterBar(
            onStatusChange = viewModel::onStatusFilterChanged,
            onSpeciesChange = viewModel::onSpeciesFilterChanged,
            onTypeChange = viewModel::onTypeFilterChanged
        )

        if (loading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        if (error != null) {
            Text(
                text = error ?: "",
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(characters) { character ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("detail/${character.id}")
                        }
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = rememberAsyncImagePainter(model = character.image),
                            contentDescription = character.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                        )
                        Text(
                            text = character.name,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilterBar(
    onStatusChange: (String) -> Unit,
    onSpeciesChange: (String) -> Unit,
    onTypeChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DropdownFilter("Status", listOf("", "Alive", "Dead", "Unknown"), onStatusChange)
        DropdownFilter("Species", listOf("", "Human", "Alien"), onSpeciesChange)
        DropdownFilter("Type", listOf("", "Parasite", "Robot"), onTypeChange)
    }
}

@Composable
fun DropdownFilter(label: String, options: List<String>, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(options.first()) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(if (selected.isNotBlank()) "$label: $selected" else label)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(if (it.isBlank()) "All" else it) },
                    onClick = {
                        selected = it
                        expanded = false
                        onSelected(it)
                    }
                )
            }
        }
    }
}
