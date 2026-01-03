package com.compagnon2code.animal_app.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.compagnon2code.animal_app.model.Animal
import com.compagnon2code.animal_app.model.getAnimalList
import com.compagnon2code.animal_app.ui.screens.AnimalAddScreen
import com.compagnon2code.animal_app.ui.screens.AnimalDetailScreen
import com.compagnon2code.animal_app.ui.screens.AnimalListScreen
import com.compagnon2code.animal_app.ui.screens.AnimalUpdateScreen
import com.compagnon2code.animal_app.widget.ConfirmationDialog

sealed class AnimalRoute(val route: String) {

    object List : AnimalRoute("animal_list")
    object Add : AnimalRoute("animal_add")
    object Detail : AnimalRoute("animal_detail/{animalId}") {
        fun createRoute(id: Int) = "animal_detail/$id"
    }
    object Update : AnimalRoute("animal_update/{animalId}") {
        fun createRoute(id: Int) = "animal_update/$id"
    }
}

object NavTransitions {

    private val duration = 400

    val enter = slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(duration)
    ) + fadeIn(tween(duration))

    val exit = slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(duration)
    ) + fadeOut(tween(duration))

    val popEnter = slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(duration)
    ) + fadeIn(tween(duration))

    val popExit = slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(duration)
    ) + fadeOut(tween(duration))
}

@Composable
fun AnimalNavGraph(
    navController: NavHostController,
    animals: SnapshotStateList<Animal>,
    onDelete: (Int) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = AnimalRoute.List.route,
        enterTransition = { NavTransitions.enter },
        exitTransition = { NavTransitions.exit },
        popEnterTransition = { NavTransitions.popEnter },
        popExitTransition = { NavTransitions.popExit },
    ) {

        composable(AnimalRoute.List.route) {
            AnimalListScreen(
                animals = animals,
                onAnimalClick = { id ->
                    navController.navigate(AnimalRoute.Detail.createRoute(id))
                },
                onDelete = onDelete,
                onAdd = { navController.navigate(AnimalRoute.Add.route) }
            )
        }

        composable(AnimalRoute.Add.route) {
            AnimalAddScreen(
                onSave = {
                    animals.add(it)
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            AnimalRoute.Detail.route,
            arguments = listOf(navArgument("animalId") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments!!.getInt("animalId")
            val animal = animals.find { it.id == id } ?: return@composable

            AnimalDetailScreen(
                animal = animal,
                onBack = { navController.popBackStack() },
                onUpdate = {
                    navController.navigate(AnimalRoute.Update.createRoute(id))
                }
            )
        }

        composable(
            AnimalRoute.Update.route,
            arguments = listOf(navArgument("animalId") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments!!.getInt("animalId")
            val animal = animals.find { it.id == id } ?: return@composable

            AnimalUpdateScreen(
                animal = animal,
                onSave = { updated ->
                    val index = animals.indexOfFirst { it.id == updated.id }
                    if (index != -1) animals[index] = updated
                    navController.popBackStack(AnimalRoute.List.route, false)
                },
                onCancel = { navController.popBackStack() },
                onBack = {
                    navController.popBackStack(AnimalRoute.List.route, false)
                }
            )
        }
    }
}



@Composable
fun AnimalApp() {
    val navController = rememberNavController()
    val animals = remember { getAnimalList().toMutableStateList() }

    var showDialog by remember { mutableStateOf(false) }
    var animalToDelete by remember { mutableStateOf<Int?>(null) }

    if (showDialog && animalToDelete != null) {
        ConfirmationDialog(
            title = "Delete ?",
            text = "Delete this animal ?",
            onDismiss = { showDialog = false },
            onConfirm = {
                animals.removeIf { it.id == animalToDelete }
                showDialog = false
            }
        )
    }

    AnimalNavGraph(
        navController = navController,
        animals = animals,
        onDelete = {
            animalToDelete = it
            showDialog = true
        }
    )
}
