package com.example.healiohealthapplication.ui.screens.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    // -- user info --
    var userId: String? = null
    var age by mutableStateOf("")
    var gender by mutableStateOf("")
    var height by mutableStateOf("")
    var weight by mutableStateOf("")
    var bmi by mutableStateOf("")

    // -- for gender dropdown menu --
    var isGenderDropdownExpanded by mutableStateOf(false)
    val genderOptions = listOf("Male", "Female")
    var textFieldWidth by mutableStateOf(0.dp)
        private set

    // -- for user screen errors --
    var showUserDataError by mutableStateOf(true)
    var showUserError by mutableStateOf(false)
    var showEditUserError by mutableStateOf(false)
    var errorMessageEditUser by mutableStateOf("")

    fun updateUserInfo(userId: String, navController: NavController, onUserUpdated: (User) -> Unit) {
        val bmiResult = calculateUserBMI()

        if (!bmiResult) {
            showEditUserError = true
            return
        }

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
                age = updatedUser.age
                gender = updatedUser.gender
                height = updatedUser.height
                weight = updatedUser.weight
                onUserUpdated(updatedUser)
                navController.navigate(Routes.USER)
            } else {
                showUserError = true
            }
        }
    }

    // TODO: make the calculator more accurate?
    private fun calculateUserBMI(): Boolean {
        if (height.contains(".")) {
            bmi = "--"
            errorMessageEditUser = "Invalid height input. Input height using centimeters."
            return false
        }

        val heightInMeters = height.toFloatOrNull()?.div(100)
        val weight = weight.toFloatOrNull()

        if (heightInMeters != null && weight != null && heightInMeters > 0) {
            val calculatedBmi = weight / (heightInMeters * heightInMeters)
            bmi = String.format(Locale.US, "%.2f", calculatedBmi)
            return true
        } else {
            bmi = "--"
            errorMessageEditUser = "Invalid height or weight for BMI calculation"
            return false
        }
    }

    fun updateTextFieldWidth(newWidth: Dp) {
        textFieldWidth = newWidth
    }
}