package com.example.core.domain.repository

import com.example.core.domain.model.RecipeItem
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAllFavorite(): Flow<List<RecipeItem>>
    fun getFavoriteById(id: Int): Flow<RecipeItem?>
    suspend fun addFavorite(recipeItem: RecipeItem)
    suspend fun deleteFavorite(id: Int)
}