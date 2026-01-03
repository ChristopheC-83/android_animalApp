package com.compagnon2code.animal_app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.compagnon2code.animal_app.AnimalApplication
import com.compagnon2code.animal_app.model.Animal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimalViewModel(
    val repository: AnimalRepository
) : ViewModel() {


    companion object {
        val factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AnimalApplication)
                val repo = application.container.animalRepository
                AnimalViewModel(repo)
            }
        }

    }

    private val _uiState = MutableStateFlow(AnimalUIState())
    val uiState = _uiState.asStateFlow()

    init {
        getAnimalList()

    }

    fun getAnimalList() {
        viewModelScope.launch {
            repository.getAnimals().collect() { animals ->
                try {
                    _uiState.value.copy(animals = animals)
                } catch (e: Exception) {
                    println("Exception : ${e.message}")
                }
            }
        }
    }

    fun getAnimalById(id: Int) {
        viewModelScope.launch {
            repository.getAnimalById(id).collect() { animal ->
                try {
                    _uiState.emit(
                        _uiState.value.copy(
                            animals = listOf(animal)
                        )
                    )
                } catch (e: Exception) {
                    println("getAnimalById error : ${e.message}")
                }
            }
        }
    }

    fun upsert(animal: Animal) {
        viewModelScope.launch {
            repository.upsertAnimal(animal)
        }
    }

    fun bulkInsert(animals: MutableList<Animal>) {
        viewModelScope.launch {
            repository.bulkInsert(animals)
        }
    }

    fun delete(animal: Animal) {
        viewModelScope.launch {
            repository.deleteAnimal(animal)
        }
    }


    data class AnimalUIState(
        val animals: List<Animal> = emptyList()
    )

}