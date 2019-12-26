package com.amitss.weatherapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.amitss.weatherapp.database.AppDatabase
import com.amitss.weatherapp.database.dao.CityDao
import com.amitss.weatherapp.database.entity.CityEntity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomDBInstrumentedTest {

    private var cityDao: CityDao? = null
    private var appDB: AppDatabase? = null

    @Before
    fun createDB() {
        val context = InstrumentationRegistry.getInstrumentation().context
        appDB = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()

        cityDao = appDB?.cityDao()

    }

    @After
    fun closeDb() {
        cityDao?.delete()
        appDB?.close()
    }

    @Test
    fun checkEmptyDB() {
        val cityList = cityDao?.getAll()
        Assert.assertEquals(cityList?.size, 0)
        Assert.assertNotEquals(cityList?.size, 1)
    }


    @Test
    fun checkInsertCityData() {
        val cityEntity = CityEntity("Delhi", "India", "India", 0.0, 0.0, 1, "")
        cityDao?.insert(cityEntity)
        Assert.assertEquals(cityDao?.getLastCity(), cityEntity)
        Assert.assertEquals(cityDao?.getAll()?.size, 1)
        Assert.assertNotEquals(cityDao?.getAll()?.size, 0)
    }

    @Test
    fun checkInsertCityDataMatch() {
        val cityEntity = CityEntity("Delhi", "India", "India", 0.0, 0.0, 1, "")
        cityDao?.insert(cityEntity)
        val cityEntityList = cityDao?.getAll()
        Assert.assertEquals(cityEntity, cityEntityList?.get(0))
    }

    @Test
    fun checkDeleteCityData() {
        val cityEntity = CityEntity("Delhi", "India", "India", 0.0, 0.0, 1, "")
        cityDao?.insert(cityEntity)
        Assert.assertEquals(cityDao?.getAll()?.size, 1)
        cityDao?.delete()
        val cityEntityList = cityDao?.getAll()
        Assert.assertEquals(cityEntityList?.size, 0)
        Assert.assertNotEquals(cityEntityList?.size, 1)
    }
}