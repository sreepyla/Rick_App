package com.example.rickapp.data.model

data class CharacterResponse(
    val results: List<Character>
)

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val origin: Origin,
    val image: String,
    val created: String
)

data class Origin(
    val name: String
)
