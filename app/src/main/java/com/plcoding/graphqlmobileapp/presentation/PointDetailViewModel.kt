package com.plcoding.graphqlmobileapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.graphqlmobileapp.domain.AggregatedInfo
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.graphqlmobileapp.domain.DetailedSignalData
import com.plcoding.graphqlmobileapp.domain.GetAggregatedInfoUseCase
import com.plcoding.graphqlmobileapp.domain.GetPointUseCase
import com.plcoding.graphqlmobileapp.domain.GetSignalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PointDetailViewModel @Inject constructor(
    private val getPointUseCase: GetPointUseCase,
    private val getSignalUseCase: GetSignalUseCase,
    private val getAggregatedInfoUseCase: GetAggregatedInfoUseCase,

): ViewModel() {
    var sensorId: String? = ""

    private val _state = MutableStateFlow(PointDetailState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
        }
    }
fun selectPoint()
{
    println("sensorID is:  $sensorId")
    viewModelScope.launch {
        val sensorId = sensorId
        val nonNullableSensorId: String = sensorId ?: "2"
        _state.update {
            val signalList = getSignalUseCase.execute(nonNullableSensorId, null, null)

            it.copy(
                selectedPoint = getPointUseCase.execute(nonNullableSensorId),
                signalList = signalList,
                aggregatedInfo = getAggregatedInfoUseCase.execute(signalList),
                isLoading = false
            )

        }
    }
}
    data class PointDetailState(
        val isLoading: Boolean = false,
        val selectedPoint: DetailedPoint? = null,
        val signalList: List<DetailedSignalData>? = null,
        val aggregatedInfo: AggregatedInfo? = null
    )
}