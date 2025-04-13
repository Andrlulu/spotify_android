package com.laioffer.spotify.ui.home

import androidx.lifecycle.ViewModel
import com.laioffer.spotify.datamodel.Section
import com.laioffer.spotify.repository.HomeRepository
import javax.inject.Inject

// todo: repo mainsafe
// todo: why lifecycle.viewmodel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
): ViewModel() {

    // model / state
    var uiState = HomeUiState(feed = listOf(), isLoading = false)

    // event
    fun fetchHomeScreen() {
        val section : List<Section> = repository.getHomeSections()
        uiState = HomeUiState(feed = section, isLoading = false)
    }

    data class HomeUiState(
        val feed: List<Section>,
        val isLoading: Boolean
    )