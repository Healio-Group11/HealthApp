package com.example.healiohealthapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.healiohealthapplication.navigation.HealioNavigation
import com.example.healiohealthapplication.ui.theme.HealioHealthApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealioHealthApplicationTheme {
                    HealioNavigation() // problem with hilt
            }
        }
    }
}