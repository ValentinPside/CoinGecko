package com.example.coingecko.data.network

import com.example.coingecko.domain.models.Coin
import com.example.coingecko.domain.Repository
import com.example.coingecko.domain.models.InfoCoin
import com.example.coingecko.utils.asInfoCoin
import com.example.coingecko.utils.asListCoin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: NetworkServiceAPI) : Repository {

    override suspend fun getCoinList(targetCurrency: String): List<Coin> {
        return withContext(Dispatchers.IO) {
            val coinRemote = api.getCoinsList(targetCurrency, 25)
            coinRemote.asListCoin()
        }
    }

    override suspend fun getCoin(id: String): InfoCoin {
        return withContext(Dispatchers.IO) {
            val coinRemote = api.getCoin(id)
            coinRemote.asInfoCoin()
        }
    }

}