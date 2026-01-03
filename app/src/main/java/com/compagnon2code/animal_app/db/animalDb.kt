package com.compagnon2code.animal_app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.compagnon2code.animal_app.dao.AnimalDao
import com.compagnon2code.animal_app.model.Animal

@Database(entities = [Animal::class], version = 1) // avec Animal, d'autres Classes si nécessaire
abstract class AnimalDb : RoomDatabase() {

    abstract fun getAnimalDao(): AnimalDao
    companion object {
        private var INSTANCE: AnimalDb? = null
        fun getAnimalDb(context: Context) : AnimalDb {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = AnimalDb::class.java,
                    name = "AnimalDb"
                ).build()
            }
        return INSTANCE as AnimalDb
        }
    }
}