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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StepsViewModel @Inject constructor(
    private val firestoreRepository: StepsRepository,
    app: Application
) : ViewModel() {
    val stepCounter = StepCounter(app.applicationContext)
    private var userId: String? = null
    private val _stepsData = MutableStateFlow<Steps?>(null)
    val stepsData: StateFlow<Steps?> = _stepsData // to be accessed from UI

    var currentStepGoal by mutableStateOf<Int?>(0)
    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress

    init {
        stepCounter.startListening()
        collectSteps()
    }

    // gets steps from the stepCounter and updates firestore using updateStepsTakenData
    private fun collectSteps() {
        Log.d("StepsViewModel", "collectSteps() started") // this gets called
        viewModelScope.launch { // cancels when viewmodel is cleared
            stepCounter.stepCount.collect { newSteps ->
                Log.d("StepsViewModel", "New steps detected: $newSteps") // never gets logged
                updateStepsTakenData(userId = "USER_ID", newSteps)
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
        firestoreRepository.getStepData(userId) { steps ->
            if (steps != null) {
                _stepsData.value = Steps(dailyStepsTaken = steps.dailyStepsTaken, dailyStepGoal = steps.dailyStepGoal)
                updateStepsProgress()
            } else {
                Log.e("Firestore", "No steps data found or failed to fetch")
            }
        }
    }

    // adds the empty daily steps and a set step goal into firestore
    fun initializeStepsData(userId: String, dailyStepGoal: Int = 10000) {
        firestoreRepository.createStepsData(userId, dailyStepGoal) { success ->
            if (success) {
                _stepsData.value = Steps(dailyStepsTaken = 0, dailyStepGoal = dailyStepGoal)
            }
        }
    }

    // update daily steps in firestore that have been taken so far
    fun updateStepsTakenData(userId: String, newSteps: Int) {
        // count the daily steps taken so far
        val currentSteps = _stepsData.value?.dailyStepsTaken ?: 0
        val updatedSteps = currentSteps + newSteps

        // update steps in firestore
        firestoreRepository.updateStepsData(userId, currentSteps) { success ->
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
}