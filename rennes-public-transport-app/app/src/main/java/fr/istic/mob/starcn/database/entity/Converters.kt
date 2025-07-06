package fr.istic.mob.starcn.database.entity

import android.util.Log
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Converters {

    //TODO: supprimer apres, au final il y aura pas besoin mais ca peut
    // etre interessant pour les transformer apres
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromDateTime(time: LocalTime): String {
            val formatter = DateTimeFormatter.ofPattern("kk:mm:ss")
            return time.format(formatter)
        }

        @TypeConverter
        @JvmStatic
        fun toDateTime(formattedDate: String): LocalTime {
            val formatter = DateTimeFormatter.ofPattern("kk:mm:ss")
            return LocalTime.parse(formattedDate,formatter)
        }
    }
}