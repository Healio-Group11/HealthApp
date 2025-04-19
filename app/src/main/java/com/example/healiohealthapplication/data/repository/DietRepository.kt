package com.example.healiohealthapplication.data.repository

import android.util.Log
import com.example.healiohealthapplication.data.models.Diet
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class DietRepository @Inject constructor() {

    private val db = FirebaseFirestore.getInstance()

    /**
     * Save or overwrite the diet for a given user on a specific date.
     *
     * @param userId the UID of the user
     * @param date a date string in format "YYYY-MM-DD"
     * @param diet the Diet object to save
     * @param onResult callback with true if success, false on failure
     */
    fun saveDietForDate(
        userId: String,
        date: String,
        diet: Diet,
        onResult: (Boolean) -> Unit
    ) {
        db.collection("users")
            .document(userId)
            .collection("diet")
            .document(date)           // use the date as document ID
            .set(diet)
            .addOnSuccessListener {
                Log.d("DietRepository", "Saved diet for $userId on $date")
                onResult(true)
            }
            .addOnFailureListener { e ->
                Log.e("DietRepository", "Error saving diet for $userId on $date", e)
                onResult(false)
            }
    }

    /**
     * Fetch the diet for a given user on a specific date.
     *
     * @param userId the UID of the user
     * @param date a date string in format "YYYY-MM-DD"
     * @param onResult callback delivering the Diet object or null if none/failure
     */
    fun getDietForDate(
        userId: String,
        date: String,
        onResult: (Diet?) -> Unit
    ) {
        db.collection("users")
            .document(userId)
            .collection("diet")
            .document(date)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val diet = doc.toObject(Diet::class.java)
                    Log.d("DietRepository", "Retrieved diet for $userId on $date: $diet")
                    onResult(diet)
                } else {
                    Log.d("DietRepository", "No diet found for $userId on $date")
                    onResult(null)
                }
            }
            .addOnFailureListener { e ->
                Log.e("DietRepository", "Error fetching diet for $userId on $date", e)
                onResult(null)
            }
    }

    /**
     * Fetch all saved Diet entries for a user, across all dates.
     *
     * @param userId the UID of the user
     * @param onResult callback delivering a Map of date → Diet
     */
    fun getAllDietEntries(
        userId: String,
        onResult: (Map<String, Diet>) -> Unit
    ) {
        db.collection("users")
            .document(userId)
            .collection("diet")
            .get()
            .addOnSuccessListener { snap ->
                val map = snap.documents
                    .mapNotNull { doc ->
                        doc.id.takeIf { doc.exists() }?.let { dateKey ->
                            doc.toObject(Diet::class.java)?.let { diet ->
                                dateKey to diet
                            }
                        }
                    }
                    .toMap()
                Log.d("DietRepository", "Fetched all diet entries for $userId: ${map.keys}")
                onResult(map)
            }
            .addOnFailureListener { e ->
                Log.e("DietRepository", "Error fetching all diets for $userId", e)
                onResult(emptyMap())
            }
    }

    /**
     * Delete the diet entry for a given user on a specific date.
     *
     * @param userId the UID of the user
     * @param date date string "YYYY-MM-DD"
     * @param onResult callback with true if deleted, false otherwise
     */
    fun deleteDietForDate(
        userId: String,
        date: String,
        onResult: (Boolean) -> Unit
    ) {
        db.collection("users")
            .document(userId)
            .collection("diet")
            .document(date)
            .delete()
            .addOnSuccessListener {
                Log.d("DietRepository", "Deleted diet for $userId on $date")
                onResult(true)
            }
            .addOnFailureListener { e ->
                Log.e("DietRepository", "Error deleting diet for $userId on $date", e)
                onResult(false)
            }
    }
}
