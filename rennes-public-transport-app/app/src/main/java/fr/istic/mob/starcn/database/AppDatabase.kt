package fr.istic.mob.starcn.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import fr.istic.mob.starcn.database.dao.*
import fr.istic.mob.starcn.database.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.sql.Time

@Database(entities = [RoutesEntity::class, StopsEntity::class, StopsTimeEntity::class, TripsEntity::class, CalendarEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun routes(): RoutesDAO
    abstract fun stops() : StopsDAO
    abstract fun stopsTime() : StopsTimeDAO
    abstract fun trips() : TripsDAO
    abstract fun calendar() : CalendarDAO



    companion object{
        //Singleton qui evite l'ouverture simultanee de plusieurs instances de la bdd

        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getDatabase(context: Context): AppDatabase {
            // Si l'instance n'est pas null, on la renvoi
            // Sinon, on cree la base
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "star_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                // On renvoie l'instance
                instance
            }
        }

    }
}