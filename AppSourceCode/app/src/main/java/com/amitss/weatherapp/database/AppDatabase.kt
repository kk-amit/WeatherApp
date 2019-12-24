package com.amitss.weatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amitss.weatherapp.R
import com.amitss.weatherapp.database.dao.CityDao
import com.amitss.weatherapp.database.entity.CityEntity
import com.amitss.weatherapp.view.util.APP_DB_NAME
import timber.log.Timber

/**
 * App database defines entities, schema and version
 */
@Database(
    entities = [CityEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * City dao to refer the City Table Queries.
     */
    abstract fun cityDao(): CityDao

    /**
     * Singleton instance of AppDatabase
     */
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            Timber.d(context.getString(R.string.str_app_db_instance))
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    APP_DB_NAME
                ).build()
            }
            return INSTANCE as AppDatabase
        }
    }
}
