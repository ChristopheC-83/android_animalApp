package com.compagnon2code.animal_app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.compagnon2code.animal_app.data.getAnimalList
import com.compagnon2code.animal_app.ui.screens.AnimalAddScreen
import com.compagnon2code.animal_app.ui.screens.AnimalDetailScreen
import com.compagnon2code.animal_app.ui.screens.AnimalListScreen
import com.compagnon2code.animal_app.ui.screens.AnimalUpdateScreen
import com.compagnon2code.animal_app.widget.ConfirmationDialog

//sealed class Screen {
//
//    object AnimalList : Screen()
//    object AnimalAdd : Screen()
//    class AnimalUpdate(val animalId: Int) : Screen()
//    class AnimalDetail(val animalId: Int) : Screen()
//}

enum class AnimalScreen {
    AnimalList,
    AnimalAdd,
    AnimalDetail,
    AnimalUpdate
}

@Composable
fun AnimalApp(
    modifier: Modifier = Modifier
) {
    var navController = rememberNavController()

    var animals by remember { mutableStateOf(getAnimalList().toMutableStateList()) }

//    var stateCurrentScreen by remember { mutableStateOf<Screen>(Screen.AnimalList) }

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

    NavHost(
        navController = navController,
        startDestination = AnimalScreen.AnimalList.name // name pour avoir String
    ) {

        composable(
            route = AnimalScreen.AnimalList.name
        ) {
            AnimalListScreen(
                animals = animals,
                onAnimalClick = { id ->
                    navController.navigate(AnimalScreen.AnimalDetail.name + "/$id")
                },
                onDelete = { id ->
                    stateAnimalToDelete = id
                    stateShowDialog = true
                },
                onAdd = { navController.navigate(route = AnimalScreen.AnimalAdd.name) }
            )
        }

        composable(
            route = AnimalScreen.AnimalAdd.name
        ) {
            AnimalAddScreen(
                onSave = { animalToAdd ->
                    animals.add(animalToAdd)
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }


        composable(
            route = AnimalScreen.AnimalDetail.name + "/{animalId}",
            arguments = listOf(navArgument(name = "animalId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("animalId")
            animals.find { it.id == id }?.let {
                AnimalDetailScreen(
                    animal = it,
                    onBack = {
                        navController.popBackStack()
                    },
                    onUpdate = {
                        navController.navigate(route = AnimalScreen.AnimalUpdate.name + "/${id}")
                    },
                )
            }
        }

        composable(
            route = AnimalScreen.AnimalUpdate.name + "/{animalId}",
            arguments = listOf(navArgument(name = "animalId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("animalId")
            AnimalUpdateScreen(
                animal = animals.find { it.id == id },
                onSave = { updatedAnimal ->
                    val index = animals.indexOfFirst { it.id == updatedAnimal.id }
                    if (index != -1) {
                        animals[index] = updatedAnimal
                    }
                    navController.popBackStack(
                        route = AnimalScreen.AnimalList.name,
                        inclusive = false
                    )

                },
//                 retour à l'écran precedent
                onCancel = { navController.popBackStack() },
//                 retour à un écran ciblé
                onBack = {
                    navController.popBackStack(
                        route = AnimalScreen.AnimalList.name,
                        inclusive = false
                    )
                }

            )
        }
    }

//    when (val screen = stateCurrentScreen) {
//        is Screen.AnimalList -> {
//            AnimalListScreen(
//                animals = animals,
//                onAnimalClick = { stateCurrentScreen = AnimalDetail(it) },
//                onDelete = { id ->
//                    // sans confirmation
//                    // animals.removeIf { animal -> animal.id == id }
//                    // avec boite de dialog
//                    stateAnimalToDelete = id
//                    stateShowDialog = true
//
//                },
//                onAdd = { stateCurrentScreen = Screen.AnimalAdd }
//            )
//        }
//
//        is Screen.AnimalDetail -> {
//            animals.find { it.id == screen.animalId }?.let {
//                AnimalDetailScreen(
//                    animal = it,
//                    onBack = {
//                        stateCurrentScreen = Screen.AnimalList
//                    },
//                    onUpdate = {
//                        stateCurrentScreen = AnimalUpdate(it.id) //??? it seul ?
//                    },
//                )
//            }
//        }
//
//        is Screen.AnimalAdd -> {
//            AnimalAddScreen(
//                // version de base avec kotlin
//                // onSave = {animals.add(it)},
//                // version  lisible
//                onSave = { animalToAdd ->
//                    animals.add(animalToAdd)
//                    stateCurrentScreen = Screen.AnimalList
//                },
//                onCancel = {
//                    stateCurrentScreen = Screen.AnimalList
//                },
//                onBack = {
//                    stateCurrentScreen = Screen.AnimalList
//                }
//            )
//        }
//
//        is Screen.AnimalUpdate -> {
//            animals.find { it.id == screen.animalId }?.let {
//                AnimalUpdateScreen(
//                    animal = it,
//                    onSave = { updatedAnimal ->
//                        val index = animals.indexOfFirst { it.id == updatedAnimal.id }
//                        if (index != -1) {
//                            animals[index] = updatedAnimal
//                        }
//                        stateCurrentScreen = Screen.AnimalList
//                    },
//                    onCancel = { stateCurrentScreen = Screen.AnimalList },
//                    onBack = { stateCurrentScreen = Screen.AnimalList }
//
//                )
//
//            }
//        }
//    }
}