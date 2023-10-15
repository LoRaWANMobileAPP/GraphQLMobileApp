package com.plcoding.graphqlmobileapp.domain

import com.apollographql.apollo3.api.Optional
import com.plcoding.SignalsByFilterQuery
import com.plcoding.type.Window
import java.sql.Timestamp

class GetAggregatedInfoUseCase(
    val aggregationClient: AggregationClient
) {
    suspend fun execute(signals: List<DetailedSignalData>?): AggregatedInfo? {
        return aggregationClient.getAggregatedInfo(signals)
    }
}