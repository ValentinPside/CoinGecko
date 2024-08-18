package com.example.coingecko.domain

import com.example.coingecko.domain.models.Coin
import com.example.coingecko.domain.models.InfoCoin

interface Repository {

    suspend fun getCoinList(targetCurrency: String): List<Coin>

    suspend fun getCoin(id: String): InfoCoin

}