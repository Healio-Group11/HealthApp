package com.example.healiohealthapplication.ui.screens.login

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.healiohealthapplication.data.repository.AuthRepository
import com.example.healiohealthapplication.navigation.Routes
import com.example.healiohealthapplication.utils.StepCounter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val stepCounter: StepCounter
) : ViewModel() {
    // -- states for current ui screen --
    var currentEmail by mutableStateOf("")
    var currentPassword by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var showError by mutableStateOf(false)

    fun login(navController: NavController, email: String, password: String, onLoginSuccess: (String) -> Unit) {
        authRepository.login(email, password) { success, error ->
            if (success) {
                authRepository.getCurrentUserId()?.let { userId ->
                    onLoginSuccess(userId)
                    navController.navigate(Routes.HOME)
                        stepCounter.startListening()
                }
            } else {
                errorMessage = error ?: "Registration failed."
                showError = true
            }
        }
    }
}