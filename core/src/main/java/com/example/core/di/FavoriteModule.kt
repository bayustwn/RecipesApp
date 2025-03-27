package com.example.core.di

import androidx.room.Room
import com.example.core.data.local.database.FavoriteDatabase
import com.example.core.data.local.repository.FavoriteRepositoryImpl
import com.example.core.domain.repository.FavoriteRepository
import com.example.core.domain.usecase.favorite.AddFavoriteUseCase
import com.example.core.domain.usecase.favorite.DeleteFavoriteUseCases
import com.example.core.domain.usecase.favorite.FavoriteUseCase
import com.example.core.domain.usecase.favorite.GetAllFavoriteUseCases
import com.example.core.domain.usecase.favorite.GetFavoriteByIdUseCases
import com.example.core.presentation.viewmodel.FavoriteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            FavoriteDatabase::class.java,
            "favorite_db"
        ).build()
    }

    single { get<FavoriteDatabase>().favoriteDao() }

    single<FavoriteRepository>{
        FavoriteRepositoryImpl(get())
    }

    single {
        FavoriteUseCase(
            getAllFav = GetAllFavoriteUseCases(repository = get<FavoriteRepository>()),
            addFav = AddFavoriteUseCase(repository = get<FavoriteRepository>()),
            deleteFav = DeleteFavoriteUseCases(repository = get<FavoriteRepository>()),
            getFavById = GetFavoriteByIdUseCases(repository = get<FavoriteRepository>())
        )
    }

    viewModel { FavoriteViewModel(get()) }
}
