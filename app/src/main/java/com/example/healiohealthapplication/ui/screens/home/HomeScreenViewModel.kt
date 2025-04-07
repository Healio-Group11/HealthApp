package com.example.healiohealthapplication.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healiohealthapplication.data.models.Steps
import com.example.healiohealthapplication.data.repository.StepsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val stepsRepository: StepsRepository
) : ViewModel() {

    // --- appbar states ---
    var expanded by mutableStateOf(false)
        private set

    // --- steps state ---
    private val _stepCount = MutableStateFlow<Steps?>(null)
    val stepCount: StateFlow<Steps?> = _stepCount

    fun toggleExpanded() {
        expanded = !expanded
    }

    fun loadSteps(userId: String) {
        viewModelScope.launch {
            stepsRepository.getStepData(userId) { data ->
                _stepCount.value = data
            }
        }
    }

    // the user cannot and should not update the steps!
}
