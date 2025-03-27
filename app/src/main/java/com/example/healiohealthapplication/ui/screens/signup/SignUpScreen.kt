package com.example.healiohealthapplication.ui.screens.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.LoginAndSignUpOutlinedTextField
import com.example.healiohealthapplication.ui.components.TopNavBar
import com.example.healiohealthapplication.ui.screens.shared.SharedViewModel

@Composable
fun SignUpScreen(navController: NavController, modifier: Modifier, viewModel: SignUpViewModel, sharedViewModel: SharedViewModel) {
    Scaffold(
        topBar = { TopNavBar("Sign Up", navController) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            Text(
                text = "Sign Up Screen!",
            )
            LoginAndSignUpOutlinedTextField(
                value = viewModel.currentEmail,
                onValueChange = { viewModel.currentEmail = it },
                label = "Email",
                keyboardType = KeyboardType.Email,
                leadingIcon = Icons.Filled.Email,
                iconContentDescription = "description"
            )
            LoginAndSignUpOutlinedTextField(
                value = viewModel.currentPassword,
                onValueChange = { viewModel.currentPassword = it },
                label = "Password",
                keyboardType = KeyboardType.Password,
                leadingIcon = Icons.Filled.Lock,
                iconContentDescription = "description"
            )
            Button(
                onClick = {
                    viewModel.register(navController, viewModel.currentEmail, viewModel.currentPassword) { userId ->
                        sharedViewModel.fetchUserData(userId)
                    }
                }
            ) {
                Text(text = "Sign Up")
            }
        }
    }
}