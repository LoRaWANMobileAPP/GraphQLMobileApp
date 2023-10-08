package com.plcoding.graphqlmobileapp.domain

data class PointWithLatestSignal(
    val id: String,
    val timestamp: Any?,
    val location: Location?,
    val unit: UnitType,
    val point: SimplePoint,
    val signalData: SignalData
)
