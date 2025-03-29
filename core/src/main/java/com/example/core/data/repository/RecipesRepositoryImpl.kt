package com.example.core.data.repository

import com.example.core.data.remote.api.ApiService
import com.example.core.domain.model.Recipe
import com.example.core.domain.model.RecipeItem
import com.example.core.domain.repository.RecipeRepository
import com.example.core.utils.Mapper.toDomain
import com.example.core.utils.Mapper.toItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class RecipesRepositoryImpl(
    private val apiService: ApiService
): RecipeRepository {
    override suspend fun getAllRecipes(): Flow<List<RecipeItem>> = flow {
        try {
            val response = apiService.getAllRecipes()
            val data = response.recipes?.mapNotNull { it?.toDomain()?.toItem() } ?: emptyList()
            emit(data)
        } catch (e: IOException) {
            throw Exception("Periksa koneksi internet" + e.message + e.cause)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getRecipesById(id: Int): Flow<Recipe> = flow {
        try {
            val response: Recipe = apiService.getRecipesById(id)
            emit(response)
        }catch (e: IOException){
            throw Exception("Periksa koneksi internet")
        }
    }.flowOn(Dispatchers.IO)

}
