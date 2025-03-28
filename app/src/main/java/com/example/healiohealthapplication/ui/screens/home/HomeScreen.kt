package com.example.healiohealthapplication.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.HomeScreenTopNavBar
import com.example.healiohealthapplication.ui.screens.shared.SharedViewModel

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier, viewModel: HomeScreenViewModel, sharedViewModel: SharedViewModel) {
    Scaffold(
        topBar = { HomeScreenTopNavBar("Home", navController, viewModel.expanded, { viewModel.toggleExpanded() }) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        // CollectAsState automatically observes changes.
        // Apparently this does not break MVVM architecture! TODO: check whether this is true and that we can use this in UI
        val userData by sharedViewModel.userData.collectAsState()

        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            Text(
                text = "Currently logged in user's email: ${userData?.email ?: "Not logged in"}!"
            )
        }
    }
}


