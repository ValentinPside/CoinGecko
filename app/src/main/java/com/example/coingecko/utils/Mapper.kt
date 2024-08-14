package com.example.coingecko.utils

import com.example.coingecko.data.network.dto.CoinDto
import com.example.coingecko.domain.Coin

fun CoinDto.asCoin() = Coin(
    id = this.id,
    symbol = this.symbol,
    name = this.name,
    image = this.image,
    price = this.price,
    changePercent = this.changePercent
)

fun List<CoinDto>.asListCoin() = this.map { it.asCoin() }