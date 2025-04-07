package com.example.healiohealthapplication.data.models

data class Steps(
    val userId: String = "",
    val date: String = "",  // Format: "yyyy-MM-dd"
    val steps: Int = 0
)