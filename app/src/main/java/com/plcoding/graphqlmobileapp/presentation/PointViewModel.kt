package com.plcoding.graphqlmobileapp.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.graphqlmobileapp.domain.AggregatedInfo
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.graphqlmobileapp.domain.DetailedSignalData
import com.plcoding.graphqlmobileapp.domain.GetAggregatedInfoUseCase
import com.plcoding.graphqlmobileapp.domain.GetPointUseCase
import com.plcoding.graphqlmobileapp.domain.GetPointsUseCase
import com.plcoding.graphqlmobileapp.domain.GetSignalUseCase
import com.plcoding.graphqlmobileapp.domain.SignalData
import com.plcoding.graphqlmobileapp.domain.SimplePoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class PointViewModel @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase,
    private val getPointUseCase: GetPointUseCase,
    private val getSignalUseCase: GetSignalUseCase,
    private val getAggregatedInfoUseCase: GetAggregatedInfoUseCase
): ViewModel() {

    private val _state = MutableStateFlow(PointState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            _state.update {
                it.copy(
                    point = getPointsUseCase.execute(),
                    isLoading = false
                )
            }
        }
    }

    fun selectPoint(
        id: String) {
        viewModelScope.launch {
            _state.update {
                val signalList = getSignalUseCase.execute(id)
                it.copy(
                    selectedPoint = getPointUseCase.execute(id),
                    signalList = signalList,
                    aggregatedInfo = getAggregatedInfoUseCase.execute(signalList)
                )
            }
        }
    }

    fun dismissPointDialog() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedPoint = null,
                    signalList = emptyList()
                )
            }
        }
    }

    data class PointState(
        val point: SimplePoint? = null,
        val isLoading: Boolean = false,
        val selectedPoint: DetailedPoint? = null,
        val signalList: List<DetailedSignalData>? = null,
        val aggregatedInfo: AggregatedInfo? = null
    )
}