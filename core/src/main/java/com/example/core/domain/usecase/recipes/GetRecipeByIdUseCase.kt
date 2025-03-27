package com.example.core.domain.usecase.recipes

import com.example.core.domain.model.Recipe
import com.example.core.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class GetRecipeByIdUseCase(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(id: Int): Flow<Recipe> {
        return repository.getRecipesById(id)
    }
}