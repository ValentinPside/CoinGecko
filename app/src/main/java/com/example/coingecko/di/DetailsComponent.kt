package com.example.coingecko.di

import com.example.coingecko.presentation.DetailsViewModel
import dagger.Subcomponent

@Subcomponent
interface DetailsComponent {
    fun viewModel(): DetailsViewModel
}