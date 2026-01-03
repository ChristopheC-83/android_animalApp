package com.compagnon2code.animal_app.ui

import com.compagnon2code.animal_app.dao.AnimalDao
import com.compagnon2code.animal_app.model.Animal
import kotlinx.coroutines.flow.Flow

interface AnimalRepository {

    fun getAnimals(): Flow<List<Animal>>

    fun getAnimalById(id: Int): Flow<Animal>

    suspend fun upsertAnimal(animal: Animal)

    suspend fun bulkInsert(animals: List<Animal>)

    suspend fun deleteAnimal(animal: Animal)
}

class AnimalRepositoryImpl(
    private val dao: AnimalDao
) : AnimalRepository {

    override fun getAnimals(): Flow<List<Animal>> {
        return dao.getAnimals()
    }

    override fun getAnimalById(id: Int): Flow<Animal> {
        return dao.getAnimalById(id)
    }

    override suspend fun upsertAnimal(animal: Animal) {
        dao.upsert(animal)
    }

    override suspend fun bulkInsert(animals: List<Animal>) {
        dao.bulkInsert(animals)
    }

    override suspend fun deleteAnimal(animal: Animal) {
        dao.delete(animal)
    }
}