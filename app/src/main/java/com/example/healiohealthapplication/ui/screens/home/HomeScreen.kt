package com.example.healiohealthapplication.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.HomeScreenTopNavBar

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier, viewModel: HomeScreenViewModel) {
    Scaffold(
        topBar = { HomeScreenTopNavBar("Home", navController, viewModel.expanded, { viewModel.toggleExpanded() }) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Text(
            text = "Home Screen!",
            modifier = Modifier.padding(innerPadding)
        )
        Button(
            onClick = {  }
        ) {
            Text(text = "Sign Up")
        }
    }
}