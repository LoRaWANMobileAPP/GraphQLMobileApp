package com.plcoding.graphqlmobileapp.domain

class GetAggregatedInfoUseCase {
    suspend fun execute(signals: List<DetailedSignalData>?): AggregatedInfo? {
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


