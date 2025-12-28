package com.compagnon2code.animal_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.compagnon2code.animal_app.data.Animal
import com.compagnon2code.animal_app.ui.components.AnimalCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalListScreen(
    animals: SnapshotStateList<Animal>,
    onAnimalClick: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    onAdd: () -> Unit
) {

    var stateSearch by remember { mutableStateOf("") }
    // pour filtrer les animaux en fonction de la recherche
    val stateAnimals by remember(stateSearch, animals) {
        derivedStateOf {
            if (stateSearch.isBlank()) {
                animals
            } else {
                val query = stateSearch.trim()
                // en début de liste, les elements qui commence par...
                val startsWith = animals.filter {
                    it.name.startsWith(query, ignoreCase = true)
                }
                // à la suite, les éléments qui contiennent... mais ne commencent pas par...
                val contains = animals.filter {
                    it.name.contains(query, ignoreCase = true) &&
                            !it.name.startsWith(query, ignoreCase = true)
                }
                // on renvoie les 2 listes
                startsWith + contains
            }
        }
    }


    Scaffold(
        modifier = Modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.surfaceContainerHighest),
                        contentAlignment = Alignment.Center,

                        ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = "Animal List",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(vertical = 15.dp),
                                style = MaterialTheme.typography.titleLarge
                            )

                            ElevatedButton(
                                onClick = { onAdd() },
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add Animal"
                                )
                            }
                        }


                    }
                }
            )
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = stateSearch,
                onValueChange = { stateSearch = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )

            LazyColumn {

                items(stateAnimals) { animal ->
                    AnimalCard(
                        animal = animal,
                        onClick = { onAnimalClick(it) },
                        onDelete = { onDelete(it) },
                    )
                }
            }
        }
    }
}