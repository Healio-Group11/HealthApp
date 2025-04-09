package com.example.healiohealthapplication.data.repository

import com.example.healiohealthapplication.data.models.Medicine
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject

class MedicineRepository @Inject constructor() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Add a medicine to Firestore under a specific user's collection
    fun addMedicine(userId: String, medicine: Medicine, onResult: (Boolean) -> Unit) {
        val collection = db.collection("user").document(userId).collection("medicine")
        val document = collection.document() // Use the correct user-specific path
        val medicineWithId = medicine.copy(id = document.id)
        document.set(medicineWithId)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener {
                onResult(false)
                // Optionally log the error for better debugging
                // Log.e("MedicineRepository", "Failed to add medicine", it)
            }
    }

    // Fetch all medicines for a specific user
    fun getMedicines(userId: String, onResult: (List<Medicine>) -> Unit) {
        db.collection("user").document(userId)
            .collection("medicine")
            .get()
            .addOnSuccessListener { snapshot ->
                val medicines = snapshot.documents.mapNotNull { it.toObject<Medicine>() }
                onResult(medicines)
            }
            .addOnFailureListener {
                onResult(emptyList()) // Return an empty list in case of failure
                // Optionally log the error for better debugging
                // Log.e("MedicineRepository", "Failed to fetch medicines", it)
            }
    }

    // Fetch a specific medicine by its ID
    fun getMedicineById(userId: String, medicineId: String, onResult: (Medicine?) -> Unit) {
        db.collection("user").document(userId)
            .collection("medicine").document(medicineId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val medicine = document.toObject<Medicine>()
                    onResult(medicine)
                } else {
                    onResult(null) // Medicine not found
                }
            }
            .addOnFailureListener {
                onResult(null) // Return null in case of failure
                // Optionally log the error for better debugging
                // Log.e("MedicineRepository", "Failed to fetch medicine", it)
            }
    }

    // Update a specific medicine
    fun updateMedicine(userId: String, medicine: Medicine, onResult: (Boolean) -> Unit) {
        db.collection("user").document(userId)
            .collection("medicine").document(medicine.id ?: "")
            .set(medicine)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener {
                onResult(false)
                // Optionally log the error for better debugging
                // Log.e("MedicineRepository", "Failed to update medicine", it)
            }
    }

    // Delete a specific medicine
    fun deleteMedicine(userId: String, medicineId: String, onResult: (Boolean) -> Unit) {
        db.collection("user").document(userId)
            .collection("medicine").document(medicineId)
            .delete()
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener {
                onResult(false)
                // Optionally log the error for better debugging
                // Log.e("MedicineRepository", "Failed to delete medicine", it)
            }
    }
}
