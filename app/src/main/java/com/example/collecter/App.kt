package com.example.collecter

import android.app.Application
import androidx.room.Room
import com.example.collecter.repositories.AuthRepository
import com.example.collecter.services.HTTP
import com.example.collecter.services.Database
import com.example.collecter.services.PreferenceDataStore
import com.example.collecter.ui.models.AuthViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

class App : Application() {
    val appModule = module {
        singleOf(::PreferenceDataStore)
        singleOf(::HTTP)
        // Updated Database provider
        single {
            Room.databaseBuilder(
                androidApplication(), // Use androidApplication() for application context
                Database::class.java,
                "collect.db" // Your database name
            ).build()
        }
        // Provide DAOs from the Database instance
        single { get<Database>().userDao() } // Assuming you have userDao() in your Database class
        singleOf(::AuthRepository)
        viewModelOf(::AuthViewModel)
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