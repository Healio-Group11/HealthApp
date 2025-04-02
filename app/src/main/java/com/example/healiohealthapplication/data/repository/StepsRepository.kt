package com.example.healiohealthapplication.data.repository

import com.example.healiohealthapplication.data.models.Steps
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class StepsRepository @Inject constructor() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // initializes a new daily steps collection with the currently set step goal
    // also deletes a possible previous collection
    // TODO: check if this is a proper function (currently it creates id document in between the steps and the values)
    fun createNewStepsCollection(userId: String, dailyStepGoal: Int, onResult: (Boolean) -> Unit) {
        // gets the collection from firestore that stores the steps for a specific user with userId
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
    fun updateStepsData(userId: String, stepsTaken: Int, onResult: (Boolean) -> Unit) {
        // gets the collection from firestore that stores the steps for a specific user
        val stepsCollection = db.collection("users").document(userId).collection("steps")

        // add the steps that have been taken so far into firestore
        // Retrieve the latest step document (assuming there is only one document for daily steps)
        stepsCollection.get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) { // if a document exists
                    val latestStepsDocument = documents.documents.first() // get the first document
                    latestStepsDocument.reference.update("dailyStepsTaken", stepsTaken)
                        .addOnSuccessListener { onResult(true) }
                        .addOnFailureListener { onResult(false) }
                } else {
                    onResult(false) // no document found to update
                }
            }
            .addOnFailureListener { onResult(false) }
    }
}