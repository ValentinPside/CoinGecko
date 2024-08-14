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

class MainViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val state = MutableStateFlow(MainState())
    fun observeUi() = state.asStateFlow()

    fun getCoinsList(currency: String) {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }
            try {
                val coinsList = repository.getCoinList(currency)
                state.update { it.copy(coinsList = coinsList, error = null) }
            } catch (e: Exception) {
                state.update { it.copy(error = R.string.error_message) }
            } finally {
                state.update { it.copy(isLoading = false) }
            }
        }
    }
}

data class MainState(
    val coinsList: List<Coin>? = null,
    val error: Int? = null,
    val isLoading: Boolean = false
)