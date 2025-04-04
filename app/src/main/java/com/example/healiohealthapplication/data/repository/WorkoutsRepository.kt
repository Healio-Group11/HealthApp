package com.example.healiohealthapplication.data.repository

import com.example.healiohealthapplication.data.models.Workout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WorkoutsRepository @Inject constructor(private val db: FirebaseFirestore) {

    // Save a new workout
    suspend fun saveWorkout(userId: String, workout: Workout): Boolean {
        return try {
            db.collection("users")
                .document(userId)
                .collection("workouts")
                .add(workout)
                .await()
            true // Successfully saved
        } catch (e: Exception) {
            false // Failed to save
        }
    }

    // Get all workouts for a user
    suspend fun getWorkouts(userId: String): List<Workout>? {
        return try {
            val documents = db.collection("users")
                .document(userId)
                .collection("workouts")
                .get()
                .await()

            documents.mapNotNull { it.toObject(Workout::class.java) }
        } catch (e: Exception) {
            null // Failed to retrieve workouts
        }
    }

    // Update an existing workout
    suspend fun updateWorkout(userId: String, workoutId: String, updatedWorkout: Workout): Boolean {
        return try {
            db.collection("users")
                .document(userId)
                .collection("workouts")
                .document(workoutId)
                .set(updatedWorkout)
                .await()
            true // Successfully updated
        } catch (e: Exception) {
            false // Failed to update
        }
    }

    // Delete a workout
    suspend fun deleteWorkout(userId: String, workoutId: String): Boolean {
        return try {
            db.collection("users")
                .document(userId)
                .collection("workouts")
                .document(workoutId)
                .delete()
                .await()
            true // Successfully deleted
        } catch (e: Exception) {
            false // Failed to delete
        }
    }
}
