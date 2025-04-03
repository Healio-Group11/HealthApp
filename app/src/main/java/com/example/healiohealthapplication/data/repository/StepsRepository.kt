package com.example.healiohealthapplication.data.repository

import com.example.healiohealthapplication.data.models.Steps
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class StepsRepository @Inject constructor() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // gets current steps and step goal
    fun getStepData(userId: String, onResult: (Steps?) -> Unit) {
        val stepsCollection = db.collection("users").document(userId).collection("steps")
        stepsCollection.get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) { // if there are documents in the collection
                    val latestStepsDocument = documents.documents.first()
                    val steps = latestStepsDocument.toObject(Steps::class.java) // convert into Steps data class
                    if (steps != null) {
                        onResult(steps)
                    } else {
                        onResult(null)
                    }
                } else {
                    onResult(null)
                }
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    // TODO: check if this is a proper function (currently it creates id document in between the steps and the values)
    // initializes a new daily steps collection with the currently set step goal and deletes possible previous collections
    fun createStepsData(userId: String, dailyStepGoal: Int, onResult: (Boolean) -> Unit) {
        val stepsCollection = db.collection("users").document(userId).collection("steps")
        stepsCollection.get()
            .addOnSuccessListener { documents -> // gets all documents within the steps collection
                val batch = db.batch()
                for (document in documents) {
                    batch.delete(document.reference) // marks each document for future deletion
                }
                batch.commit() // deletes all documents marked for deletion
                    .addOnSuccessListener {
                        // create new step document
                        val steps = Steps(
                            dailyStepsTaken = 0, // with no steps taken yet
                            dailyStepGoal = dailyStepGoal // with currently set goal
                        )
                        stepsCollection.add(steps)
                            .addOnSuccessListener { onResult(true) }
                            .addOnFailureListener { onResult(false) }
                    }
                    .addOnFailureListener { onResult(false) }
            }
            .addOnFailureListener { onResult(false) }
    }


    // updates the daily steps taken so far
    fun updateStepsData(userId: String, stepsTaken: Int? = null, stepGoal: Int? = null, onResult: (Boolean) -> Unit) {
        val stepsCollection = db.collection("users").document(userId).collection("steps")
        stepsCollection.get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) { // if a document exists
                    val latestStepsDocument = documents.documents.first() // get the first document
                    val updates = mutableMapOf<String, Any>()
                    stepsTaken?.let { updates["dailyStepsTaken"] = it }
                    stepGoal?.let { updates["dailyStepGoal"] = it }
                    latestStepsDocument.reference.update(updates)
                        .addOnSuccessListener { onResult(true) }
                        .addOnFailureListener { onResult(false) }
                } else {
                    onResult(false) // no document found to update
                }
            }
            .addOnFailureListener { onResult(false) }
    }
}