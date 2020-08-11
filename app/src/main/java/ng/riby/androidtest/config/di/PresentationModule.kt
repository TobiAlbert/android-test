package ng.riby.androidtest.config.di

import ng.riby.androidtest.presentation.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainActivityViewModel(get()) }
}