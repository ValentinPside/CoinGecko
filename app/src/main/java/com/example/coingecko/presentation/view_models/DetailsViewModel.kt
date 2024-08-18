package com.example.coingecko.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coingecko.R
import com.example.coingecko.domain.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: Repository,
    id: String
) : ViewModel() {

    private val state = MutableStateFlow(DetailsState())
    fun observeUi() = state.asStateFlow()

    init {
        getCoinInfo(id)
    }

    fun getCoinInfo(id: String) {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }
            try {
                val coinInfo = repository.getCoin(id)
                state.update {
                    it.copy(
                        info = coinInfo.description,
                        category = coinInfo.categories,
                        imageUrl = coinInfo.image,
                        error = null
                    )
                }
            } catch (e: Exception) {
                state.update { it.copy(error = R.string.error_message) }
            } finally {
                state.update { it.copy(isLoading = false) }
            }
        }
    }
}

data class DetailsState(
    val info: String? = null,
    val category: String? = null,
    val imageUrl: String? = null,
    val error: Int? = null,
    val isLoading: Boolean = false
)