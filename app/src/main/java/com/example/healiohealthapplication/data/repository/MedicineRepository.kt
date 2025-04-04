package com.example.healiohealthapplication.data.repository

import com.example.healiohealthapplication.data.models.Medicine
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class MedicineRepository @Inject constructor() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Add a medicine to Firestore
    fun addMedicine(medicine: Medicine, onResult: (Boolean) -> Unit) {
        val document = db.collection("medicines").document()
        val medicineWithId = medicine.copy(id = document.id)
        document.set(medicineWithId)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    // Fetch all medicines from Firestore
    fun getMedicines(onResult: (List<Medicine>) -> Unit) {
        db.collection("medicines").get()
            .addOnSuccessListener { snapshot ->
                val medicines = snapshot.documents.mapNotNull { it.toObject(Medicine::class.java) }
                onResult(medicines)
            }
            .addOnFailureListener {
                onResult(emptyList()) // If it fails, return an empty list
            }
    }

}
