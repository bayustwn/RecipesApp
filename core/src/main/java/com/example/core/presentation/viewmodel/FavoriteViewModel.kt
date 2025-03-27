package com.example.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.RecipeItem
import com.example.core.domain.usecase.favorite.FavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel (
    private val favoriteUseCase: FavoriteUseCase
) : ViewModel() {

    private val _recipeItemRecipes = MutableStateFlow<List<RecipeItem>>(emptyList())
    val recipeItemRecipes: StateFlow<List<RecipeItem>> = _recipeItemRecipes.asStateFlow()

    private val _recipeItemState = MutableStateFlow<RecipeItem?>(null)
    val recipeItemState: StateFlow<RecipeItem?> = _recipeItemState

    init {
        getFavoriteRecipes()
    }

    fun addFavorite(recipeItem: RecipeItem){
        viewModelScope.launch {
            favoriteUseCase.addFav.invoke(recipeItem)
        }
    }

    fun deleteFavorite(id: Int){
        viewModelScope.launch {
            favoriteUseCase.deleteFav.invoke(id)
        }
    }

    fun checkFavorite(id: Int) {
        viewModelScope.launch {
            favoriteUseCase.getFavById.invoke(id).collect { fav->
                _recipeItemState.value = fav
            }
        }
    }

    private fun getFavoriteRecipes() {
        viewModelScope.launch {
            favoriteUseCase.getAllFav().collect { fav ->
                    _recipeItemRecipes.value = fav
                }
        }
    }

}