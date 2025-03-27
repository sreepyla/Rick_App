package com.example.rickapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickapp.data.model.Character
import com.example.rickapp.repository.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {

    private val _query = MutableStateFlow("rick")
    val query: StateFlow<String> = _query

    private val _statusFilter = MutableStateFlow("")
    val statusFilter: StateFlow<String> = _statusFilter

    private val _speciesFilter = MutableStateFlow("")
    val speciesFilter: StateFlow<String> = _speciesFilter

    private val _typeFilter = MutableStateFlow("")
    val typeFilter: StateFlow<String> = _typeFilter

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchCharacters()
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    fun onSearchClicked() {
        fetchCharacters()
    }

    fun onStatusFilterChanged(status: String) {
        _statusFilter.value = status
        fetchCharacters()
    }

    fun onSpeciesFilterChanged(species: String) {
        _speciesFilter.value = species
        fetchCharacters()
    }

    fun onTypeFilterChanged(type: String) {
        _typeFilter.value = type
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val result = repository.searchCharacters(query.value)
                _characters.value = result.results.filter {
                    it.status.contains(statusFilter.value, ignoreCase = true) &&
                            it.species.contains(speciesFilter.value, ignoreCase = true) &&
                            it.type.contains(typeFilter.value, ignoreCase = true)
                }
            } catch (e: Exception) {
                _characters.value = emptyList()
                _error.value = "Could not load characters. Try again."
            } finally {
                _loading.value = false
            }
        }
    }
}
