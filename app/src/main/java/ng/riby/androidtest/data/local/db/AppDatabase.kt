package ng.riby.androidtest.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ng.riby.androidtest.data.local.converters.CustomTypeConverters
import ng.riby.androidtest.data.local.dao.LocationDao
import ng.riby.androidtest.data.local.entities.LocationEntity

@Database(entities = [LocationEntity::class], exportSchema = false, version = 1)
@TypeConverters(CustomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}