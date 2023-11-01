package com.plcoding.graphqlmobileapp.compose

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ShareCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.graphqlmobileapp.domain.AggregatedInfo
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.graphqlmobileapp.domain.DetailedSignalData
import com.plcoding.graphqlmobileapp.domain.SignalData
import com.plcoding.graphqlmobileapp.domain.SimpleEdge
import com.plcoding.graphqlmobileapp.domain.SimpleNode
import com.plcoding.graphqlmobileapp.domain.UnitType
import com.plcoding.graphqlmobileapp.presentation.HomeScreen
import com.plcoding.graphqlmobileapp.ui.theme.GraphQlMobileAppTheme
import com.plcoding.graphqlmobileapp.R
import com.plcoding.graphqlmobileapp.presentation.PointDetailScreen
import com.plcoding.graphqlmobileapp.presentation.PointViewModel

@Composable
fun LoRaWanApp() {
    val navController = rememberNavController()
    LoRaWanNavHost(
        navController = navController
    )
}

@Composable
fun LoRaWanNavHost(
    navController: NavHostController
) {
    val activity = (LocalContext.current as Activity)
    val viewModel = hiltViewModel<PointViewModel>()
    //val state by viewModel.state.collectAsState()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                //state = state,
                onPointClick = {
                    navController.navigate("sensorDetail/${it.id}")
                    viewModel::selectPoint
                }
            )
        }
        composable(
            "sensorDetail/{Id}",
            arguments = listOf(navArgument("Id") {
                type = NavType.StringType
            })
        ) {
            PointDetailScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}