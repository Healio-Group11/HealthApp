package com.example.healiohealthapplication.data.repository

import com.example.healiohealthapplication.data.models.User
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class StepsRepository @Inject constructor() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // saves the amount of steps taken so far
    fun saveStepsData(userId: String, email: String, onResult: (Boolean) -> Unit) {
        // initialize the steps
        // add function that adds the currently taken steps into firestore
    }

    // gets the steps goal
    fun getStepsData(userId: String, onResult: (User?) -> Unit) {
        // add function to get the steps of the specific user
    }
}