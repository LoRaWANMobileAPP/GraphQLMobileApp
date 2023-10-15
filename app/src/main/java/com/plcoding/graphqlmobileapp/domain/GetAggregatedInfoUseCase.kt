package com.plcoding.graphqlmobileapp.domain

import com.apollographql.apollo3.api.Optional
import com.plcoding.type.Window
import java.sql.Timestamp

class GetAggregatedInfoUseCase(
    val aggregationClient: AggregationClient
) {
    suspend fun execute(from: Timestamp, to: Timestamp, id: String, type: String, window: Optional<Window?> = Optional.Absent) {
        aggregationClient.getAggregatedInfo(from, to, id, type, window)
    }
}