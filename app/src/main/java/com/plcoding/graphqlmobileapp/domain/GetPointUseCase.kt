package com.plcoding.graphqlmobileapp.domain

class GetPointUseCase(
    private val pointClient: PointClient
) {
    suspend fun execute(id: String): DetailedPoint? {
        return pointClient.getPoint(id)
    }
}