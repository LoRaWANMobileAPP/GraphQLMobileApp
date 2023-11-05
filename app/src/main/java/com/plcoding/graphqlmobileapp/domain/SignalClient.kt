package com.plcoding.graphqlmobileapp.domain

import java.sql.Timestamp

interface SignalClient {

    suspend fun getSignals(id: String, fromDate: Timestamp?, toDate: Timestamp?): List<DetailedSignalData>?
}