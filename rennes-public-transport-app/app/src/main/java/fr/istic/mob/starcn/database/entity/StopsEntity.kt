package fr.istic.mob.starcn.database.entity

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "stops" )
data class StopsEntity(
    @PrimaryKey @ColumnInfo(name="stop_id") val id : String,
    @ColumnInfo(name="stop_name") val name : String,
    @ColumnInfo(name="stop_desc") val description : String,
    @ColumnInfo(name="stop_lat") val latitude : String,
    @ColumnInfo(name="stop_lon") val longitude : String,
    @ColumnInfo(name="weelchair_boarding") val weelchairBoarding : String
)


