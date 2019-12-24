package com.amitss.weatherapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.amitss.weatherapp.database.entity.CityEntity

/**
 *  City dao to perform the City related DB operations.
 */
@Dao
interface CityDao {

    /**
     * DB query to fetch last 10 record from City Table.
     */
    @Query("SELECT * FROM (SELECT * FROM city_table ORDER BY uid DESC LIMIT 10) ORDER BY uid ASC;")
    fun getAll(): List<CityEntity>

    /**
     * DB insert for City record.
     */
    @Insert
    fun insert(users: CityEntity)

    /**
     * Delete all the records from city table.
     */
    @Query("DELETE FROM city_table")
    fun delete()

}
