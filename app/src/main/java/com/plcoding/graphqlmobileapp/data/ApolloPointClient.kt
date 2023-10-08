package com.plcoding.graphqlmobileapp.data

import com.apollographql.apollo3.ApolloClient
import com.plcoding.PointsQuery
import com.plcoding.PointQuery
import com.plcoding.SignalsByFilterQuery
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.graphqlmobileapp.domain.DetailedSignalData
import com.plcoding.graphqlmobileapp.domain.PointClient
import com.plcoding.graphqlmobileapp.domain.SignalClient
import com.plcoding.graphqlmobileapp.domain.SimplePoint
import java.sql.Timestamp
import java.time.LocalDateTime

class ApolloPointClient(
    private val apolloClient: ApolloClient
) : PointClient, SignalClient
{
    override suspend fun getPoints(): SimplePoint? {
        return try {
            var points = apolloClient
                .query(PointsQuery())
                .execute()
                .data
                ?.points

            points?.toSimplePoints()
        } catch (ex: Exception) {
            println(ex)
            null
        }
    }

    override suspend fun getPoint(id: String): DetailedPoint? {
        val points = apolloClient
            .query(PointQuery(id))
            .execute()
            .data
            ?.points
        return points?.toDetailedPoint()
    }

    override suspend fun getSignals(id: String): List<DetailedSignalData>? {
        val signals = apolloClient
            .query(SignalsByFilterQuery(id, "2023-10-08T20:00:00.0000", "2023-10-08T14:00:00.0000"))
            .execute()
            .data
            ?.signals
        return signals?.toDetailedSignalData()
    }
}