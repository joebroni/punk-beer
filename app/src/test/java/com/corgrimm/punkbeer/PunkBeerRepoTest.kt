package com.corgrimm.punkbeer

import com.corgrimm.punkbeer.api.PunkApiService
import com.corgrimm.punkbeer.models.Beer
import com.corgrimm.punkbeer.repository.PunkBeerRepoImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class PunkBeerRepoTest {

    @Test
    fun `beerListFlow emits Loading and Success states`() {
        // Given
        val punkApiService = mockk<PunkApiService>()
        coEvery { punkApiService.getBeers() } returns listOf(mockk())

        val punkBeerRepo = PunkBeerRepoImpl(punkApiService)

        // When
        val flowResult = runBlocking {
            val flowList = mutableListOf<Resource<List<Beer>>>()
            punkBeerRepo.beerListFlow().collect { flowList.add(it) }
            flowList
        }

        // Then
        assertEquals(2, flowResult.size) // Loading and Success states
        assertEquals(Resource.Loading<List<Beer>>(), flowResult[0])
        assert(flowResult[1] is Resource.Success)
    }

    @Test
    fun `beerListFlow emits Loading and Error states`() {
        // Given
        val punkApiService = mockk<PunkApiService>()
        coEvery { punkApiService.getBeers() } throws Exception("Network error")

        val punkBeerRepo = PunkBeerRepoImpl(punkApiService)

        // When
        val flowResult = runBlocking {
            val flowList = mutableListOf<Resource<List<Beer>>>()
            punkBeerRepo.beerListFlow().collect { flowList.add(it) }
            flowList
        }

        // Then
        assertEquals(2, flowResult.size) // Loading and Error states
        assertEquals(Resource.Loading<List<Beer>>(), flowResult[0])
        assert(flowResult[1] is Resource.Error)
    }
}