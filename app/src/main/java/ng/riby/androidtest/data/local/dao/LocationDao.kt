package ng.riby.androidtest.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ng.riby.androidtest.data.local.entities.LocationEntity

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationEntity)

    @Query("Delete FROM locations")
    suspend fun deleteAll()

    @Query("SELECT * FROM locations")
    fun getLocations(): LiveData<List<LocationEntity>>
}