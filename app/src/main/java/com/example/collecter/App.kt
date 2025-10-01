package com.example.collecter

import android.app.Application
import androidx.room.Room // Toevoegen voor Room
import com.example.collecter.services.HTTP
import com.example.collecter.repositories.AuthRepository
import com.example.collecter.repositories.CollectionRepository
import com.example.collecter.services.PreferenceDataStore
import com.example.collecter.ui.models.AuthViewModel
import com.example.collecter.ui.models.CollectionListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

class App : Application() {
    val appModule = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                com.example.collecter.services.Database::class.java,
                "db"
            ).build()
        }
        singleOf(::PreferenceDataStore)
        singleOf(::HTTP)
        singleOf(::AuthRepository)

        viewModelOf(::AuthViewModel)
        singleOf(::CollectionRepository)
        viewModelOf(::CollectionListViewModel)
    }

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }
}