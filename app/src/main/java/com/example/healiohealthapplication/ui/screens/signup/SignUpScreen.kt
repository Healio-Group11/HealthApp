package com.example.healiohealthapplication.ui.screens.signup

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

@Composable
fun SignUpScreen(navController: NavController, modifier: Modifier, viewModel: SignUpViewModel) {
    Scaffold(
        topBar = { TopNavBar("Sign Up", navController) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Text(
            text = "Sign Up Screen!",
            modifier = Modifier.padding(innerPadding)
        )
        LoginAndSignUpOutlinedTextField(
            value = viewModel.currentEmail,
            onValueChange = { viewModel.currentEmail = it },
            label = "Email",
            keyboardType= KeyboardType.Email,
            leadingIcon = Icons.Filled.Email,
            iconContentDescription = "description"
        )
        LoginAndSignUpOutlinedTextField(
            value = viewModel.currentPassword,
            onValueChange = { viewModel.currentPassword = it },
            label = "Password",
            keyboardType= KeyboardType.Password,
            leadingIcon = Icons.Filled.Lock,
            iconContentDescription = "description"
        )
        Button(
            onClick = { viewModel.register(viewModel.currentEmail, viewModel.currentPassword) }
        ) {
            Text(text = "Sign Up")
        }
    }
}