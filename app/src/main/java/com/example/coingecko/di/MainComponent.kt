package com.example.coingecko.di

import com.example.coingecko.presentation.view_models.MainViewModel
import dagger.Subcomponent

@Subcomponent
interface MainComponent {
    fun viewModel(): MainViewModel
}