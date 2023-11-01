package com.plcoding.graphqlmobileapp.compose

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ShareCompat
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
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                /*
                onPlantClick = {
                    navController.navigate("plantDetail/${it.plantId}")
                }

                 */
            )
        }
        composable(
            "plantDetail/{plantId}",
            arguments = listOf(navArgument("plantId") {
                type = NavType.StringType
            })
        ) {
            PlantDetailsScreen(
                onBackClick = { navController.navigateUp() },
                onShareClick = {
                    createShareIntent(activity, it)
                },
                onGalleryClick = {
                    navController.navigate("gallery/${it.name}")
                }
            )
        }
        composable(
            "gallery/{plantName}",
            arguments = listOf(navArgument("plantName") {
                type = NavType.StringType
            })
        ) {
            GalleryScreen(
                onPhotoClick = {
                    val uri = Uri.parse(it.user.attributionUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    activity.startActivity(intent)
                },
                onUpClick = {
                    navController.navigateUp()
                })
        }
    }
}