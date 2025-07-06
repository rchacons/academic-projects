package fr.istic.mob.starcn.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routes" )
data class RoutesEntity(
    @PrimaryKey @ColumnInfo(name = "route_id") val id : String,
    @ColumnInfo(name = "route_short_name") val shortName: String,
    @ColumnInfo(name="route_long_name") val longName: String,
    @ColumnInfo(name="route_desc") val description : String,
    @ColumnInfo(name="route_type") val type : String,
    @ColumnInfo(name="route_color") val color : String,
    @ColumnInfo(name="route_text_color")  val textColor : String)

