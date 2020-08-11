package ng.riby.androidtest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
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

    override suspend fun saveLocations(locations: List<Pair<Double, Double>>) {
        locations.forEach { saveLocation(it.first, it.second) }
    }

    override suspend fun calculateDistanceTravelled(): Double = 0.0
}

interface ILocationRepository {
    suspend fun saveLocation(latitude: Double, longitude: Double)
    suspend fun saveLocations(locations: List<Pair<Double, Double>>)
    suspend fun calculateDistanceTravelled(): Double
}