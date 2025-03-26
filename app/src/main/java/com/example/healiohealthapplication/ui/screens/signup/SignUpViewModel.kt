package com.example.healiohealthapplication.ui.screens.signup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.healiohealthapplication.data.models.User
import com.example.healiohealthapplication.data.repository.AuthRepository
import com.example.healiohealthapplication.data.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    // -- user data --
    private val _userData = MutableStateFlow<User?>(null)
    val userData: StateFlow<User?> = _userData // can be accessed from UI

    // -- states for current ui screen --
    var currentEmail by mutableStateOf("")
    var currentPassword by mutableStateOf("")

    // saves the user both in firebase authentication and in firestore (since the user has more information on it too)
    fun register(email: String, password: String) {
        authRepository.register(email, password) { success -> // creates a new user in firebase authentication
            if (success) {
                authRepository.getCurrentUserId()?.let { userId -> // if successful, gets the current user's user id
                    firestoreRepository.saveUserData(userId, email) { saveSuccess -> // and saves the user data in firestore
                        if (saveSuccess) Log.d("SignUpViewModel", "message for now") // if successful, fetches the data of the current user
                    }
                }
            }
        }
    }

    // if (saveSuccess) fetchUserData(userId) // if successful, fetches the data of the current user

    /*private fun fetchUserData(userId: String) {
        firestoreRepository.getUserData(userId) { data ->
            _userData.value = data
        }
    } */
}