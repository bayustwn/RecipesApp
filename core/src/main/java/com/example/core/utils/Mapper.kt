package com.example.core.utils

import com.example.core.data.local.entity.FavoriteEntity
import com.example.core.domain.model.Recipe
import com.example.core.data.model.RecipesItemResponse
import com.example.core.domain.model.RecipeItem

object Mapper {

    fun RecipesItemResponse.toDomain(): Recipe {
        return Recipe(
            id = this.id ?: 0,
            name = this.name.orEmpty(),
            image = this.image.orEmpty(),
            cookTimeMinutes = this.cookTimeMinutes ?: 0,
            prepTimeMinutes = this.prepTimeMinutes ?: 0,
            caloriesPerServing = this.caloriesPerServing ?: 0,
            rating = this.rating?.toString().orEmpty(),
            mealType = this.mealType?.filterNotNull() ?: emptyList(),
            cuisine = this.cuisine.orEmpty(),
            userId = this.userId ?: 0,
            tags = this.tags?.filterNotNull() ?: emptyList(),
            difficulty = this.difficulty.orEmpty(),
            servings = this.servings ?: 0,
            reviewCount = this.reviewCount ?: 0,
            instructions = this.instructions?.filterNotNull() ?: emptyList(),
            ingredients = this.ingredients?.filterNotNull() ?: emptyList()
        )
    }

    fun Recipe.toItem(): RecipeItem{
        return RecipeItem(
            id = id, name = name, difficulty = difficulty, image = image
        )
    }

    fun FavoriteEntity.toDomain(): RecipeItem{
        return RecipeItem(id = id, name = name, difficulty = difficulty, image = image)
    }

    fun RecipeItem.toEntity(): FavoriteEntity{
        return FavoriteEntity(id = id, name = name, difficulty = difficulty, image = image)
    }




}