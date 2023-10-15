package com.plcoding.graphqlmobileapp.data

import com.plcoding.PointQuery
import com.plcoding.PointsQuery
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.graphqlmobileapp.domain.Location
import com.plcoding.graphqlmobileapp.domain.SignalData
import com.plcoding.graphqlmobileapp.domain.SimpleEdge
import com.plcoding.graphqlmobileapp.domain.SimpleNode
import com.plcoding.graphqlmobileapp.domain.SimplePoint
import com.plcoding.graphqlmobileapp.domain.UnitType

fun PointsQuery.Points.toSimplePoints(): SimplePoint {
    return SimplePoint(
        edges = this.edges?.map { edge ->
            SimpleEdge(
                node = SimpleNode(
                    id = edge.node.id,
                    name = edge.node.name!!,
                    description = edge.node.description
                )
            )
        }
    )
}

fun PointQuery.Points.toDetailedPoint(): DetailedPoint? {
    val node = this.edges?.get(0)!!.node.signals?.let { it.edges?.get(0) ?: null }?.let { it.node }
    return if (node == null) null
    else DetailedPoint(
        id = node.point.id,
        name = node.point.name,
        description = node.point.description,
        timestamp = node.timestamp,
        type = node.type,
        unit = UnitType.safeValueOf(node.unit.rawValue),
        location = node.location?.let {
            Location(
                lat = it.lat,
                lon = it.lon
            )
        },
        signalData = SignalData(
            numericValue = node.data.numericValue,
            rawValue = node.data.rawValue,
            time = node.timestamp
        )
    )
}

//fun PointSummaryByLatestSignalQuery.Signals.toPointSummaryByLatestSignal(): PointWithLatestSignal {
//    val edge = this.edges?.let { it[0] }!!
//    return PointWithLatestSignal(
//        id = edge.node.id,
//        timestamp = edge.node.timestamp,
//        point = SimplePoint(
//            edges = listOf(
//                SimpleEdge(
//                    node = SimpleNode(
//                        id = edge.node.point.id,
//                        name = edge.node.point.name,
//                        description = edge.node.point.description
//                    )
//                )
//            )
//        ),
//        signalData = SignalData(
//            numericValue = "0",
//            rawValue = "0"
//        ),
//        location = edge.node.location?.let {
//            Location(
//                lat = it.lat,
//                lon = it.lon
//            )
//        },
//        unit = UnitType.safeValueOf(edge.node.unit.rawValue)
//
//    )
//
//}