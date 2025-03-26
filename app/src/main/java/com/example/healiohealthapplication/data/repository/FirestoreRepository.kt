package com.example.healiohealthapplication.data.repository

import android.util.Log
import com.example.healiohealthapplication.data.models.User
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirestoreRepository @Inject constructor() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun saveUserData(userId: String, email: String, onResult: (Boolean) -> Unit) {
        // initializes the user
        val user = User(
            id = userId,
            email = email,
            weight = "",
            height = "",
            bmi = ""
        )

        // adds the specific user to firestore
        db.collection("users")
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                Log.d("FirestoreRepository", "User data saved successfully!")
                onResult(true) // returns the callback with true if saving a user into firestore was successful
            }
            .addOnFailureListener { e ->
                Log.w("FirestoreRepository", "Error saving user data", e)
                onResult(false) // returns the callback with false
            }
    }
}