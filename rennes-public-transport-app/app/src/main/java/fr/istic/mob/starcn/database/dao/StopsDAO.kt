package fr.istic.mob.starcn.database.dao

import androidx.room.*
import fr.istic.mob.starcn.database.entity.StopsEntity

@Dao
interface StopsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(stopsEntity: StopsEntity)

    @Update
    fun update(stopsEntity: StopsEntity)

    @Delete
    fun delete(stopsEntity: StopsEntity)

    @Query("delete from stops")
    fun deleteAllStops()

    @Query("select * from stops")
    fun getAllStops(): List<StopsEntity>

    @Query("SELECT * FROM stops WHERE stop_name LIKE :stopText || '%' GROUP BY stop_name " +
            "ORDER BY stop_name ASC ")
    fun getStopsFromGivenText(stopText:String) : List<StopsEntity>

    @Query("SELECT s.* FROM stops s\n" +
            "JOIN \n" +
            "\t(SELECT * FROM trips t \n" +
            "\tINNER JOIN stops_times AS st ON t.trip_id = st.trip_id\n" +
            "\tWHERE t.route_id=:route AND t.trip_headsign=:direction \n" +
            "\tGROUP BY stop_id ORDER BY cast(stop_sequence as NUMERIC) ASC) as t1\n" +
            "ON s.stop_id= t1.stop_id")
    fun searchStopWithRouteAndDirection(route:String,direction:String) : List<StopsEntity>
}