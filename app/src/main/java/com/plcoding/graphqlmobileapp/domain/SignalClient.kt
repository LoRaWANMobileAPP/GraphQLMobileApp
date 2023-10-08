package com.plcoding.graphqlmobileapp.domain

interface SignalClient {

    suspend fun getSignals(id: String): List<DetailedSignalData>?
}