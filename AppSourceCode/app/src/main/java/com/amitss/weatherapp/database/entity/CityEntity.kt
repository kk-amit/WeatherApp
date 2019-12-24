package com.amitss.weatherapp.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * City table created using Room DB, where latitude value will be unique.
 */
@Entity(tableName = "city_table", indices = [Index(value = ["latitude"], unique = true)])
data class CityEntity(
    /**
     *  city_table area column
     */
    @ColumnInfo(name = "area_name")
    var areaName: String?,

    /**
     *  City table country column
     */
    @ColumnInfo(name = "country")
    var country: String?,

    /**
     * City Table region column
     */
    @ColumnInfo(name = "region")
    var region: String?,

    /**
     * City Table latitude column
     */
    @ColumnInfo(name = "latitude")
    var latitude: Double?,

    /**
     * City Table longitude column
     */
    @ColumnInfo(name = "longitude")
    var longitude: Double?,
    @ColumnInfo(name = "population")

    /**
     * City Table weather_url column
     */
    var population: Int?,
    @ColumnInfo(name = "weather_url")
    var weatherUrl: String?
) {
    /**
     * City Table auto generated primary key uid.
     */
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}


