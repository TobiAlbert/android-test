package ng.riby.androidtest.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ng.riby.androidtest.data.local.entities.LocationEntity

@Dao
interface LocationDao {

    @Query("SELECT * FROM locations ORDER BY updated_at ASC LIMIT 1")
    suspend fun getStartLocation(): LocationEntity

    @Query("SELECT * FROM locations ORDER BY updated_at DESC LIMIT 1")
    suspend fun getStopLocation(): LocationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationEntity)

    @Query("Delete FROM locations")
    suspend fun deleteAll()

    @Query("SELECT * FROM locations")
    fun getLocations(): LiveData<List<LocationEntity>>
}