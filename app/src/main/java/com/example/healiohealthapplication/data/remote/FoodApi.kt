package com.example.healiohealthapplication.data.remote

import com.example.healiohealthapplication.data.models.FoodSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {
    @GET("cgi/search.pl")
    suspend fun searchFood(
        @Query("search_terms") foodName: String,
        @Query("search_simple") searchSimple: Int = 1,
        @Query("action") action: String = "process",
        @Query("json") json: Int = 1
    ): FoodSearchResponse
}
