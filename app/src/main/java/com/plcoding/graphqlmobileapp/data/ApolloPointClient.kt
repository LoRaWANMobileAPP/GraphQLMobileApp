package com.plcoding.graphqlmobileapp.data

import com.apollographql.apollo3.ApolloClient
import com.plcoding.PointsQuery
import com.plcoding.PointQuery
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.graphqlmobileapp.domain.PointClient
import com.plcoding.graphqlmobileapp.domain.SimplePoint

class ApolloPointClient(
    private val apolloClient: ApolloClient
) : PointClient//, SignalClient
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

//    override suspend fun getPointSummaryByLatestSignal(pointId: String): PointWithLatestSignal? {
//        return try {
//            var signals = apolloClient
//                .query(PointSummaryByLatestSignalQuery(pointId))
//                .execute()
//                .data
//                ?.signals
//
//            signals?.toPointSummaryByLatestSignal()
//        } catch (ex: Exception) {
//            println(ex)
//            null
//        }
//    }


//    override suspend fun getSignals(): List<SimpleSignal>? {
//        return try {
//            var signals = apolloClient
//                .query(LatestSignalsByTypeQuery())
//                .execute()
//                .data
//                ?.signals
//
//            listOf(signals?.toSimpleSignals()).filterNotNull()
//        } catch (ex: Exception) {
//            println(ex)
//            null
//        }
//    }

    override suspend fun getPoint(id: String): DetailedPoint? {
        val points = apolloClient
            .query(PointQuery(id))
            .execute()
            .data
            ?.points
        return points?.toDetailedPoint()
    }
}