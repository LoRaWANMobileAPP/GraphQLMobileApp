package com.plcoding.graphqlmobileapp.domain

class GetAggregatedInfoUseCase(
    val aggregationClient: AggregationClient
) {
    suspend fun execute(signals: List<DetailedSignalData>?): AggregatedInfo? {
        return aggregationClient.getAggregatedInfo(signals)
    }
}