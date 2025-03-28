package com.example.healiohealthapplication.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healiohealthapplication.navigation.Routes
import com.example.healiohealthapplication.ui.components.BigButton
import com.example.healiohealthapplication.ui.components.ChangePageText
import com.example.healiohealthapplication.ui.components.LoginAndSignUpOutlinedTextField
import com.example.healiohealthapplication.ui.screens.shared.SharedViewModel

@Composable
fun LoginScreen(navController: NavController, modifier: Modifier, viewModel: LoginViewModel, sharedViewModel: SharedViewModel) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize().fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome back!",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(34.dp))
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
                isPassword = true,
                leadingIcon = Icons.Filled.Lock,
                iconContentDescription = "description"
            )
            Spacer(modifier = Modifier.height(34.dp))
            BigButton(
                text = "Log In",
                onClick = {
                    viewModel.login(
                        navController,
                        viewModel.currentEmail,
                        viewModel.currentPassword
                    ) { userId ->
                        sharedViewModel.fetchUserData(userId)
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ChangePageText(
                text = "Don't have an account?",
                linkText = "Sign up"
            ) { navController.navigate(Routes.SIGNUP) }
        }
    }
}