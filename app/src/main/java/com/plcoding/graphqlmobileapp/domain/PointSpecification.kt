package com.plcoding.graphqlmobileapp.domain

data class PointSpecification(
    val id: String,
    val name: String?,
    val description: String?,
    val existingSignalTypes: List<String>?,
    val lastActive: Any?
)