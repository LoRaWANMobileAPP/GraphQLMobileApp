package com.plcoding.graphqlmobileapp.domain

interface AggregationClient {
    suspend fun getAggregatedInfo(signals: List<DetailedSignalData>?): AggregatedInfo
}