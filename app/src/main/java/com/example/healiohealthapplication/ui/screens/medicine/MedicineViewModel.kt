package com.example.healiohealthapplication.ui.screens.medicine

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.healiohealthapplication.data.models.Medicine
import com.example.healiohealthapplication.data.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val repository: MedicineRepository
) : ViewModel() {

    private val _medicines = MutableStateFlow<List<Medicine>>(emptyList())
    val medicines: StateFlow<List<Medicine>> = _medicines

    private val _medicine = MutableStateFlow<Medicine?>(null) // To store a single medicine
    val medicine: StateFlow<Medicine?> = _medicine

    private var userId: String = ""

    fun setUserId(id: String) {
        userId = id
    }

    // Fetch a single medicine by ID
    fun getMedicineById(medicineId: String) {
        if (userId.isNotEmpty()) {
            repository.getMedicineById(userId, medicineId) { medicine ->
                _medicine.value = medicine // Update state flow
            }
        } else {
            _medicine.value = null // Set to null if userId is empty
        }
    }

    // Add a new medicine
    fun addMedicine(medicine: Medicine, onComplete: (Boolean) -> Unit) {
        if (userId.isNotEmpty()) {
            repository.addMedicine(userId, medicine) { success ->
                if (success) {
                    fetchMedicines() // Refresh after adding
                }
                onComplete(success)
            }
        } else {
            onComplete(false) // Could not add without a userId
        }
    }

    // Update existing medicine
    fun updateMedicine(medicine: Medicine, onComplete: (Boolean) -> Unit) {
        if (userId.isNotEmpty()) {
            repository.updateMedicine(userId, medicine) { success ->
                onComplete(success)
            }
        } else {
            onComplete(false)
        }
    }


    // Delete a medicine
    fun deleteMedicine(medicineId: String, onComplete: (Boolean) -> Unit) {
        if (userId.isNotEmpty()) {
            repository.deleteMedicine(userId, medicineId) { success ->
                if (success) {
                    fetchMedicines() // Refresh after deletion
                }
                onComplete(success)
            }
        } else {
            onComplete(false) // Handle the case when userId is empty
        }
    }

    // Fetch all medicines
    fun fetchMedicines() {
        if (userId.isNotEmpty()) {
            repository.getMedicines(userId) { fetchedMedicines ->
                _medicines.value = fetchedMedicines
            }
        }
    }

}


