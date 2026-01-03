package com.compagnon2code.animal_app.di

import android.content.Context
import com.compagnon2code.animal_app.db.AnimalDb
import com.compagnon2code.animal_app.ui.AnimalRepository
import com.compagnon2code.animal_app.ui.AnimalRepositoryImpl

interface AppContainer{
    val animalRepository: AnimalRepository

}

class AppContainerImpl(
    val context : Context
): AppContainer{
    override val animalRepository: AnimalRepository
        get() = AnimalRepositoryImpl(AnimalDb.getAnimalDb(context).getAnimalDao())
}