package com.example.healiohealthapplication.ui.screens.diet

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healiohealthapplication.data.models.Diet
import com.example.healiohealthapplication.data.repository.DietRepository
import com.example.healiohealthapplication.utils.Permissions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DietViewModel @Inject constructor(
    private val dietRepository: DietRepository,
    private val permissions: Permissions
) : ViewModel() {

    // --- STATE: the Diet for the "current" date ---
    private val _currentDiet = MutableStateFlow<Diet?>(null)
    val currentDiet: StateFlow<Diet?> = _currentDiet.asStateFlow()

    // --- STATE: all Diet entries for the user ---
    private val _allDiets = MutableStateFlow<Map<String, Diet>>(emptyMap())
    val allDiets: StateFlow<Map<String, Diet>> = _allDiets.asStateFlow()

    // -- STATE: permissions for notifications
    private val _hasPermission = MutableStateFlow(false)
    val hasPermission: StateFlow<Boolean> = _hasPermission

    // --- Load the diet for a specific date (e.g. "2025-04-18") ---
    fun loadDietForDate(userId: String, date: String = LocalDate.now().toString()) {
        viewModelScope.launch {
            dietRepository.getDietForDate(userId, date) { diet ->
                _currentDiet.value = diet ?: Diet(id = date)
            }
        }
    }

    // --- Save or update the diet for a specific date ---
    fun saveDietForDate(userId: String, date: String = LocalDate.now().toString(), updated: Diet) {
        viewModelScope.launch {
            dietRepository.saveDietForDate(userId, date, updated) { success ->
                if (success) {
                    _currentDiet.value = updated
                    // also reflect in allDiets map
                    _allDiets.update { map -> map + (date to updated) }
                }
            }
        }
    }

    // --- Load all diet entries for the user ---
    fun loadAllDiets(userId: String) {
        viewModelScope.launch {
            dietRepository.getAllDietEntries(userId) { map ->
                _allDiets.value = map
            }
        }
    }

    // --- Delete the diet entry for a given date ---
    fun deleteDietForDate(userId: String, date: String) {
        viewModelScope.launch {
            dietRepository.deleteDietForDate(userId, date) { success ->
                if (success) {
                    _allDiets.update { it - date }
                    if (_currentDiet.value?.id == date) {
                        _currentDiet.value = null
                    }
                }
            }
        }
    }

    // --- Increment only waterIntake on the *current* date ---
    fun incrementWaterIntake(amount: Int = 250, userId: String) {
        val date = _currentDiet.value?.id ?: LocalDate.now().toString()
        val current = _currentDiet.value ?: Diet(id = date)
        val updated = current.copy(waterIntake = current.waterIntake + amount)
        saveDietForDate(userId, date, updated)
    }

    fun checkNotificationPermission() {
        Log.e("DietViewModel","Checking for notification permission from phone")
        _hasPermission.value = permissions.hasNotificationPermission()
        Log.e("DietViewModel","Notification permission from check set to: ${_hasPermission.value}")
    }

    fun setHasNotificationPermission(granted: Boolean) {
        Log.e("DietViewModel","setting notification permission to $granted with setter" )
        _hasPermission.value = granted
    }
}
