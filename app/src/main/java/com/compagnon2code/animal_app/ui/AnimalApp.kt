package com.compagnon2code.animal_app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import com.compagnon2code.animal_app.data.getAnimalList
import com.compagnon2code.animal_app.ui.Screen.*
import com.compagnon2code.animal_app.ui.screens.AnimalAddScreen
import com.compagnon2code.animal_app.ui.screens.AnimalDetailScreen
import com.compagnon2code.animal_app.ui.screens.AnimalListScreen
import com.compagnon2code.animal_app.ui.screens.AnimalUpdateScreen
import com.compagnon2code.animal_app.widget.ConfirmationDialog

sealed class Screen {

    object AnimalList : Screen()
    object AnimalAdd : Screen()
    class AnimalUpdate(val animalId: Int) : Screen()
    class AnimalDetail(val animalId: Int) : Screen()
}

@Composable
fun AnimalApp(
    modifier: Modifier = Modifier
) {
    var animals by remember { mutableStateOf(getAnimalList().toMutableStateList()) }

    var stateCurrentScreen by remember { mutableStateOf<Screen>(Screen.AnimalList) }

    var stateShowDialog by remember { mutableStateOf(false) }

    var stateAnimalToDelete by remember { mutableStateOf(0) }

    if (stateShowDialog) {
        ConfirmationDialog(
            title = "Delete ?",
            text = "Delete this animal ?",
            onDismiss = { stateShowDialog = false },
            onConfirm = {
                animals.removeIf { animal -> animal.id == stateAnimalToDelete }
                stateShowDialog = false
            },
        )
    }

    when (val screen = stateCurrentScreen) {
        is Screen.AnimalList -> {
            AnimalListScreen(
                animals = animals,
                onAnimalClick = { stateCurrentScreen = AnimalDetail(it) },
                onDelete = { id ->
                    // sans confirmation
                    // animals.removeIf { animal -> animal.id == id }
                    // avec boite de dialog
                    stateAnimalToDelete = id
                    stateShowDialog = true

                },
                onAdd = { stateCurrentScreen = Screen.AnimalAdd }
            )
        }

        is Screen.AnimalDetail -> {
            animals.find { it.id == screen.animalId }?.let {
                AnimalDetailScreen(
                    animal = it,
                    onBack = {
                        stateCurrentScreen = Screen.AnimalList
                    },
                    onUpdate = {
                        stateCurrentScreen = AnimalUpdate(it.id) //??? it seul ?
                    },
                )
            }
        }

        is Screen.AnimalAdd -> {
            AnimalAddScreen(
                // version de base avec kotlin
                // onSave = {animals.add(it)},
                // version  lisible
                onSave = { animalToAdd ->
                    animals.add(animalToAdd)
                    stateCurrentScreen = Screen.AnimalList
                },
                onCancel = {
                    stateCurrentScreen = Screen.AnimalList
                },
                onBack = {
                    stateCurrentScreen = Screen.AnimalList
                }
            )
        }

        is Screen.AnimalUpdate -> {
            animals.find { it.id == screen.animalId }?.let {
                AnimalUpdateScreen(
                    animal = it,
                    onSave = {updatedAnimal ->
                        val index = animals.indexOfFirst { it.id == updatedAnimal.id }
                        if (index != -1) {
                            animals[index] = updatedAnimal
                        }
                        stateCurrentScreen = Screen.AnimalList
                    },
                    onCancel = { stateCurrentScreen = Screen.AnimalList },
                    onBack = { stateCurrentScreen = Screen.AnimalList }

                )

            }
        }
    }
}