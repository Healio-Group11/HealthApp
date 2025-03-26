package com.example.healiohealthapplication.ui.screens.medicine

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.TopNavBar

@Composable
fun MedicineScreen(navController: NavController, modifier: Modifier) {
    Scaffold(
        topBar = { TopNavBar("Medicine", navController) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Text(
            text = "Medicine Screen!",
            modifier = Modifier.padding(innerPadding)
        )
        Button(
            onClick = {  }
        ) {
            Text(text = "Sign Up")
        }
    }
}