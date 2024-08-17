package com.example.coingecko.di.components

import android.content.Context
import com.example.coingecko.di.modules.NetworkModule
import com.example.coingecko.di.modules.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
    ]
)
interface AppComponent {

    fun mainComponent(): MainComponent

    fun detailsComponent(): DetailsComponent.DetailsComponentFactory

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}