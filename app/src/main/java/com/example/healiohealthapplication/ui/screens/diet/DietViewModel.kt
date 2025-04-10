package com.example.healiohealthapplication.ui.screens.diet

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.healiohealthapplication.data.models.Diet
import com.example.healiohealthapplication.data.repository.DietRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class DietViewModel @Inject constructor(
    private val dietRepository: DietRepository
) : ViewModel() {

    private val _diet = MutableStateFlow(Diet())
    val diet: StateFlow<Diet> = _diet

    fun loadDietData(userId: String) {
        viewModelScope.launch {
            dietRepository.getDietData(userId) { data ->
                data?.let {
                    _diet.value = it
                }
            }
        }
    }

    fun updateDiet(userId: String, updatedDiet: Diet) {
        viewModelScope.launch {
            dietRepository.saveDietData(userId, updatedDiet) { success ->
                if (success) {
                    _diet.value = updatedDiet
                }
            }
        }
    }

    // Optional: if you want to update only water intake
    fun incrementWaterIntake(userId: String, amount: Int = 250) {
        val current = _diet.value
        val newDiet = current.copy(waterIntake = current.waterIntake + amount)
        updateDiet(userId, newDiet)
    }

}
