package com.example.healiohealthapplication.ui.screens.medicine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healiohealthapplication.data.models.Medicine
import com.example.healiohealthapplication.data.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val repository: MedicineRepository
) : ViewModel() {

    private val _medicines = MutableStateFlow<List<Medicine>>(emptyList())
    val medicines: StateFlow<List<Medicine>> = _medicines

    fun fetchMedicines() {
        repository.getMedicines { fetchedMedicines ->
            _medicines.value = fetchedMedicines
        }
    }

    fun addMedicine(medicine: Medicine, onComplete: (Boolean) -> Unit) {
        repository.addMedicine(medicine) { success ->
            if (success) {
                fetchMedicines() // Refresh list
            }
            onComplete(success)
        }
    }
}
