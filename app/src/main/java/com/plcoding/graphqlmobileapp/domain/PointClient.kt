package com.plcoding.graphqlmobileapp.domain

interface PointClient {

    suspend fun getPoints(): SimplePoint?

    suspend fun getPointSummaryByLatestSignal(pointId: String): PointWithLatestSignal?
//    suspend fun getPoint(id: String): DetailedPoint?
}