package com.example.healiohealthapplication.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.healiohealthapplication.data.repository.AuthRepository
import com.example.healiohealthapplication.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    // -- states for current ui screen --
    var currentEmail by mutableStateOf("")
    var currentPassword by mutableStateOf("")

    fun login(navController: NavController, email: String, password: String, onLoginSuccess: (String) -> Unit) {
        authRepository.login(email, password) { success ->
            if (success) {
                authRepository.getCurrentUserId()?.let { userId ->
                    onLoginSuccess(userId)
                    navController.navigate(Routes.HOME)
                }
            }
        }
    }
}