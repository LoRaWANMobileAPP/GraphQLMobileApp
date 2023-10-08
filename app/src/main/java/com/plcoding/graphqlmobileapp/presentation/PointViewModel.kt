package com.plcoding.graphqlmobileapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.graphqlmobileapp.domain.GetPointUseCase
import com.plcoding.graphqlmobileapp.domain.GetPointsUseCase
import com.plcoding.graphqlmobileapp.domain.SimplePoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PointViewModel @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase,
    private val getPointUseCase: GetPointUseCase
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

    fun selectPoint(id: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedPoint = getPointUseCase.execute(id)
                )
            }
        }
    }

    fun dismissPointDialog() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedPoint = null
                )
            }
        }
    }

    data class PointState(
        val point: SimplePoint? = null,
        val isLoading: Boolean = false,
        val selectedPoint: DetailedPoint? = null
    )
}