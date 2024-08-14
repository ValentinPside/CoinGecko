package com.example.coingecko.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coingecko.R
import com.example.coingecko.domain.Coin
import com.example.coingecko.domain.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val state = MutableStateFlow(DetailsState())
    fun observeUi() = state.asStateFlow()

    fun getCoin(id: String, currency: String) {
        viewModelScope.launch {
            try {
                val coin = repository.getCoin(id, currency)
                state.update { it.copy(coin= coin, error = null) }
            } catch (e: Exception) {
                state.update { it.copy(error = R.string.error_message) }
            }
        }
    }
}

data class DetailsState(
    val coin: Coin? = null,
    val error: Int? = null
)