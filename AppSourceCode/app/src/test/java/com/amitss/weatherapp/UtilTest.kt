package com.amitss.weatherapp

import com.amitss.weatherapp.database.entity.CityEntity
import com.amitss.weatherapp.service.model.*
import com.amitss.weatherapp.view.util.getCityEntity
import com.amitss.weatherapp.view.util.initModel
import com.amitss.weatherapp.view.util.isInternetAvailable
import com.amitss.weatherapp.view.util.loadImage
import org.junit.Assert
import org.junit.Test


class UtilTest {

    @Test
    fun testInitModel() {
        Assert.assertEquals(initModel(null), null)
    }

    @Test
    fun testCityEntity() {
        Assert.assertEquals(getCityEntity(null), null)
        val cityEntity = CityEntity("", "", "", 0.0, 0.0, 0, "")
        val weatherUrl = WeatherUrl("")
        val region = Region("")
        val country =
            Country("")
        val areaName = AreaName("")
        val result = Result(
            arrayListOf(areaName), arrayListOf(country),
            arrayListOf(region), 0.0, 0.0, 0,
            arrayListOf(weatherUrl)
        )
        Assert.assertEquals(getCityEntity(result), cityEntity)
    }

    @Test
    fun testInternetAvailable() {
        Assert.assertEquals(isInternetAvailable(null), null)
    }

    @Test
    fun loadImage() {
        Assert.assertEquals(loadImage(null, ""), null)
        Assert.assertEquals(loadImage(null, null), null)
    }

}