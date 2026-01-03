package com.compagnon2code.animal_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compagnon2code.animal_app.model.Animal
import com.compagnon2code.animal_app.model.getAnimalList
import com.compagnon2code.animal_app.ui.theme.Animal_AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalUpdateScreen(
    modifier: Modifier = Modifier,
    animal: Animal?,
    onSave: (Animal) -> Unit,
    onCancel: () -> Unit,
    onBack: () -> Unit,
) {

    data class FormField(
        val name: String,                // clé métier
        val label: String,
        val state: MutableState<String>,
        val required: Boolean = false
    )
    if (animal == null) return
    // Déclare tous tes states dans un tableau
    val fields = listOf(
        FormField("name", "Name", remember { mutableStateOf(animal.name) }, required = true),
        FormField("gender", "Gender", remember { mutableStateOf(animal.gender) }, required = true),
        FormField("species", "Species", remember { mutableStateOf(animal.species) }, required = true),
        FormField("description", "Description", remember { mutableStateOf(animal.description) }),
        FormField("feeding", "Feeding", remember { mutableStateOf(animal.feeding) }),
        FormField("note", "Note", remember { mutableStateOf(animal.note) }),
    )

    val isFormValid by remember {
        derivedStateOf {
            fields
                .filter { it.required }
                .all { it.state.value.isNotBlank() }
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
                        Text(
                            text = "Update Animal : ${animal.name}",
                            modifier = Modifier.padding(vertical = 15.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {onBack()}
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding((paddingValues))
                .fillMaxSize()
                .padding(12.dp),
        ) {
            fields.forEach { field ->
                OutlinedTextField(
                    value = field.state.value,
                    onValueChange = { field.state.value = it },
                    label = { Text(field.label) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        if(isFormValid){
                            fun valueOf(name: String): String =
                                fields.first { it.name == name }.state.value

                            val updatedAnimal = Animal(
                                id = animal.id,
                                name = valueOf("name"),
                                gender = valueOf("gender"),
                                species = valueOf("species"),
                                description = valueOf("description"),
                                feeding = valueOf("feeding"),
                                note = valueOf("note")
                            )

                            onSave(updatedAnimal)

                        }
                    },
                    enabled =isFormValid
                ) {
                    Text(text = "Update")
                }
                Button(
                    onClick = { onBack() }
                ) {
                    Text(text = "Cancel")
                }
            }
        }
    }

}


@Preview
@Composable
private fun AnimalUpdateScreenPrev() {
    Animal_AppTheme {
        AnimalUpdateScreen(
            animal = getAnimalList()[0],
            onSave = {},
            onCancel = {},
            onBack = {},
        )
    }
}