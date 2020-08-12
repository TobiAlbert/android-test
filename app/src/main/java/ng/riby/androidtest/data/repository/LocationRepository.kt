package ng.riby.androidtest.data.repository

import androidx.lifecycle.LiveData
import ng.riby.androidtest.data.local.dao.LocationDao
import ng.riby.androidtest.data.local.entities.LocationEntity
import java.util.*

class LocationRepository(private val locationDao: LocationDao): ILocationRepository {

    override suspend fun saveLocation(latitude: Double, longitude: Double) {
        val date = Date()
        val locationEntity = LocationEntity(
            latitude = latitude,
            longitude = longitude,
            createdAt = date,
            updatedAt = date
        )
        locationDao.insert(locationEntity)
    }

    override fun getRoutes(): LiveData<List<LocationEntity>> = locationDao.getLocations()

    override suspend fun deleteAll() = locationDao.deleteAll()
}

interface ILocationRepository {
    suspend fun saveLocation(latitude: Double, longitude: Double)
    fun getRoutes(): LiveData<List<LocationEntity>>
    suspend fun deleteAll()
}