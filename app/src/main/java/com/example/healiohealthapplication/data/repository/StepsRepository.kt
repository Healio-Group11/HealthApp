package com.example.healiohealthapplication.data.repository

import com.example.healiohealthapplication.data.models.Steps
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class StepRepository @Inject constructor() {

    private val db = FirebaseFirestore.getInstance()

    private fun getTodayDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    fun saveStepData(userId: String, steps: Int, onResult: (Boolean) -> Unit) {
        val date = getTodayDate()
        val stepData = Steps(userId = userId, date = date, steps = steps)

        db.collection("steps")
            .document("$userId-$date")
            .set(stepData)
            .addOnSuccessListener {
                Log.d("StepRepository", "Step data saved successfully")
                onResult(true)
            }
            .addOnFailureListener { e ->
                Log.w("StepRepository", "Error saving step data", e)
                onResult(false)
            }
    }

    fun getStepData(userId: String, onResult: (Steps?) -> Unit) {
        val date = getTodayDate()

        db.collection("steps")
            .document("$userId-$date")
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val stepData = document.toObject(Steps::class.java)
                    Log.d("StepRepository", "Step data retrieved: $stepData")
                    onResult(stepData)
                } else {
                    Log.d("StepRepository", "No step data found")
                    onResult(null)
                }
            }
            .addOnFailureListener { e ->
                Log.w("StepRepository", "Error getting step data", e)
                onResult(null)
            }
    }
}