package com.plcoding.graphqlmobileapp.compose

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ShareCompat
import androidx.fragment.app.FragmentManager.BackStackEntry
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.graphqlmobileapp.presentation.HomeScreen
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
    //val activity = (LocalContext.current as Activity)
    val viewModel = hiltViewModel<PointViewModel>()
    //val state by viewModel.state.collectAsState()
    val onPointSelect = viewModel::selectPoint
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                //state = state,
                onPointClick = {
                    //only for testing. gathering LoRaWAN sensor ID.
                    //println("Sensor ID: ${it.id}")
                    navController.navigate("sensorDetail/${it.id}")
                    onPointSelect(it.id)
                }
            )
        }
        composable(
            "sensorDetail/{Id}",
            arguments = listOf(navArgument("Id") {
                type = NavType.StringType
            })
        ) {
            BackStackEntry ->
            //accessing sensorID from backstackentry
            val sensorId =BackStackEntry.arguments?.getString("Id")
            PointDetailScreen(
                onBackClick = { navController.navigateUp() },
                sensorId = sensorId //passing the sensorID to PointDetailView.
            )
        }
    }
}