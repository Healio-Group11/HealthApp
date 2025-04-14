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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healiohealthapplication.R
import com.example.healiohealthapplication.navigation.Routes
import com.example.healiohealthapplication.ui.components.BigButton
import com.example.healiohealthapplication.ui.components.ChangePageText
import com.example.healiohealthapplication.ui.components.LoginAndSignUpOutlinedTextField
import com.example.healiohealthapplication.ui.screens.shared.SharedViewModel

@Composable
fun SignUpScreen(navController: NavController, modifier: Modifier, viewModel: SignUpViewModel, sharedViewModel: SharedViewModel) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.sign_up_screen_title),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(34.dp))
            LoginAndSignUpOutlinedTextField(
                value = viewModel.currentEmail,
                onValueChange = { viewModel.currentEmail = it },
                label = stringResource(R.string.sign_up_screen_outlined_text_field_label_email),
                keyboardType = KeyboardType.Email,
                leadingIcon = Icons.Filled.Email,
                iconContentDescription = stringResource(R.string.sign_up_screen_outlined_text_field_icon_desc_email),
            )
            LoginAndSignUpOutlinedTextField(
                value = viewModel.currentPassword,
                onValueChange = { viewModel.currentPassword = it },
                label = stringResource(R.string.sign_up_screen_outlined_text_field_label_password),
                keyboardType = KeyboardType.Password,
                isPassword = true,
                leadingIcon = Icons.Filled.Lock,
                iconContentDescription = stringResource(R.string.sign_up_screen_outlined_text_field_icon_desc_password),
            )
            Text(
                text = stringResource(R.string.sign_up_screen_password_requirements_text),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )
            Spacer(modifier = Modifier.height(34.dp))
            BigButton(
                text = stringResource(R.string.sign_up_screen_submit_button_text),
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
                text = stringResource(R.string.sign_up_screen_change_page_text),
                linkText = stringResource(R.string.sign_up_screen_change_page_link_text)
            ) { navController.navigate(Routes.LOGIN) }
        }
    }
}