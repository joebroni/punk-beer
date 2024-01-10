package com.corgrimm.punkbeer.api

import com.corgrimm.punkbeer.models.Beer
import retrofit2.http.GET

interface PunkApiService {
    @GET("beers")
    suspend fun getBeers(): List<Beer>
}