package fr.istic.mob.starcn.database.entity

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "trips")
data class TripsEntity(
    @PrimaryKey @ColumnInfo(name="trip_id") val id: String,
    @ColumnInfo(name="route_id") val routeId : String,
    @ColumnInfo(name= "service_id") val serviceId: String,
    @ColumnInfo(name="trip_headsign") val tripHeadsing: String,
    @ColumnInfo(name="direction_id") val directionId: String,
    @ColumnInfo(name="block_id") val blockId: String,
    @ColumnInfo(name="weelchair_accessible") val weelchairAccesible: String
    )
