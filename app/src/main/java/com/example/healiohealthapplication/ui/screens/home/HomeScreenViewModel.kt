package com.example.healiohealthapplication.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// import com.example.healiohealthapplication.data.repository.StepRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
) : ViewModel() {

    // --- appbar states ---
    var expanded by mutableStateOf(false)
        private set

    // --- steps state ---
    private val _stepCount = MutableStateFlow(0)
    val stepCount: StateFlow<Int> = _stepCount

    fun toggleExpanded() {
        expanded = !expanded
    }

    /*fun loadSteps(userId: String) {
        viewModelScope.launch {
            stepRepository.getStepData(userId) { data ->
                _stepCount.value = data?.steps ?: 0
            }
        }
    } */

    /* fun updateSteps(userId: String, steps: Int) {
        viewModelScope.launch {
            stepRepository.saveStepData(userId, steps) { success ->
                if (success) _stepCount.value = steps
            }
        }
    }*/
}
