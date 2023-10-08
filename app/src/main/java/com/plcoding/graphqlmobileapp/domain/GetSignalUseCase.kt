package com.plcoding.graphqlmobileapp.domain

class GetSignalUseCase(
    private val signalClient: SignalClient
) {
    suspend fun execute(id: String): List<DetailedSignalData>? {
        return signalClient.getSignals(id)
    }

}