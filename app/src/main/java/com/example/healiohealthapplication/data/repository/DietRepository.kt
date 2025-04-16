package com.example.healiohealthapplication.data.repository

import android.util.Log
import com.example.healiohealthapplication.data.models.Diet
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class DietRepository @Inject constructor() {

    private val db = FirebaseFirestore.getInstance()

    fun saveDietData(userId: String, diet: Diet, onResult: (Boolean) -> Unit) {
        db.collection("users")
            .document(userId)
            .set(diet)
            .addOnSuccessListener {
                Log.d("DietRepository", "Diet data saved!")
                onResult(true)
            }
            .addOnFailureListener {
                Log.e("DietRepository", "Failed to save diet data", it)
                onResult(false)
            }
    }

    fun getDietData(userId: String, onResult: (Diet?) -> Unit) {
        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val diet = document.toObject(Diet::class.java)
                    onResult(diet)
                } else {
                    onResult(null)
                }
            }
            .addOnFailureListener {
                Log.e("DietRepository", "Failed to get diet data", it)
                onResult(null)
            }
    }
}
