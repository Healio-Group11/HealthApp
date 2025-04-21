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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healiohealthapplication.R
import com.example.healiohealthapplication.ui.components.BigButton
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.ErrorCard
import com.example.healiohealthapplication.ui.components.TopNavBar
import com.example.healiohealthapplication.ui.screens.shared.SharedViewModel

@Composable
fun EditUserScreen(navController: NavController, viewModel: UserViewModel, sharedViewModel: SharedViewModel) {
    Scaffold(
        topBar = { TopNavBar(stringResource(R.string.edit_user_top_nav_bar_title), navController) },
        bottomBar = { BottomNavBar(navController) },
    ) { innerPadding ->
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
        LazyColumn(
            modifier = Modifier.padding(innerPadding).fillMaxSize().fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = stringResource(R.string.edit_user_screen_title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(34.dp)) }
            item {
                InfoInputOutlinedTextField(viewModel.age, { viewModel.age = it },
                    stringResource(R.string.edit_user_screen_outlined_text_field_label_age))
                GenderDropdown(
                    selectedGender = viewModel.gender,
                    expanded = viewModel.isGenderDropdownExpanded,
                    genderOptions = viewModel.genderOptions,
                    onExpandChanged = { viewModel.isGenderDropdownExpanded = it },
                    onGenderSelected = { viewModel.gender = it },
                    viewModel = viewModel
                )
                InfoInputOutlinedTextField(
                    viewModel.height,
                    { viewModel.height = it },
                    stringResource(R.string.edit_user_screen_outlined_text_field_label_height)
                )
                InfoInputOutlinedTextField(
                    viewModel.weight,
                    { viewModel.weight = it },
                    stringResource(R.string.edit_user_screen_outlined_text_field_label_weight)
                )
            }
            item {
                viewModel.showEditUserError.takeIf { it }?.let {
                    ErrorCard(
                        errorMessage = viewModel.errorMessageEditUser,
                        onDismiss = { viewModel.showEditUserError = false }
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(34.dp)) }
            item {
                BigButton(
                    text = stringResource(R.string.edit_user_screen_button_text),
                    onClick = { viewModel.updateUserInfo(userData?.id ?: "", navController ) { updatedUser ->
                        sharedViewModel.fetchUserData(updatedUser.id)
                    } }
                )
            }
        }
    }
}