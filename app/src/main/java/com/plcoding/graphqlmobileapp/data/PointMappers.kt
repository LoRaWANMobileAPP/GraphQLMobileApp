package com.plcoding.graphqlmobileapp.data

import com.plcoding.PointQuery
import com.plcoding.PointSummaryByLatestSignalQuery
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.PointsQuery
import com.plcoding.graphqlmobileapp.domain.Location
import com.plcoding.graphqlmobileapp.domain.PointWithLatestSignal
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

fun PointSummaryByLatestSignalQuery.Signals.toPointSummaryByLatestSignal(): PointWithLatestSignal {
    val edge = this.edges?.let { it[0] }!!
    return PointWithLatestSignal(
        id = edge.node.id,
        timestamp = edge.node.timestamp,
        point = SimplePoint(
            edges = listOf(
                SimpleEdge(
                    node = SimpleNode(
                        id = edge.node.point.id,
                        name = edge.node.point.name,
                        description = edge.node.point.description
                    )
                )
            )
        ),
        signalData = SignalData(
            numericValue = "0",
            rawValue = "0"
        ),
        location = edge.node.location?.let {
            Location(
                lat = it.lat,
                lon = it.lon
            )
        },
        unit = UnitType.safeValueOf(edge.node.unit.rawValue)

    )

}