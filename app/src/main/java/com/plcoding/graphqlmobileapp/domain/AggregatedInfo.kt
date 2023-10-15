package com.plcoding.graphqlmobileapp.domain

data class AggregatedInfo(
    val min: Double?,
    val timeOfMin: Any?,
    val max: Double?,
    val timeOfMax: Any?,
    val avg: Double?
)
