package com.example.core.data.model

data class RecipesResponse(
    val recipes: List<RecipesItemResponse?>? = null,
)

data class RecipesItemResponse(
    val cookTimeMinutes: Int? = null,
    val instructions: List<String?>? = null,
    val image: String? = null,
    val prepTimeMinutes: Int? = null,
    val caloriesPerServing: Int? = null,
    val rating: Any? = null,
    val mealType: List<String?>? = null,
    val cuisine: String? = null,
    val userId: Int? = null,
    val tags: List<String?>? = null,
    val difficulty: String? = null,
    val servings: Int? = null,
    val reviewCount: Int? = null,
    val name: String? = null,
    val ingredients: List<String?>? = null,
    val id: Int? = null
)

