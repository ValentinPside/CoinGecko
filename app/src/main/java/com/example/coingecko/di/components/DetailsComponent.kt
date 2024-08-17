package com.example.coingecko.di.components

import com.example.coingecko.presentation.view_models.DetailsViewModel
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