package com.example.presentation.presentation.state

sealed class RecipesUiState<out T> {
    object Loading : RecipesUiState<Nothing>()
    data class Success<T>(val data: T) : RecipesUiState<T>()
    data class Error(val message: String) : RecipesUiState<Nothing>()
}