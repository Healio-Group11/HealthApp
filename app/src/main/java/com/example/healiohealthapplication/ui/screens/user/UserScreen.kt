package com.example.healiohealthapplication.ui.screens.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healiohealthapplication.navigation.Routes
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.ErrorCard
import com.example.healiohealthapplication.ui.components.TopNavBar
import com.example.healiohealthapplication.ui.components.UniqueFloatingActionButton
import com.example.healiohealthapplication.ui.screens.shared.SharedViewModel

@Composable
fun UserScreen(navController: NavController, viewModel: UserViewModel, sharedViewModel: SharedViewModel) {
    val userData by sharedViewModel.userData.collectAsState()
    LaunchedEffect(userData?.id) {
        userData?.let { user ->
            viewModel.userId = user.id
            viewModel.age = user.age
            viewModel.gender = user.gender
            viewModel.height = user.height
            viewModel.weight = user.weight
            viewModel.bmi = user.bmi
        }
    }
    Scaffold(
        topBar = { TopNavBar("Profile", navController) },
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = { userData?.let { UniqueFloatingActionButton(navController = navController, route = Routes.EDIT_USER, icon = Icons.Filled.Edit, contentDescription = "Edit button") } }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding).fillMaxSize().fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Your information",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(34.dp)) }
            item {
                userData?.let { user ->
                    UserInfoCard(
                        age = user.age,
                        gender = user.gender,
                        height = user.height,
                        weight = user.weight,
                        bmi = user.bmi
                    )
                } ?: run {
                    ErrorCard(
                        errorMessage = "User data not available",
                        onDismiss = { viewModel.showError = false }
                    )
                }
            }
        }
    }
}