package com.plcoding.graphqlmobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.graphqlmobileapp.presentation.PointViewModel
import com.plcoding.graphqlmobileapp.presentation.PointsScreen
import com.plcoding.graphqlmobileapp.ui.theme.GraphQlMobileAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraphQlMobileAppTheme {
                val viewModel = hiltViewModel<PointViewModel>()
                val state by viewModel.state.collectAsState()
                PointsScreen(state = state)
            }
        }
    }
}