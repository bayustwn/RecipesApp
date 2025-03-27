package com.example.core.domain.usecase.favorite

import com.example.core.domain.model.RecipeItem
import com.example.core.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteByIdUseCases (private val repository: FavoriteRepository){

    fun invoke(id: Int): Flow<RecipeItem?>{
        return repository.getFavoriteById(id)
    }

}