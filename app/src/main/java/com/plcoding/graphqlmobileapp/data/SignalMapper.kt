package com.plcoding.graphqlmobileapp.data

import com.plcoding.LatestSignalsByTypeQuery
import com.plcoding.graphqlmobileapp.domain.SimpleData
import com.plcoding.graphqlmobileapp.domain.SimpleEdge
import com.plcoding.graphqlmobileapp.domain.SimpleNode
import com.plcoding.graphqlmobileapp.domain.SimpleSignal

fun LatestSignalsByTypeQuery.Signals.toSimpleSignals(): SimpleSignal? {
    return SimpleSignal(
        edges = this.edges?.map { edge ->
            SimpleEdge(
                node = SimpleNode(
                    name = edge.node.type,
                    data = SimpleData(
                        numericValue = edge.node.data.numericValue,
                        rawValue = edge.node.data.rawValue
                    ),
                    space = null
                )
            )
        }
    )
}