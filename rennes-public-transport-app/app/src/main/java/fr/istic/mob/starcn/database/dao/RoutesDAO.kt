package fr.istic.mob.starcn.database.dao

import androidx.room.*
import fr.istic.mob.starcn.database.entity.RoutesEntity
import fr.istic.mob.starcn.database.entity.StopsEntity
import fr.istic.mob.starcn.database.entity.StopsTimeEntity

@Dao
interface RoutesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(routesEntity: RoutesEntity)

    @Update
    fun update(routesEntity: RoutesEntity)

    @Delete
    fun delete(routesEntity: RoutesEntity)

    @Query("delete from routes")
    fun deleteAllRoutes()

    @Query("select * from routes")
    fun getAllRoutes(): List<RoutesEntity>

    @Query("SELECT r.* from stops s \n" +
            "INNER JOIN stops_times st ON st.stop_id = s.stop_id\n" +
            "INNER JOIN trips t ON st.trip_id = t.trip_id\n" +
            "INNER JOIN routes r ON t.route_id = r.route_id\n" +
            "where s.stop_name = :stopName \n" +
            "GROUP BY r.route_id")
    fun getRoutesFromStopId(stopName : String) : List<RoutesEntity>


}