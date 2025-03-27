package com.example.rickapp.repository

import com.example.rickapp.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun searchCharacters(query: String) = apiService.searchCharacters(query)
}
