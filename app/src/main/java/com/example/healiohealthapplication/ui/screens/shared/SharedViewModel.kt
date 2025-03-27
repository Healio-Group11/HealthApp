package com.example.healiohealthapplication.ui.screens.shared

import androidx.lifecycle.ViewModel
import com.example.healiohealthapplication.data.models.User
import com.example.healiohealthapplication.data.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    // -- STATES --
    // -- user data --
    private val _userData = MutableStateFlow<User?>(null)
    val userData: StateFlow<User?> = _userData // can be accessed from UI (holds all user info for specific userId)

    // -- FUNCTIONS --
    fun fetchUserData(userId: String) {
        firestoreRepository.getUserData(userId) { data ->
            _userData.value = data
        }
    }
}