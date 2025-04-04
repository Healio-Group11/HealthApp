package com.example.healiohealthapplication.ui.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.healiohealthapplication.data.repository.AuthRepository
import com.example.healiohealthapplication.data.repository.UserRepository
import com.example.healiohealthapplication.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreRepository: UserRepository
) : ViewModel() {
    // -- states for current ui screen --
    var currentEmail by mutableStateOf("")
    var currentPassword by mutableStateOf("")

    // saves the user both in firebase authentication and in firestore (since the user has more information on it too)
    fun register(navController: NavController, email: String, password: String, onSignUpSuccess: (String) -> Unit) {
        authRepository.register(email, password) { success -> // creates a new user in firebase authentication
            if (success) {
                authRepository.getCurrentUserId()?.let { userId -> // if successful, gets the current user's user id
                    firestoreRepository.saveUserData(userId, email) { saveSuccess -> // and saves the user data in firestore
                        if (saveSuccess) {
                            onSignUpSuccess(userId) // if successful, fetches the data of the current user
                            navController.navigate(Routes.HOME) // and moves the user into HomeScreen
                        }
                    }
                }
            }
        }
    }
}