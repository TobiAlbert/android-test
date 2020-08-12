package ng.riby.androidtest.presentation.capture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ng.riby.androidtest.data.repository.ILocationRepository

class CaptureViewModel(private val locationRepo: ILocationRepository): ViewModel() {

    fun saveLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            locationRepo.saveLocation(latitude, longitude)
        }
    }
}