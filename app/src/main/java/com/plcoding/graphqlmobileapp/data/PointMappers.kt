package com.plcoding.graphqlmobileapp.data

import com.plcoding.PointQuery
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.graphqlmobileapp.domain.SimpleSpace
import com.plcoding.PointsWithSpaceQuery
import com.plcoding.graphqlmobileapp.domain.SimpleEdge
import com.plcoding.graphqlmobileapp.domain.SimpleNode
import com.plcoding.graphqlmobileapp.domain.SimplePoint

fun PointsWithSpaceQuery.Points.toSimplePoints(): SimplePoint {
    return SimplePoint(
        edges = this.edges?.map { edge ->
            SimpleEdge(
                node = SimpleNode(
                    name = edge.node.name!!,
                    space = SimpleSpace(
                        name = edge.node.space?.name,
                        createdAt = edge.node.space?.id
                    ),
                    data = null
                )
            )
        }
    )
}

fun PointQuery.Point.toDetailedPoint(): DetailedPoint? {
    return DetailedPoint(
        name = this.name,
        description = this.description,
        space = SimpleSpace(
            name = this.space?.name,
            createdAt = this.space?.name
        )
    )
}