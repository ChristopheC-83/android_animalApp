package com.compagnon2code.animal_app

import android.app.Application
import com.compagnon2code.animal_app.di.AppContainer
import com.compagnon2code.animal_app.di.AppContainerImpl

class AnimalApplication : Application() {

    lateinit var container : AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(context = this)
    }



}