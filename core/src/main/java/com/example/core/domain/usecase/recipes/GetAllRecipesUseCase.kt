package com.example.core.domain.usecase.recipes

import com.example.core.domain.model.RecipeItem
import com.example.core.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class GetAllRecipesUseCase(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(): Flow<List<RecipeItem>> {
        return repository.getAllRecipes()
    }
}