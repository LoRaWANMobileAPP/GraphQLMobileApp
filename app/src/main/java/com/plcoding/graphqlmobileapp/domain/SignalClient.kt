package com.plcoding.graphqlmobileapp.domain

interface SignalClient {

    suspend fun getSignals(): List<SimpleSignal>?
}