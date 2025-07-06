package fr.istic.mob.starcn.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.sql.Time
import java.time.LocalDateTime
import java.time.LocalTime

//TODO define foreign keys
@Entity(tableName = "stops_times")
data class StopsTimeEntity(
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo(name="trip_id") val tripId : String,
    @ColumnInfo(name="arrival_time") val arrivalTime : String,
    @ColumnInfo(name="departure_time") val departureTime : String,
    @ColumnInfo(name="stop_id") val stopId : String,
    @ColumnInfo(name="stop_sequence") val stopSequence: String
)

