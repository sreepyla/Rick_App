package com.example.rickapp.di

import com.example.rickapp.repository.CharacterRepository

interface AppModuleProvider {
    fun getCharacterRepository(): CharacterRepository
}
