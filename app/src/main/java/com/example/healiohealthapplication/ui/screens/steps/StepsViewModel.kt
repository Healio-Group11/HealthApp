package com.example.healiohealthapplication.ui.screens.steps

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.healiohealthapplication.data.models.Steps
import com.example.healiohealthapplication.data.repository.StepsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class StepsViewModel @Inject constructor(
    private val firestoreRepository: StepsRepository,
    app: Application // should
) : ViewModel() {
    // private val stepCounter = StepCounter(app.applicationContext)

    private val _stepsData = MutableStateFlow<Steps?>(null)
    val stepsData: StateFlow<Steps?> = _stepsData // to be accessed from UI

    // how do I get the userId in the easiest possible way?

    // on initialization
    /*
    init {
        stepCounter.startListening()
        collectSteps()
    } */

    // gets steps from the stepCounter and updates firestore using updateStepsTakenData
    /* private fun collectSteps() {
        viewModelScope.launch { // cancels when viewmodel is cleared
            stepCounter.stepCount.collect { newSteps ->
                updateStepsTakenData(userId = "USER_ID", newSteps)
            }
        }
    } */

    // adds the empty daily steps and a set step goal into firestore
    // to be called when user first registers and every 24hrs when the steps reset
    fun initializeStepsData(userId: String, dailyStepGoal: Int = 10000) {
        firestoreRepository.createNewStepsCollection(userId, dailyStepGoal) { success ->
            if (success) {
                _stepsData.value = Steps(dailyStepsTaken = 0, dailyStepGoal = dailyStepGoal)
            }
        }
    }

    // update daily steps that have been taken so far
    private fun updateStepsTakenData(userId: String, newSteps: Int) {
        // count the daily steps taken so far
        val currentSteps = _stepsData.value?.dailyStepsTaken ?: 0
        val updatedSteps = currentSteps + newSteps

        // update steps in firestore
        firestoreRepository.updateStepsData(userId, updatedSteps) { success ->
            if (success) {
                _stepsData.value = _stepsData.value?.copy(dailyStepsTaken = updatedSteps)
            }
        }
    }

    /*
    // updates the step goal
    fun updateDailyStepGoal() {
        stepCounter.startListening()
        collectSteps()
    } */
}