package com.plcoding.graphqlmobileapp.domain

interface PointClient {

    suspend fun getPoints(): SimplePoint?
    suspend fun getPoint(id: String): DetailedPoint?
    suspend fun getPointSpecification(): List<PointSpecification>?
}