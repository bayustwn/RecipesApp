package com.example.core.domain.usecase.favorite

import com.example.core.domain.model.RecipeItem
import com.example.core.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class GetAllFavoriteUseCases (private val repository: FavoriteRepository) {

    operator fun invoke(): Flow<List<RecipeItem>>{
        return repository.getAllFavorite()
    }

}