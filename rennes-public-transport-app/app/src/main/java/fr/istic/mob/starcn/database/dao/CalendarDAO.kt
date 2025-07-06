package fr.istic.mob.starcn.database.dao

import androidx.room.*
import fr.istic.mob.starcn.database.entity.CalendarEntity
import fr.istic.mob.starcn.database.entity.RoutesEntity

@Dao
interface CalendarDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(calendarEntity: CalendarEntity)

    @Update
    fun update(calendarEntity: CalendarEntity)

    @Delete
    fun delete(calendarEntity: CalendarEntity)

    @Query("delete from calendar")
    fun deleteAllCalendars()

    @Query("select * from calendar")
    fun getAllCalendars(): List<CalendarEntity>

}