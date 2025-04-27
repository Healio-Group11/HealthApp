package com.example.healiohealthapplication.data.repository

import android.util.Log
import com.example.healiohealthapplication.data.models.FoodProduct
import com.example.healiohealthapplication.data.remote.FoodApi
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val foodApi: FoodApi
) {
    suspend fun searchFood(foodName: String): List<FoodProduct> {
        return try {
            val response = foodApi.searchFood(foodName)
            Log.d("FoodRepository", "Fetched ${response.products.size} products")
            response.products
        } catch (e: Exception) {
            Log.e("FoodRepository", "Error fetching products", e)
            emptyList()
        }
    }
}
