package ng.riby.androidtest.config.di

import ng.riby.androidtest.presentation.capture.CaptureViewModel
import ng.riby.androidtest.presentation.map.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { CaptureViewModel(get()) }

    viewModel { MapViewModel(get()) }
}