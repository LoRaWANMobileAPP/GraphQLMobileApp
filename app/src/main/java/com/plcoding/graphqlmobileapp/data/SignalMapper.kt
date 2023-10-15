package com.plcoding.graphqlmobileapp.data

import com.plcoding.SignalsByFilterQuery
import com.plcoding.graphqlmobileapp.domain.AggregatedInfo
import com.plcoding.graphqlmobileapp.domain.DetailedSignalData
import com.plcoding.graphqlmobileapp.domain.SignalData
import com.plcoding.graphqlmobileapp.domain.UnitType

fun SignalsByFilterQuery.Signals.toDetailedSignalData(): List<DetailedSignalData>? {
    return this.edges?.map { edge ->
        edge.node.timestamp
        DetailedSignalData(
            unit = UnitType.safeValueOf(edge.node.unit.rawValue),
            signalData = SignalData(
                numericValue = edge.node.data.numericValue,
                rawValue = edge.node.data.rawValue,
                time = edge.node.timestamp
            )
        )
    }
}

//fun SignalsByFilterQuery.Signals.toAggregatedInfo(): AggregatedInfo {
//    val signals = this.toDetailedSignalData()
//    val signalsWithValue = signals?.filter { it.signalData.numericValue != null }
//    val min = signalsWithValue?.minBy { it.signalData.numericValue!! }
//    val max = signalsWithValue?.maxBy { it.signalData.numericValue!! }
//    val avg = (signalsWithValue?.sumOf { it.signalData.numericValue!! }?: 0.0) / (signalsWithValue?.size?: 1)
//
//    return AggregatedInfo(
//        min = min?.signalData?.numericValue,
//        timeOfMin = min?.signalData?.time,
//        max = max?.signalData?.numericValue,
//        timeOfMax = max?.signalData?.time,
//        avg = avg
//    )
//}