package com.plcoding.graphqlmobileapp.data

import com.plcoding.GetAggregatedInfoByFilterQuery
import com.plcoding.graphqlmobileapp.domain.AggregatedInfo

fun GetAggregatedInfoByFilterQuery.SignalsAggregation.toAggregatedInfo(): AggregatedInfo {
    return AggregatedInfo(
        min = this.min,
        max = this.max,
        avg = this.avg,
        //TODO values are wrong, unable to fetch data and fill fields correctly
        timeOfMin = this.time,
        timeOfMax = this.time
    )
}