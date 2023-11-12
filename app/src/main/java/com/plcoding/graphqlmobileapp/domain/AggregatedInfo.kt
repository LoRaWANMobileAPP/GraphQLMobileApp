package com.plcoding.graphqlmobileapp.domain

import java.sql.Timestamp

data class AggregatedInfo(
    val min: Double?,
    val timeOfMin: Timestamp?,
    val max: Double?,
    val timeOfMax: Timestamp?,
    val avg: Double?
)
