package com.compagnon2code.animal_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compagnon2code.animal_app.model.Animal
import com.compagnon2code.animal_app.model.getAnimalList
import com.compagnon2code.animal_app.ui.theme.Animal_AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalDetailScreen(
    modifier: Modifier = Modifier,
    animal: Animal?,
    onBack: () -> Unit,
    onUpdate: () -> Unit,
) {
    if (animal == null) return
    val infoList = listOf(
        "Id" to animal.id.toString(),
        "Gender" to animal.gender,
        "Species" to animal.species,
        "Description" to animal.description,
        "Feeding" to animal.feeding,
        "Note" to animal.note
    )

    Scaffold(
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
                        Text(
                            text = "Animal Detail : ${animal.name}",
                            modifier = Modifier.padding(vertical = 15.dp)
                        )
                        ElevatedButton(
                            onClick = { onUpdate() },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Update Animal"
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack

                    ) {Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    ) }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = animal.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
                )

            infoList.forEach { (title, value) ->
                InfoCard(title = title, value = value)
            }
        }
    }
}

@Composable
fun InfoCard(
    title: String,
    value: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.labelLarge, color = Color.Gray)
            Text(text = value, style = MaterialTheme.typography.labelMedium)

        }
    }
}

@Preview
@Composable
private fun AnimalDetailScreenPreview() {
    Animal_AppTheme {
        AnimalDetailScreen(
            animal = getAnimalList()[0],
            onBack = {},
            onUpdate ={},
        )
    }

}