package com.example.coingecko.di

import com.example.coingecko.presentation.DetailsViewModel
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
interface DetailsComponent {
    fun viewModel(): DetailsViewModel

    @Subcomponent.Factory
    interface DetailsComponentFactory {
        fun create(
            @BindsInstance id: String
        ): DetailsComponent
    }
}