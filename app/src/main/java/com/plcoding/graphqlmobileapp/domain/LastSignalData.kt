package com.plcoding.graphqlmobileapp.domain

import java.sql.Timestamp

data class LastSignalData (
    val numericValue: Double?,
    val rawValue: String,
    val time: Timestamp,
    val type: String,
    val unit: String
)
