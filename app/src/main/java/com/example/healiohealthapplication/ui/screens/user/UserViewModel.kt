package com.example.healiohealthapplication.ui.screens.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.healiohealthapplication.data.models.User
import com.example.healiohealthapplication.data.repository.UserRepository
import com.example.healiohealthapplication.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val firestoreRepository: UserRepository
) : ViewModel() {
    var userId: String? = null
    var age by mutableStateOf("")
    var gender by mutableStateOf("")
    var height by mutableStateOf("")
    var weight by mutableStateOf("")
    var bmi by mutableStateOf("")

    var showError by mutableStateOf(true)
    var showEditUserError by mutableStateOf(true)

    fun updateUserInfo(userId: String, navController: NavController) {
        calculateUserBMI()

        val updatedUser = User(
            id = userId,
            age = age,
            gender = gender,
            height = height,
            weight = weight,
            bmi = bmi
        )

        firestoreRepository.updateUserData(updatedUser) { success ->
            if (success) {
                navController.navigate(Routes.USER)
            } else {
                // show some kind of error
            }
        }
    }

    private fun calculateUserBMI() {
        val heightInMeters = height.toFloatOrNull()?.div(100)
        val weight = weight.toFloatOrNull()

        if (heightInMeters != null && weight != null && heightInMeters > 0) {
            val calculatedBmi = weight / (heightInMeters * heightInMeters)
            bmi = String.format(Locale.US, "%.2f", calculatedBmi)
        } else {
            bmi = "--"
            // show some kind of error
        }
    }
}