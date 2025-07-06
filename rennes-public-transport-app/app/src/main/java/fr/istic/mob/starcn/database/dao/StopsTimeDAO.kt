package fr.istic.mob.starcn.database.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import fr.istic.mob.starcn.database.entity.StopsTimeEntity


@Dao
interface StopsTimeDAO {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(stopsTimeEntity: StopsTimeEntity)

    @Update
    fun update(stopsTimeEntity: StopsTimeEntity)

    @Delete
    fun delete(stopsTimeEntity: StopsTimeEntity)

    @Query("delete from stops_times")
    fun deleteAllStopTimes()

    @Query("select * from stops_times")
    fun getAllStopTimes(): List<StopsTimeEntity>

    @RawQuery
    fun vacuumDb(supportSQLiteQuery: SupportSQLiteQuery?): Int

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStopsTime(stopsTimeEntityList: List<StopsTimeEntity>)


    @Query("SELECT st.* from stops_times st\n" +
            "    INNER JOIN trips t ON st.trip_id = t.trip_id\n" +
            "    INNER JOIN calendar c ON t.service_id = c.service_id\n" +
            "    where stop_id = :stopId AND arrival_time > :arrivalTime \n" +
            "    AND :date  BETWEEN start_date AND end_date\n" +
            "\tAND monday = :monday AND tuesday = :tuesday \n" +
            "\tAND wednesday = :wednesday AND thursday = :thursday \n" +
            "\tAND friday = :friday AND saturday = :saturday AND sunday = :sunday\n" +
            "    ORDER BY start_date,arrival_time")
    fun getStopTimesWithGivenIdFromTime(
        stopId: String,
        arrivalTime: String,
        date: String,
        monday : Int,
        tuesday : Int,
        wednesday : Int,
        thursday : Int,
        friday : Int,
        saturday : Int,
        sunday : Int,

    ): List<StopsTimeEntity>

    @Query("SELECT * FROM stops_times WHERE trip_id = :tripId AND   stop_id = :stopId")
    fun getStopTimeFromTripAndStop(tripId:String,stopId: String) : StopsTimeEntity


}