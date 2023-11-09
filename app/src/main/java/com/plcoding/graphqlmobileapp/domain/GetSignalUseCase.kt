package com.plcoding.graphqlmobileapp.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.plcoding.SignalsByFilterQuery
import java.time.LocalDateTime

class GetSignalUseCase(
    private val signalClient: SignalClient
) {

    //TODO need cleanup, add time to filter
    val signalsMap = mutableMapOf<String, SignalCache>()
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun execute(id: String): List<DetailedSignalData>? {
        if (signalsMap[id] == null) {
            println("*****1*****")
            val lst = mutableListOf<DetailedSignalData>()
            fetchAndUpdate(id)?.let { lst.addAll(it) }
            signalsMap[id] = SignalCache(LocalDateTime.now(), lst)
        } else {
            println("*****2*****")
            println(id)
            val signalCache = signalsMap[id]
            if (signalCache!!.lastFetch.isBefore(LocalDateTime.now().minusMinutes(10))) {
                val lst = signalCache!!.signals
                fetchAndUpdate(id)?.let { lst.addAll(it) }
                signalsMap[id] = SignalCache(
                    LocalDateTime.now(),
                    signals = lst
                )
            }
        }
        return signalsMap[id]!!.signals
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun fetchAndUpdate(id: String): List<DetailedSignalData>? {
        println("SignalClienttest")
        println(signalClient.getSignals(id,null, null))
        return signalClient.getSignals(id, null, null)
    }
}

data class SignalCache(
    val lastFetch: LocalDateTime,
    val signals: MutableList<DetailedSignalData>
)