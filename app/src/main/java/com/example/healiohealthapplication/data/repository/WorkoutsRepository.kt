package com.example.healiohealthapplication.data.repository

import com.example.healiohealthapplication.data.models.Workout
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class WorkoutsRepository @Inject constructor(private val db: FirebaseFirestore) {

    // Save a new workout
    fun saveWorkout(userId: String, workout: Workout, onResult: (Boolean) -> Unit) {
        db.collection("users")
            .document(userId)
            .collection("workouts")
            .add(workout)
            .addOnSuccessListener {
                onResult(true) // Successfully saved the workout
            }
            .addOnFailureListener {
                onResult(false) // Failed to save the workout
            }
    }

    // Get all workouts for a user
    fun getWorkouts(userId: String, onResult: (List<Workout>?) -> Unit) {
        db.collection("users")
            .document(userId)
            .collection("workouts")
            .get()
            .addOnSuccessListener { documents ->
                val workouts = documents.mapNotNull { it.toObject(Workout::class.java) }
                onResult(workouts)
            }
            .addOnFailureListener {
                onResult(null) // Failed to retrieve workouts
            }
    }

    // Update an existing workout
    fun updateWorkout(userId: String, workoutId: String, updatedWorkout: Workout, onResult: (Boolean) -> Unit) {
        db.collection("users")
            .document(userId)
            .collection("workouts")
            .document(workoutId)
            .set(updatedWorkout)
            .addOnSuccessListener {
                onResult(true) // Successfully updated the workout
            }
            .addOnFailureListener {
                onResult(false) // Failed to update the workout
            }
    }

    // Delete a workout
    fun deleteWorkout(userId: String, workoutId: String, onResult: (Boolean) -> Unit) {
        db.collection("users")
            .document(userId)
            .collection("workouts")
            .document(workoutId)
            .delete()
            .addOnSuccessListener {
                onResult(true) // Successfully deleted the workout
            }
            .addOnFailureListener {
                onResult(false) // Failed to delete the workout
            }
    }
}
