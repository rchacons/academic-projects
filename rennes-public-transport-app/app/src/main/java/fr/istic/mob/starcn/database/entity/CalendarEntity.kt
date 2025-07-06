package fr.istic.mob.starcn.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "calendar" )
data class CalendarEntity(
    @PrimaryKey @ColumnInfo(name = "service_id") val id : String,
    @ColumnInfo(name="monday") val monday : String,
    @ColumnInfo(name="tuesday") val tuesday : String,
    @ColumnInfo(name="wednesday") val wednesday : String,
    @ColumnInfo(name="thursday") val thursday : String,
    @ColumnInfo(name="friday") val friday : String,
    @ColumnInfo(name="saturday") val saturday : String,
    @ColumnInfo(name="sunday") val sunday : String,
    @ColumnInfo(name="start_date") val startDate : String,
    @ColumnInfo(name="end_date") val endDate : String
)
