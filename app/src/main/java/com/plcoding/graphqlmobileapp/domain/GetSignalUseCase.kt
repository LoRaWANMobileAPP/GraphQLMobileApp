package com.plcoding.graphqlmobileapp.domain

import android.os.Build
import androidx.annotation.RequiresApi
import java.sql.Timestamp
import java.util.Date

class GetSignalUseCase(
    private val signalClient: SignalClient
) {

//    private val dimensionFourTimeFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    //TODO need cleanup, add time to filter
    val signalsMap = mutableMapOf<String, SignalCache>()
//    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun execute(id: String, fromDate: Timestamp?, toDate: Timestamp?): List<DetailedSignalData>? {
        val currentDateTime = Date()
        val newFromDate = fromDate ?: Timestamp(currentDateTime.time - 86400000)
        val newToDate = toDate ?: Timestamp(currentDateTime.time)
        signalsMap[id] = fetchAndUpdate(signalsMap[id], id, newFromDate, newToDate)
        return signalsMap[id]!!.signals.distinctBy { it.signalData.time }
    }

//    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun fetchAndUpdate(existedSignalCache: SignalCache?, id: String, fromDate: Timestamp, toDate: Timestamp): SignalCache {
        val signalCache = existedSignalCache ?: SignalCache(signals = mutableListOf())
        val lst = signalCache.signals
        val signals = lst.filter {
            (it.signalData.time != null) &&
                    (it.signalData.time.compareTo(fromDate) == 1) &&
                    (it.signalData.time.compareTo(toDate) == -1)
        }
        val lastSampledTime = (signals.maxByOrNull { it.signalData.time!! }?.signalData?.time ?: fromDate)
        val firstSampledTime = (signals.minByOrNull { it.signalData.time!! }?.signalData?.time ?: toDate)
        val diffFromLast = toDate.time - lastSampledTime.time
        val diffFromFirst = firstSampledTime.time - fromDate.time
        if ( diffFromLast >= 600000) {
            val fetchedList = signalClient.getSignals(id, lastSampledTime, toDate)
            if (!fetchedList.isNullOrEmpty()) {
                lst.addAll(fetchedList)
                fetchAndUpdate(SignalCache(lst), id, lastSampledTime, toDate)
            }
        }
        if ( diffFromFirst >= 600000) {
            val fetchedList = signalClient.getSignals(id, fromDate, firstSampledTime)
            if (!fetchedList.isNullOrEmpty()) {
                lst.addAll(fetchedList)
                fetchAndUpdate(SignalCache(lst), id, fromDate, firstSampledTime)
            }
        }
        return SignalCache(lst)
    }
}

data class SignalCache(
    val signals: MutableList<DetailedSignalData>
)