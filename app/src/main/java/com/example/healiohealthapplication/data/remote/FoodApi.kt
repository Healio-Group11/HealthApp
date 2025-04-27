package com.example.healiohealthapplication.data.remote

import com.example.healiohealthapplication.data.models.FoodSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {
    @GET("search")
    suspend fun searchFood(
        @Query("categories_tags_en") category: String? = null,
        @Query("labels_tags_en") labels: String? = null,
        @Query("fields") fields: String = "code,product_name,brands,nutriments",
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 20
    ): FoodSearchResponse
}
