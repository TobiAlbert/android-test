package ng.riby.androidtest.data.local.converters

import androidx.room.TypeConverter
import java.util.*

class CustomTypeConverters {
    @TypeConverter
    fun fromTimestampToDate(time: Long): Date = Date(time)

    @TypeConverter
    fun fromDateToTimestamp(date: Date): Long = date.time
}