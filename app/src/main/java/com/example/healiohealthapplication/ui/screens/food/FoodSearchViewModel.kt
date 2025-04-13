package com.example.healiohealthapplication.ui.screens.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healiohealthapplication.data.models.FoodProduct
import com.example.healiohealthapplication.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodSearchViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _foodResults = MutableStateFlow<List<FoodProduct>>(emptyList())
    val foodResults: StateFlow<List<FoodProduct>> = _foodResults

    fun searchFood(foodName: String) {
        viewModelScope.launch {
            _foodResults.value = foodRepository.searchFood(foodName)
        }
    }
}
