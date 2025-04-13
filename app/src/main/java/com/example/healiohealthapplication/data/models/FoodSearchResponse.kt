package com.example.healiohealthapplication.data.models

import com.google.gson.annotations.SerializedName

data class FoodSearchResponse(
    val count: Int,
    val products: List<FoodProduct>
)

data class FoodProduct(
    @SerializedName("product_name")
    val productName: String?,
    val nutriments: Nutriments?
)

data class Nutriments(
    @SerializedName("energy-kcal_100g")
    val caloriesPer100g: Float?,
    @SerializedName("carbohydrates_100g")
    val carbsPer100g: Float?,
    @SerializedName("proteins_100g")
    val proteinPer100g: Float?,
    @SerializedName("fat_100g")
    val fatPer100g: Float?
)
