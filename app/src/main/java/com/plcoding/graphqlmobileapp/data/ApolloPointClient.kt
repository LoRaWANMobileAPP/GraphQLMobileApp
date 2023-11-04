package com.plcoding.graphqlmobileapp.data

import com.apollographql.apollo3.ApolloClient
import com.plcoding.ExistingSignalsSpecificPointQuery
import com.plcoding.PointsQuery
import com.plcoding.PointQuery
import com.plcoding.SignalsByFilterQuery
import com.plcoding.graphqlmobileapp.domain.AggregatedInfo
import com.plcoding.graphqlmobileapp.domain.AggregationClient
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.graphqlmobileapp.domain.DetailedSignalData
import com.plcoding.graphqlmobileapp.domain.PointClient
import com.plcoding.graphqlmobileapp.domain.PointSpecification
import com.plcoding.graphqlmobileapp.domain.SignalClient
import com.plcoding.graphqlmobileapp.domain.SimplePoint

class ApolloPointClient(
    private val apolloClient: ApolloClient
) : PointClient, SignalClient, AggregationClient
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

    private suspend fun getRequiredLastParameterToCoverAllSignals(): Int {
        return getPointSpecification()?.maxBy {
            it.existingSignalTypes?.size ?: 1
        }?.existingSignalTypes?.size ?: 1
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

    override suspend fun getSignals(id: String): List<DetailedSignalData>? {
        val signals = apolloClient
            .query(SignalsByFilterQuery(id, "2023-10-08T20:00:00.0000", "2023-10-08T14:00:00.0000"))
            .execute()
            .data
            ?.signals
        return signals?.toDetailedSignalData()
    }

    override suspend fun getAggregatedInfo(signals: List<DetailedSignalData>?): AggregatedInfo {
        val signalsWithValue = signals?.filter { it.signalData.numericValue != null }
        val min = signalsWithValue?.minBy { it.signalData.numericValue!! }
        val max = signalsWithValue?.maxBy { it.signalData.numericValue!! }
        val avg = (signalsWithValue?.sumOf { it.signalData.numericValue!! }?: 0.0) / (signalsWithValue?.size?: 1)

        return AggregatedInfo(
            min = min?.signalData?.numericValue,
            timeOfMin = min?.signalData?.time,
            max = max?.signalData?.numericValue,
            timeOfMax = max?.signalData?.time,
            avg = avg
        )
    }
}