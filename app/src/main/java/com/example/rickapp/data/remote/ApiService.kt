package com.example.rickapp.data.remote

import com.example.rickapp.data.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun searchCharacters(
        @Query("name") name: String
    ): CharacterResponse
}
