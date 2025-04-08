package com.example.healiohealthapplication.ui.screens.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healiohealthapplication.ui.components.BigButton
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.TopNavBar
import com.example.healiohealthapplication.ui.screens.shared.SharedViewModel

@Composable
fun EditUserScreen(navController: NavController, viewModel: UserViewModel, sharedViewModel: SharedViewModel) {
    Scaffold(
        topBar = { TopNavBar("Profile", navController) },
        bottomBar = { BottomNavBar(navController) },
    ) { innerPadding ->
        val userData by sharedViewModel.userData.collectAsState()
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
                InfoInputOutlinedTextField(viewModel.age, {viewModel.age = it}, "age")
                InfoInputOutlinedTextField(viewModel.gender, {viewModel.age = it}, "gender")
                InfoInputOutlinedTextField(viewModel.height, {viewModel.age = it}, "height (cm)")
                InfoInputOutlinedTextField(viewModel.weight, {viewModel.age = it}, "weight (kg)")
            }
            item { Spacer(modifier = Modifier.height(34.dp)) }
            item {
                BigButton(
                    text = "Update info",
                    onClick = { viewModel.updateUserInfo(userData?.id ?: "", navController) }
                )
            }
        }
    }
}