package com.plcoding.graphqlmobileapp.domain

import com.apollographql.apollo3.api.Optional
import com.plcoding.type.Window
import java.sql.Timestamp

interface AggregationClient {
    suspend fun getAggregatedInfo(signals: List<DetailedSignalData>?): AggregatedInfo
}