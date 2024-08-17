package com.example.coingecko.di.components

import com.example.coingecko.presentation.view_models.MainViewModel
import dagger.Subcomponent

@Subcomponent
interface MainComponent {
    fun viewModel(): MainViewModel
}