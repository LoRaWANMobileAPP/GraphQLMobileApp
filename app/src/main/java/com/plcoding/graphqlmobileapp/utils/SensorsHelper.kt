package com.plcoding.graphqlmobileapp.utils

data class SensorConfig(val name: String, val signalTypesNumber: Int)

class SensorsHelper {

    val sensors = listOf(
        SensorConfig(
            name = "ComfortSensor",
            signalTypesNumber = 2
        ),
        SensorConfig(
            name = "DoorSensor",
            signalTypesNumber = 1
        ),
        SensorConfig(
            name = "TemperatureSensor",
            signalTypesNumber = 1
        ),
    )

    public fun getRequiredLastParameterToCoverAllSignals() = sensors.maxBy { it.signalTypesNumber }.signalTypesNumber
}