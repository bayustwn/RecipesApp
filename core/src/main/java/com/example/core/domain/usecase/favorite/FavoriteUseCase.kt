package com.example.core.domain.usecase.favorite

data class FavoriteUseCase (
    val getAllFav: GetAllFavoriteUseCases,
    val addFav: AddFavoriteUseCase,
    val deleteFav: DeleteFavoriteUseCases,
    val getFavById: GetFavoriteByIdUseCases
)