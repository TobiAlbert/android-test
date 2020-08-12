package ng.riby.androidtest.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import ng.riby.androidtest.data.repository.ILocationRepository

class MapViewModel(private val repo: ILocationRepository): ViewModel() {

    fun getLocations(): LiveData<List<LatLng>> =
            Transformations.map(repo.getRoutes()) {
                return@map it.map { l -> LatLng(l.latitude, l.longitude) }
            }


    fun deleteNotes() {
        viewModelScope.launch {
            repo.deleteAll()
        }
    }
}