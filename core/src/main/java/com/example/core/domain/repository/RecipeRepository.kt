package com.example.core.domain.repository

import com.example.core.domain.model.Recipe
import com.example.core.domain.model.RecipeItem
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getAllRecipes(): Flow<List<RecipeItem>>
    suspend fun getRecipesById(id: Int): Flow<Recipe>
}