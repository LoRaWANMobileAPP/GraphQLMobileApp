package com.plcoding.graphqlmobileapp.data

import com.apollographql.apollo3.ApolloClient
import com.plcoding.ExistingSignalsSpecificPointQuery
import com.plcoding.PointsQuery
import com.plcoding.PointQuery
import com.plcoding.SignalsByFilterQuery
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.graphqlmobileapp.domain.DetailedSignalData
import com.plcoding.graphqlmobileapp.domain.PointClient
import com.plcoding.graphqlmobileapp.domain.PointSpecification
import com.plcoding.graphqlmobileapp.domain.SignalClient
import com.plcoding.graphqlmobileapp.domain.SimplePoint
import com.plcoding.graphqlmobileapp.utils.SensorHelper
import java.sql.Timestamp
import java.util.Date

class ApolloPointClient(
    private val apolloClient: ApolloClient
) : PointClient, SignalClient
{
    override suspend fun getPoints(): SimplePoint? {
        return try {
            var points = apolloClient
                .query(PointsQuery(getRequiredLastParameterToCoverAllSignals()))
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

    private fun getRequiredLastParameterToCoverAllSignals(): Int {
        return SensorHelper.getSignalTypeNumber()
    }

    override suspend fun getPointSpecification(): List<PointSpecification>? {
        return try {
            var points = apolloClient
                .query(ExistingSignalsSpecificPointQuery())
                .execute()
                .data
                ?.points

            points?.toPointSpecification()
        } catch (ex: Exception) {
            println(ex)
            null
        }
    }

    override suspend fun getSignals(id: String, fromDate: Timestamp?, toDate: Timestamp?): List<DetailedSignalData>? {
        val currentDateTime = Date()
        val fromDateParameter = Timestamp(fromDate?.time ?: currentDateTime.time - 86400000).toString()
        val toDateParameter = Timestamp(toDate?.time ?: currentDateTime.time).toString()
        val signals = apolloClient
            .query(SignalsByFilterQuery(id, fromDateParameter, toDateParameter))
            .execute()
            .data
            ?.signals
        return signals?.toDetailedSignalData()
    }

}
