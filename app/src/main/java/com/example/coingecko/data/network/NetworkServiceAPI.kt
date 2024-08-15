package com.example.coingecko.data.network

import com.example.coingecko.data.network.dto.CoinDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkServiceAPI {


    @GET("markets")
    suspend fun getCoinsList(@Query("vs_currency") targetCurrency: String, @Query("per_page") perPage: Int): List<CoinDto>

    @GET("{id}/market_chart")
    suspend fun getCoin(
        @Path("id") id: String,
        @Query("vs_currency") targetCurrency: String
    ): CoinDto


}