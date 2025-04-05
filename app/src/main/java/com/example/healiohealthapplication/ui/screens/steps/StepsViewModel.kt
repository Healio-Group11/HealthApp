package com.example.healiohealthapplication.ui.screens.steps

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healiohealthapplication.data.models.Steps
import com.example.healiohealthapplication.data.repository.StepsRepository
import com.example.healiohealthapplication.utils.StepCounter
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StepsViewModel @Inject constructor(
    private val firestoreRepository: StepsRepository,
    app: Application
) : ViewModel() {
    private val stepCounter = StepCounter(app.applicationContext)
    var currentStepGoal by mutableStateOf<Int?>(0)

    var userId: String? = null

    private var lastResetDate: String? = null

    private val _stepsData = MutableStateFlow<Steps?>(null)
    val stepsData: StateFlow<Steps?> = _stepsData // to be accessed from UI
    private var lastStepCount = 0

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress

    init {
        stepCounter.startListening()
    }

    // gets steps from the stepCounter and updates firestore using updateStepsTakenData
    private fun collectSteps() {
        userId?.let { id ->
            viewModelScope.launch { // cancels when viewmodel is cleared
                stepCounter.stepCount.collect { newTotalSteps ->
                    val today = getTodayDate()
                    if (lastResetDate != today) {
                        Log.d("StepsViewModel", "New day detected. Resetting step count.")
                        lastResetDate = today
                        resetStepsForNewDay(userId = id)
                    }
                    val stepsDifference = newTotalSteps - lastStepCount
                    if (stepsDifference > 0) {
                        Log.d("StepsViewModel", "New steps detected: $stepsDifference")
                        updateStepsTakenData(userId = id, stepsDifference)
                        lastStepCount = newTotalSteps
                    }
                }
            }
        }
    }

    // calculates how many steps walked / the goal
    private fun updateStepsProgress() {
        val stepsTaken = stepsData.value?.dailyStepsTaken ?: 0
        val stepGoal = stepsData.value?.dailyStepGoal ?: 10000
        _progress.value = if (stepGoal > 0) (stepsTaken.toFloat() / stepGoal).coerceIn(0f, 1f) else 0f
    }

    // get current step goal and steps if they exist
    fun getCurrentStepData(userId: String) {
        collectSteps()
        firestoreRepository.getStepData(userId) { steps ->
            if (steps != null) {
                _stepsData.value = Steps(dailyStepsTaken = steps.dailyStepsTaken, dailyStepGoal = steps.dailyStepGoal)
                currentStepGoal = steps.dailyStepGoal
                updateStepsProgress()
            } else {
                initializeStepsData(userId)
            }
        }
    }

    // adds the empty daily steps and a set step goal into firestore
    private fun initializeStepsData(userId: String, dailyStepGoal: Int = 10000) {
        firestoreRepository.createStepsData(userId, dailyStepGoal) { success ->
            if (success) {
                _stepsData.value = Steps(dailyStepsTaken = 0, dailyStepGoal = dailyStepGoal)
            }
        }
    }

    // update daily steps in firestore that have been taken so far
    private fun updateStepsTakenData(userId: String, newSteps: Int) {
        val currentSteps = _stepsData.value?.dailyStepsTaken ?: 0
        val updatedSteps = currentSteps + newSteps

        // update steps in firestore
        firestoreRepository.updateStepsData(userId, updatedSteps) { success ->
            if (success) {
                _stepsData.value = _stepsData.value?.copy(dailyStepsTaken = updatedSteps)
                updateStepsProgress()
            }
        }
    }

    // updates the step goal
    fun updateDailyStepGoal(userId: String, stepGoal: Int) {
        firestoreRepository.updateStepsData(userId, stepGoal = stepGoal) { success ->
            if (success) {
                _stepsData.value = _stepsData.value?.copy(dailyStepGoal = stepGoal)
                updateStepsProgress()
            }
        }
    }

    // resets all steps
    private fun resetStepsForNewDay(userId: String) {
        lastStepCount = 0
        _stepsData.value = _stepsData.value?.copy(dailyStepsTaken = 0)
        firestoreRepository.updateStepsData(userId, 0) { success ->
            if (success) {
                updateStepsProgress()
            }
        }
    }

    // gets today's date
    private fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // Format: "2025-04-05"
        return dateFormat.format(Date())
    }
}