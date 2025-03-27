package com.example.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Recipe
import com.example.core.domain.model.RecipeItem
import com.example.core.domain.usecase.recipes.RecipeUseCases
import com.example.core.presentation.state.RecipesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RecipesViewModel(
    private val useCases: RecipeUseCases
) : ViewModel() {

    private val _recipesState = MutableStateFlow<RecipesUiState<List<RecipeItem>>>(RecipesUiState.Loading)
    val recipesState: StateFlow<RecipesUiState<List<RecipeItem>>> = _recipesState

    private val _recipeDetailState = MutableStateFlow<RecipesUiState<Recipe>>(RecipesUiState.Loading)
    val recipeDetailState: StateFlow<RecipesUiState<Recipe>> = _recipeDetailState

    fun getAllRecipes() {
        viewModelScope.launch {
            useCases.getAllRecipes()
                .onStart {
                    _recipesState.value = RecipesUiState.Loading
                }
                .catch { e ->
                    _recipesState.value = RecipesUiState.Error(e.message ?: "Terjadi kesalahan")
                }
                .collect { recipes ->
                    _recipesState.value = RecipesUiState.Success(recipes)
                }
        }
    }

    fun getRecipeById(id: Int) {
        viewModelScope.launch {
            useCases.getRecipeById(id)
                .onStart {
                    _recipeDetailState.value = RecipesUiState.Loading
                }
                .catch { e ->
                    _recipeDetailState.value = RecipesUiState.Error(e.message ?: "Terjadi kesalahan")
                }
                .collect { recipe ->
                    _recipeDetailState.value = RecipesUiState.Success(recipe)
                }
        }
    }
}