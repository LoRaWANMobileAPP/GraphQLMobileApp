package com.plcoding.graphqlmobileapp.data

import com.plcoding.SignalsByFilterQuery
import com.plcoding.graphqlmobileapp.domain.DetailedSignalData
import com.plcoding.graphqlmobileapp.domain.SignalData
import com.plcoding.graphqlmobileapp.domain.UnitType

fun SignalsByFilterQuery.Signals.toDetailedSignalData(): List<DetailedSignalData>? {
    return this.edges?.map { edge ->
        DetailedSignalData(
            unit = UnitType.safeValueOf(edge.node.unit.rawValue),
            signalData = SignalData(
                numericValue = edge.node.data.numericValue,
                rawValue = edge.node.data.rawValue
            )
        )
    }
}