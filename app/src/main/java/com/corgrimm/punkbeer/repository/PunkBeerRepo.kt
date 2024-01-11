package com.corgrimm.punkbeer.repository

import com.corgrimm.punkbeer.Resource
import com.corgrimm.punkbeer.api.PunkApiService
import com.corgrimm.punkbeer.fetchNetworkResource
import com.corgrimm.punkbeer.models.Beer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

interface PunkBeerRepo {
    fun beerListFlow(): Flow<Resource<List<Beer>>>
}

class PunkBeerRepoImpl(
    private val punkBeerApi: PunkApiService
) : PunkBeerRepo {
    override fun beerListFlow() = flow {

        val beerFlow: Flow<Resource<List<Beer>>> = fetchNetworkResource(api = {
            punkBeerApi.getBeers()
        })

        emitAll(beerFlow)
    }

}
