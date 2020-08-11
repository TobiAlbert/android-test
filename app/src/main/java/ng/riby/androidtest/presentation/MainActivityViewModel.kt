package ng.riby.androidtest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ng.riby.androidtest.data.repository.ILocationRepository

class MainActivityViewModel(private val locationRepo: ILocationRepository): ViewModel() {

    private val locations = mutableListOf<Pair<Double, Double>>()

    fun addLocation(latitude: Double, longitude: Double) {
        locations.add(Pair(latitude, longitude))
    }

    fun saveLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            locationRepo.saveLocation(latitude, longitude)
        }
    }

    fun saveLocations() {
        viewModelScope.launch {
            locationRepo.saveLocations(locations)
        }
    }
}