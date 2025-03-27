package com.example.core.di

import com.example.core.data.remote.api.ApiService
import com.example.core.domain.repository.RecipeRepository
import com.example.core.data.repository.RecipesRepositoryImpl
import com.example.core.domain.usecase.recipes.GetAllRecipesUseCase
import com.example.core.domain.usecase.recipes.GetRecipeByIdUseCase
import com.example.core.domain.usecase.recipes.RecipeUseCases
import com.example.core.presentation.viewmodel.RecipesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }

    single<RecipeRepository> {
        RecipesRepositoryImpl(get())
    }

    single { GetAllRecipesUseCase(get()) }
    single { GetRecipeByIdUseCase(get()) }

    single {
        RecipeUseCases(
            getAllRecipes = get(),
            getRecipeById = get()
        )
    }

    viewModel { RecipesViewModel(get()) }
}
