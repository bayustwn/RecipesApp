package com.example.core.domain.usecase.recipes


data class RecipeUseCases(
    val getAllRecipes: GetAllRecipesUseCase,
    val getRecipeById: GetRecipeByIdUseCase
)