package com.compagnon2code.animal_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.compagnon2code.animal_app.ui.AnimalApp
import com.compagnon2code.animal_app.ui.theme.Animal_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Animal_AppTheme(
                //darkTheme = false, // on peut le forcer ici,
                dynamicColor = false // désactive les couleurs par défaut de M3
            ) {
                AnimalApp()
            }
        }
    }
}




