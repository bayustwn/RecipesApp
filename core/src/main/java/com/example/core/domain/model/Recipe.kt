package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
	val id: Int,
	val name: String,
	val image: String,
	val cookTimeMinutes: Int,
	val prepTimeMinutes: Int,
	val caloriesPerServing: Int,
	val rating: String,
	val mealType: List<String>,
	val cuisine: String,
	val userId: Int,
	val tags: List<String>,
	val difficulty: String,
	val servings: Int,
	val reviewCount: Int,
	val instructions: List<String>,
	val ingredients: List<String>
): Parcelable