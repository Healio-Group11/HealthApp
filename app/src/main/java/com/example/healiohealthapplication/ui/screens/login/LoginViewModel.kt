package com.example.healiohealthapplication.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.healiohealthapplication.data.models.User
import com.example.healiohealthapplication.data.repository.AuthRepository
import com.example.healiohealthapplication.data.repository.FirestoreRepository
import com.example.healiohealthapplication.navigation.Routes
import com.example.healiohealthapplication.ui.screens.shared.SharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    // -- user data --
    private val _userData = MutableStateFlow<User?>(null)
    val userData: StateFlow<User?> = _userData // can be accessed from UI (holds all user info for specific userId)

    // -- states for current ui screen --
    var currentEmail by mutableStateOf("")
    var currentPassword by mutableStateOf("")

    fun login(navController: NavController, email: String, password: String) {
        authRepository.login(email, password) { success ->
            if (success) {
                authRepository.getCurrentUserId()?.let { userId ->
                    fetchUserData(userId)
                    navController.navigate(Routes.HOME)
                }
            }
        }
    }

    private fun fetchUserData(userId: String) {
        firestoreRepository.getUserData(userId) { data ->
            _userData.value = data
        }
    }
}