package com.plcoding.graphqlmobileapp.domain

data class DetailedPoint(
    val id: String,
    val name: String?,
    val description: String?,
    val timestamp: Any?,
    val type : String,
    val unit: UnitType,
    val location: Location?,
    val signalData: SignalData
)
