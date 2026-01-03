package com.compagnon2code.animal_app.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.compagnon2code.animal_app.model.Animal
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalDao{
    @Query("SELECT * FROM tblAnimal")
    fun getAnimals() : Flow<List<Animal>>

    @Query("SELECT *  FROM tblAnimal WHERE id= :id ")
    fun getAnimalById(id: Int) : Flow<Animal>

    // FLOW tourne déjà dans une coroutine, suspend pas necessaire

    // on peut cumuler update et insert avec UPSERT
    // si id n'existe pas  -> INSERT
    // si id existe -> UPDATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert (animal: Animal)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bulkInsert(animals: List<Animal>)

    @Delete
    suspend fun delete(animal: Animal)
}