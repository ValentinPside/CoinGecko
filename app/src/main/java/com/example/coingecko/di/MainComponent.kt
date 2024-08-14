package com.example.coingecko.di

import com.example.coingecko.presentation.MainViewModel
import dagger.Subcomponent

@Subcomponent
interface MainComponent {
    fun viewModel(): MainViewModel
}