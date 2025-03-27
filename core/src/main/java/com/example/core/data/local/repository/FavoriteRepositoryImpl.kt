package com.example.core.data.local.repository

import com.example.core.data.local.dao.FavoriteDao
import com.example.core.domain.model.RecipeItem
import com.example.core.domain.repository.FavoriteRepository
import com.example.core.utils.Mapper.toDomain
import com.example.core.utils.Mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class FavoriteRepositoryImpl (
    private val dao: FavoriteDao
): FavoriteRepository {
    override fun getAllFavorite(): Flow<List<RecipeItem>> {
        return dao.getAllFavorite().map { list -> list.map { it.toDomain() } }
    }

    override fun getFavoriteById(id: Int): Flow<RecipeItem?> {
        return dao.getFavoriteById(id).map { it?.toDomain() }
    }

    override suspend fun addFavorite(recipeItem: RecipeItem) {
        return dao.insertFavorite(recipeItem.toEntity())
    }

    override suspend fun deleteFavorite(id: Int) {
        return dao.deleteFavorite(id)
    }
}