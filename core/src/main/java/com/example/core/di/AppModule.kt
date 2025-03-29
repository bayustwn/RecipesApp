package com.example.core.di

import com.example.core.BuildConfig
import com.example.core.data.remote.api.ApiService
import com.example.core.domain.repository.RecipeRepository
import com.example.core.data.repository.RecipesRepositoryImpl
import com.example.core.domain.usecase.recipes.GetAllRecipesUseCase
import com.example.core.domain.usecase.recipes.GetRecipeByIdUseCase
import com.example.core.domain.usecase.recipes.RecipeUseCases
import com.example.core.presentation.viewmodel.RecipesViewModel
import com.example.core.utils.Mapper.getDomain
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single {
        CertificatePinner.Builder()
            .add(BuildConfig.BASE_URL.getDomain(), BuildConfig.CERTIFICATE)
            .build()
    }
    single {
        OkHttpClient.Builder()
            .certificatePinner(get())
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }

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
