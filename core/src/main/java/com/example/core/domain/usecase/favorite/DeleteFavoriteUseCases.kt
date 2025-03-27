package com.example.core.domain.usecase.favorite

import com.example.core.domain.repository.FavoriteRepository

class DeleteFavoriteUseCases(private val repository: FavoriteRepository) {

    suspend operator fun invoke(id: Int){
        return repository.deleteFavorite(id)
    }

}