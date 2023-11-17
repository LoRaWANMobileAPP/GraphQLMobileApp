package com.plcoding.graphqlmobileapp.data

import com.plcoding.ExistingSignalsSpecificPointQuery
import com.plcoding.PointQuery
import com.plcoding.PointsQuery
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.graphqlmobileapp.domain.LastSignalData
import com.plcoding.graphqlmobileapp.domain.Location
import com.plcoding.graphqlmobileapp.domain.PointSpecification
import com.plcoding.graphqlmobileapp.domain.SignalData
import com.plcoding.graphqlmobileapp.domain.SimpleEdge
import com.plcoding.graphqlmobileapp.domain.SimpleNode
import com.plcoding.graphqlmobileapp.domain.SimplePoint
import com.plcoding.graphqlmobileapp.domain.UnitType
import com.plcoding.graphqlmobileapp.utils.Helper.stringToTimestamp

fun PointsQuery.Points.toSimplePoints(): SimplePoint {
    val edges = SimplePoint(
        edges = this.edges?.map { edge ->
            SimpleEdge(
                node = SimpleNode(
                    id = edge.node.id,
                    name = edge.node.name!!,
                    description = edge.node.description,
                    lastSignals = edge.node.signals?.edges?.groupBy { it.node.type }?.map {
                        val lastValue = it.value.maxBy { edge ->
                            edge.node.timestamp.toString()
                        }
                        LastSignalData(
                            time = stringToTimestamp(lastValue.node.timestamp.toString()),
                            rawValue = lastValue.node.data.rawValue,
                            numericValue = lastValue.node.data.numericValue,
                            unit = lastValue.node.unit.name,
                            type = lastValue.node.type
                        )
                    } ?: emptyList()
                )
            )
        }
    )
    return edges
}

fun PointQuery.Points.toDetailedPoint(): DetailedPoint? {
    val node = this.edges?.get(0)!!.node.signals?.let { it.edges?.get(0) ?: null }?.let { it.node }
    return if (node == null) null
    else DetailedPoint(
        id = node.point.id,
        name = node.point.name,
        description = node.point.description,
        timestamp = node.timestamp?.let { stringToTimestamp( node.timestamp.toString()) },
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
            time = node.timestamp?.let { stringToTimestamp(it.toString()) }
        )
    )
}

fun ExistingSignalsSpecificPointQuery.Points.toPointSpecification(): List<PointSpecification>? {
    return this.edges?.map {
        PointSpecification(
            id = it.node.id,
            name = it.node.name,
            description = it.node.description,
            lastActive = it.node.lastActive,
            existingSignalTypes = it.node.existingSignalTypes
        )
    }
}


