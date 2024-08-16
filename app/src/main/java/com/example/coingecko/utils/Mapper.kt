package com.example.coingecko.utils

import android.icu.text.DecimalFormat
import com.example.coingecko.R
import com.example.coingecko.data.network.dto.CoinDto
import com.example.coingecko.domain.Coin
import java.math.RoundingMode
import java.util.Locale

fun CoinDto.asCoin() = Coin(
    id = this.id,
    symbol = this.symbol.uppercase(Locale.ROOT),
    name = this.name,
    image = this.image,
    price = formatPrice(this.price),
    changePercent = plusOrNot(String.format("%.2f", this.changePercent).plus("%")),
    color = color(this.changePercent)
)

fun List<CoinDto>.asListCoin() = this.map { it.asCoin() }

private fun formatPrice(price: Double): String {
    val df = DecimalFormat()
    val roundoff = price.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
    df.isGroupingUsed = true
    df.groupingSize = 3
    return df.format(roundoff).replace(" ", ",")
}

private fun plusOrNot(changePercent: String): String{
    return if(changePercent[0] == '-'){
        changePercent
    } else{
        "+".plus(changePercent)
    }
}

private fun color(changePercent: Double): Int{
    return if (changePercent < 0) {
        R.color.red
    } else {
        R.color.green
    }
}