package fr.istic.mob.starcn.database.dao

import androidx.room.*
import fr.istic.mob.starcn.database.entity.CalendarEntity
import fr.istic.mob.starcn.database.entity.TripsEntity

@Dao
interface TripsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tripsEntity: TripsEntity)

    @Update
    fun update(tripsEntity: TripsEntity)

    @Delete
    fun delete(tripsEntity: TripsEntity)

    @Query("delete from trips")
    fun deleteAllTrips()

    @Query("select * from trips")
    fun getAllTrips(): List<TripsEntity>

    @Query("select trip_headsign from trips t where t.route_id = :routeId group by t.direction_id")
    fun getTripDirectionFromRoute(routeId : String) : List<String>

    @Query("select distinct trip_headsign from trips t where t.route_id = :routeId")
    fun getAllTripDirectionFromRoute(routeId : String) : List<String>

}