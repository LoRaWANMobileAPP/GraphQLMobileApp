package com.plcoding.graphqlmobileapp.domain

import java.sql.Timestamp

data class SignalData(
    val numericValue: Double?,
    val rawValue: String,
    val time: Timestamp?
)
