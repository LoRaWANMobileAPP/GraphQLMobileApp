package com.plcoding.graphqlmobileapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val getPointsUseCase: GetPointsUseCase
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

//    fun selectPoint(id: String) {
//        viewModelScope.launch {
//            _state.update {
//                //selectedPoint
//            }
//        }
//    }

    data class PointState(
        val point: SimplePoint? = null,
        val isLoading: Boolean = false
    )
}