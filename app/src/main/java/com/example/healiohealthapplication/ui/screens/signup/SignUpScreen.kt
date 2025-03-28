package com.example.healiohealthapplication.ui.screens.signup

import androidx.compose.foundation.background
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
fun SignUpScreen(navController: NavController, modifier: Modifier, viewModel: SignUpViewModel, sharedViewModel: SharedViewModel) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize().padding(16.dp).background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Let's help you meet your goal",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(34.dp))
            LoginAndSignUpOutlinedTextField(
                value = viewModel.currentEmail,
                onValueChange = { viewModel.currentEmail = it },
                label = "Email",
                keyboardType = KeyboardType.Email,
                leadingIcon = Icons.Filled.Email,
                iconContentDescription = "description",
            )
            LoginAndSignUpOutlinedTextField(
                value = viewModel.currentPassword,
                onValueChange = { viewModel.currentPassword = it },
                label = "Password",
                keyboardType = KeyboardType.Password,
                isPassword = true,
                leadingIcon = Icons.Filled.Lock,
                iconContentDescription = "description",
            )
            Text(
                text = "• Password must contain at least 6 characters",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.Start).padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )
            Spacer(modifier = Modifier.height(34.dp))
            BigButton(
                text = "Sign Up",
                onClick = {
                    viewModel.register(
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
                text = "Already have an account?",
                linkText = "Log in"
            ) { navController.navigate(Routes.LOGIN) }
        }
    }
}