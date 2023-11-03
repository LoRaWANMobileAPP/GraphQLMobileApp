package com.plcoding.graphqlmobileapp.domain

data class LastSignalData (
    val numericValue: Double?,
    val rawValue: String,
    val time: Any?,
    val type: String,
    val unit: String
)
