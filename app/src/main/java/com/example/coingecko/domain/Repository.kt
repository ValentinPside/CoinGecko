package com.example.coingecko.domain

import com.example.coingecko.data.network.dto.InfoCoinDto

interface Repository {

    suspend fun getCoinList(targetCurrency: String): List<Coin>

    suspend fun getCoin(id: String): InfoCoinDto

}