package com.plcoding.graphqlmobileapp.data

import com.apollographql.apollo3.ApolloClient
import com.plcoding.PointsWithSpaceQuery
import com.plcoding.graphqlmobileapp.domain.PointClient
import com.plcoding.graphqlmobileapp.domain.SimplePoint
//import com.plcoding.type.PointFilterInput
//import com.plcoding.type.StringComparisonFilter

class ApolloPointClient(
    private val apolloClient: ApolloClient
) : PointClient {
    override suspend fun getPoints(): SimplePoint? {
        return try {
            var points = apolloClient
                .query(PointsWithSpaceQuery())
                .execute()
                .data
                ?.points

            points?.toSimplePoints()
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