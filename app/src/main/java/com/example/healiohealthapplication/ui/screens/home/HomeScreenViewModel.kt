package com.example.healiohealthapplication.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(

) : ViewModel() {
    // --- appbar states ---
    var expanded by mutableStateOf(false)
        private set

    // change main top app bar expanded state
    fun toggleExpanded() {
        expanded = !expanded
    }
}