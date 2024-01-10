package com.corgrimm.punkbeer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corgrimm.punkbeer.models.Beer
import com.corgrimm.punkbeer.repository.PunkBeerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(
    private val punkBeerRepo: PunkBeerRepo
) : ViewModel() {

    private val stateFlow = MutableStateFlow(initialBeerState)
    val state = stateFlow.asStateFlow()

    init {
        fetchBeers()
    }

    fun fetchBeers() {
        punkBeerRepo
            .beerListFlow()
            .onEach(::processBeers)
            .launchIn(viewModelScope)
    }

    fun getBeerById(beerId: Int): Beer? {
        return state.value.beers.find { it.id == beerId }
    }

    private suspend fun processBeers(collectionResource: Resource<List<Beer>>) {
        when (collectionResource) {
            is Resource.Loading -> {
                val newState = state.value.copy(
                    isLoading = true,
                    errorMessage = ""
                )
                stateFlow.emit(newState)
            }

            is Resource.Success -> {
                val newState = state.value.copy(
                    isLoading = false,
                    beers = collectionResource.data
                )
                stateFlow.emit(newState)
            }

            is Resource.Error -> {
                val newState = state.value.copy(
                    isLoading = false,
                    errorMessage = collectionResource.message
                )
                stateFlow.emit(newState)
            }
        }
    }
}

private val initialBeerState = BeerState(
    isLoading = false,
    beers = listOf(),
    errorMessage = ""
)

data class BeerState(
    val isLoading: Boolean,
    val beers: List<Beer>,
    val errorMessage: String
)