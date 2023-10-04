package com.plcoding.graphqlmobileapp.domain

class GetPointsUseCase(
    private val pointClient: PointClient
) {
    suspend fun execute(): SimplePoint? {
        return pointClient.getPoints()
    }
}