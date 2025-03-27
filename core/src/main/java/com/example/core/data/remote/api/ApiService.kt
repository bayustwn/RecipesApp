package com.example.core.data.remote.api

import com.example.core.domain.model.Recipe
import com.example.core.data.model.RecipesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("recipes?limit=50")
    suspend fun getAllRecipes(): RecipesResponse

    @GET("recipes/{id}")
    suspend fun getRecipesById(@Path("id") id: Int): Recipe

}