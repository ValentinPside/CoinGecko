package com.example.coingecko.data.network

import com.example.coingecko.data.network.dto.InfoCoinDto
import com.example.coingecko.domain.Coin
import com.example.coingecko.domain.Repository
import com.example.coingecko.utils.asCoin
import com.example.coingecko.utils.asListCoin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: NetworkServiceAPI): Repository {

    override suspend fun getCoinList(targetCurrency: String): List<Coin> {
        return withContext(Dispatchers.IO) {
            val coinRemote = api.getCoinsList(targetCurrency, 25)
            coinRemote.asListCoin()
        }
    }

    override suspend fun getCoin(id: String): InfoCoinDto {
        return withContext(Dispatchers.IO) {
            val coinRemote = api.getCoin(id)
            coinRemote
        }
    }

}