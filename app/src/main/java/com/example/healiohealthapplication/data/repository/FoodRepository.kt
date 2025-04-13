package com.example.healiohealthapplication.data.repository

import com.example.healiohealthapplication.data.models.FoodProduct
import com.example.healiohealthapplication.data.remote.FoodApi
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val foodApi: FoodApi
) {
    suspend fun searchFood(foodName: String): List<FoodProduct> {
        return try {
            val response = foodApi.searchFood(foodName)
            response.products
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
