package com.plcoding.graphqlmobileapp.domain

class GetSignalUseCase(
    private val signalClient: SignalClient
) {
    suspend fun execute(): List<SimpleSignal>? {
        return signalClient.getSignals()
    }

}