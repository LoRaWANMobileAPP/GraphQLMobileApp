package com.plcoding.graphqlmobileapp.data

import com.apollographql.apollo3.ApolloClient
import com.plcoding.LatestSignalsByTypeQuery
import com.plcoding.PointSummaryByLatestSignalQuery
import com.plcoding.PointsQuery
import com.plcoding.graphqlmobileapp.domain.PointClient
import com.plcoding.graphqlmobileapp.domain.PointWithLatestSignal
import com.plcoding.graphqlmobileapp.domain.SignalClient
import com.plcoding.graphqlmobileapp.domain.SimplePoint
import com.plcoding.graphqlmobileapp.domain.SimpleSignal

class ApolloPointClient(
    private val apolloClient: ApolloClient
) : PointClient, SignalClient {
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

    override suspend fun getPointSummaryByLatestSignal(pointId: String): PointWithLatestSignal? {
        return try {
            var signals = apolloClient
                .query(PointSummaryByLatestSignalQuery(pointId))
                .execute()
                .data
                ?.signals

            signals?.toPointSummaryByLatestSignal()
        } catch (ex: Exception) {
            println(ex)
            null
        }
    }


    override suspend fun getSignals(): List<SimpleSignal>? {
        return try {
            var signals = apolloClient
                .query(LatestSignalsByTypeQuery())
                .execute()
                .data
                ?.signals

            listOf(signals?.toSimpleSignals()).filterNotNull()
        } catch (ex: Exception) {
            println(ex)
            null
        }
    }

//    override suspend fun getPoint(id: String): DetailedPoint? {
//        val filter: Optional<StringComparisonFilter?> = Optional.present(StringComparisonFilter(_EQ = Optional.present(id)))
//        val point = apolloClient
//            .query(PointQuery(PointFilterInput(id = filter)))
//            .execute()
//            .data
//            ?.point
//        return point?.toDetailedPoint()
//    }
}