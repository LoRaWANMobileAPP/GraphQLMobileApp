package com.plcoding.graphqlmobileapp.utils

import com.plcoding.graphqlmobileapp.R

data class SensorImage(
    val sensorType: String,
    val sensorImageUrl: Int
)

class ImageHelper {

    private val sensorImageUrls = listOf(
        SensorImage("DoorSensor", R.drawable.doorsensor),
        SensorImage("TemperatureSensor", R.drawable.temperaturesensor),
        SensorImage("ComfortSensor", R.drawable.comfortsensor),
        SensorImage("Default", R.drawable.temperaturesensor)
    )

    fun getSensorImageUrl(sensorType: String): Int {
        val i = sensorImageUrls.singleOrNull() { it.sensorType == sensorType }
        return i?.sensorImageUrl ?: R.drawable.temperaturesensor
    }
}