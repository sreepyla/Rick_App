package com.example.rickapp

import app.cash.turbine.test
import com.example.rickapp.data.model.Character
import com.example.rickapp.data.model.CharacterResponse
import com.example.rickapp.data.model.Origin
import com.example.rickapp.presentation.CharacterViewModel
import com.example.rickapp.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterViewModelTest {

    private lateinit var viewModel: CharacterViewModel
    private lateinit var mockRepository: CharacterRepository

    private val testDispatcher = StandardTestDispatcher()

    private val dummyCharacter = Character(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "",
        origin = Origin("Earth"),
        image = "https://rick.png",
        created = "2017-11-04T18:48:46.250Z"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        mockRepository = mock(CharacterRepository::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onQueryChanged updates characters on search`() = runTest {
        `when`(mockRepository.searchCharacters("rick")).thenReturn(
            CharacterResponse(results = listOf(dummyCharacter))
        )

        viewModel = CharacterViewModel(mockRepository)
        viewModel.onQueryChanged("rick")
        viewModel.onSearchClicked()

        advanceUntilIdle()

        viewModel.characters.test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("Rick Sanchez", result[0].name)
            cancel()
        }
    }

    @Test
    fun `loading becomes false after fetch`() = runTest {
        `when`(mockRepository.searchCharacters("rick")).thenReturn(
            CharacterResponse(results = listOf(dummyCharacter))
        )

        viewModel = CharacterViewModel(mockRepository)
        viewModel.onSearchClicked()

        advanceUntilIdle()

        assertEquals(false, viewModel.loading.value)
    }
}
