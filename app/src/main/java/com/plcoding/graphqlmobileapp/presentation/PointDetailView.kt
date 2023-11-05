package com.plcoding.graphqlmobileapp.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.apollographql.apollo3.api.BooleanExpression

import androidx.compose.foundation.Image

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp

import com.plcoding.graphqlmobileapp.R
import com.plcoding.graphqlmobileapp.domain.AggregatedInfo
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.graphqlmobileapp.domain.DetailedSignalData

data class Sensor(
    val id: String?,
    val name: String,
    val description: String
)
@Composable
fun PointDetailScreen(
    onBackClick: () -> Unit,
    sensorId: String?,
    //viewModel: PointViewModel = hiltViewModel(),
    modifier: Modifier = Modifier

){
    val sensorData = when(sensorId){
        "6505c5094543cbb034793ef2"->{
            Sensor("6505c5094543cbb034793ef2", stringResource(id = R.string.Comfort_sensor), stringResource(
                id = R.string.Comfort_sens_info
            ))
        }
        "6505c702f490ba356202544f"->{
            Sensor("6505c702f490ba356202544f", stringResource(id = R.string.Door_sensor), stringResource(
                id = R.string.Door_sens_info
            ))
        }
        "65102bf52a6d1ce45e3792e3"->{
            Sensor("65102bf52a6d1ce45e3792e3", stringResource(id = R.string.Temperature_sensor),
                stringResource(id = R.string.temp_sens_info))
        }

        else -> {
            Sensor(sensorId,"Unknown Sensor","unknown description")
        }
    }


    LazyColumn(){
        item {
            Surface() {
                Text(
                    text = sensorData.name,
                    style = MaterialTheme.typography.displaySmall)
            }
        }
        item {
            Image(
                painter = painterResource(id = R.drawable.ic_wireless_sensor_symbol), // Replace with your image resource
                contentDescription = "My Image",
                modifier = Modifier.fillMaxWidth()
            )
            Text(

                text = "image",
                style = MaterialTheme.typography.displaySmall)
        }
        item{
            Surface() {
                Text(
                text = sensorData.description,
                style = MaterialTheme.typography.labelSmall)
            }
        }
        item{
            Surface() {
                Text(
                    text = "The sensor iD is: ${sensorData.id}",
                    style = MaterialTheme.typography.labelSmall)
            }
        }
    }

    //Text(text = "Sensor ID: $sensorId")





}




//@Preview(showSystemUi = true)
@Composable
fun PointDetailViewPreview(
    onBackClick: () -> Unit = {},
    sensorId: String?

){
    PointDetailScreen(onBackClick = onBackClick, sensorId=sensorId)
}
