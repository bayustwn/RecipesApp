package com.example.core.domain.usecase.favorite

import com.example.core.domain.model.RecipeItem
import com.example.core.domain.repository.FavoriteRepository

class AddFavoriteUseCase (private val repository: FavoriteRepository) {

    suspend operator fun invoke(recipeItem: RecipeItem){
        repository.addFavorite(recipeItem)
    }

}