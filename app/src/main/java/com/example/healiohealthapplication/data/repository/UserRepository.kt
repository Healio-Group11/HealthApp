package com.example.healiohealthapplication.data.repository

import android.util.Log
import com.example.healiohealthapplication.data.models.User
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserRepository @Inject constructor() {

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

    fun getUserData(userId: String, onResult: (User?) -> Unit) {
        db.collection("users")
            .document(userId) // fetches the document in "users" with the specific userId under which is all this user's information
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    Log.d("FirestoreRepository", "User data retrieved successfully: $user")
                    onResult(user) // callback gives the user information if fetching was successful
                } else {
                    Log.d("FirestoreRepository", "No user data found")
                    onResult(null)
                }
            }
            .addOnFailureListener { e ->
                Log.w("FirestoreRepository", "Error getting user data", e)
                onResult(null)
            }
    }
}