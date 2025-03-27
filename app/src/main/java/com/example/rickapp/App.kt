package com.example.rickapp

import android.app.Application
import com.example.rickapp.data.remote.ApiService
import com.example.rickapp.di.AppModuleProvider
import com.example.rickapp.repository.CharacterRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application(), AppModuleProvider {

    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private val repository: CharacterRepository by lazy {
        CharacterRepository(apiService)
    }

    override fun getCharacterRepository(): CharacterRepository = repository
}
