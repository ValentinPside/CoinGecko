package com.example.coingecko.domain

interface Repository {

    suspend fun getCoinList(targetCurrency: String): List<Coin>?

    suspend fun getCoin(id: String, targetCurrency: String): Coin?

}