package com.example.healiohealthapplication.ui.screens.workout

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healiohealthapplication.data.models.Workout
import com.example.healiohealthapplication.data.repository.WorkoutsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WorkoutViewModel(private val workoutsRepository: WorkoutsRepository) : ViewModel() {

    // Mutable states for workouts and loading state using StateFlow
    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workouts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Fetch workouts for the user
    fun fetchWorkouts(userId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            workoutsRepository.getWorkouts(userId) { result ->
                _isLoading.value = false
                if (result != null) {
                    _workouts.value = result
                } else {
                    _errorMessage.value = "Failed to load workouts."
                }
            }
        }
    }

    // Save a new workout
    fun saveWorkout(userId: String, workout: Workout, onResult: (Boolean) -> Unit) {
        workoutsRepository.saveWorkout(userId, workout) { success ->
            onResult(success)
            if (success) {
                fetchWorkouts(userId)  // Refresh workouts after adding
            } else {
                _errorMessage.value = "Failed to save workout."
            }
        }
    }

    // Delete a workout
    fun deleteWorkout(userId: String, workoutId: String, onResult: (Boolean) -> Unit) {
        workoutsRepository.deleteWorkout(userId, workoutId) { success ->
            onResult(success)
            if (success) {
                _workouts.value = _workouts.value.filterNot { it.id == workoutId } // Remove workout from list
            } else {
                _errorMessage.value = "Failed to delete workout."
            }
        }
    }

    // Update an existing workout
    fun updateWorkout(userId: String, workoutId: String, updatedWorkout: Workout, onResult: (Boolean) -> Unit) {
        workoutsRepository.updateWorkout(userId, workoutId, updatedWorkout) { success ->
            onResult(success)
            if (success) {
                fetchWorkouts(userId)  // Refresh workouts after update
            } else {
                _errorMessage.value = "Failed to update workout."
            }
        }
    }
}
