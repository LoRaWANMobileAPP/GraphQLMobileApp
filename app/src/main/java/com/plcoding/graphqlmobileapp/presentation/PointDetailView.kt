package com.plcoding.graphqlmobileapp.presentation

import androidx.activity.compose.ReportDrawn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.graphqlmobileapp.R
import com.plcoding.graphqlmobileapp.domain.AggregatedInfo
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.graphqlmobileapp.domain.DetailedSignalData
import com.plcoding.graphqlmobileapp.domain.SignalData
import com.plcoding.graphqlmobileapp.domain.UnitType
import com.plcoding.graphqlmobileapp.utils.Helper

data class Sensor(
    val id: String?,
    val name: String,
    val description: String,
    val imageurl: Int


)
data class SignalData(
    val rawValue: Double?,
    val numericValue: Double?,

)
/*
@Composable
fun PointDetailTest(
    modifier: Modifier = Modifier,
    viewModel: PointDetailViewModel = hiltViewModel(),

) {
    val state by viewModel.state.collectAsState()

    PointScreenTest(
        state = state,
        modifier = modifier,
        // onPointClick = onPointClick
        //onSelectPoint = onSelectPoint
        //onDismissPointDialog = onDismissPointDialog
    )

}

 */
@Composable
fun PointDetailScreen(
    onBackClick: () -> Unit,
    sensorId: String?,
    viewModel: PointDetailViewModel = hiltViewModel(),

    modifier: Modifier = Modifier,


) {
    val marginNormal = dimensionResource(id = R.dimen.margin_normal)
    val state by viewModel.state.collectAsState()

    viewModel.sensorId = sensorId
    viewModel.selectPoint()
    //viewModel.setSensorId(sensorId)
    //sensorId?.let { viewModel.setSensorId(it) }

    val sensorData = when (sensorId) {
        "6505c5094543cbb034793ef2" -> {
            Sensor(
                "6505c5094543cbb034793ef2",
                stringResource(id = R.string.Comfort_sensor),
                stringResource(
                    id = R.string.Comfort_sens_info
                ),
                R.drawable.comfortsensor
            )
        }

        "6505c702f490ba356202544f" -> {
            Sensor(
                "6505c702f490ba356202544f",
                stringResource(id = R.string.Door_sensor),
                stringResource(
                    id = R.string.Door_sens_info
                ),
                R.drawable.doorsensor
            )
        }

        "65102bf52a6d1ce45e3792e3" -> {
            Sensor(
                "65102bf52a6d1ce45e3792e3", stringResource(id = R.string.Temperature_sensor),
                stringResource(id = R.string.temp_sens_info), R.drawable.temperaturesensor
            )
        }

        else -> {
            Sensor(sensorId, "Unknown Sensor", "unknown description", 2)
        }
    }

    if (state.isLoading) {
        Box(modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    } else if (state.point?.edges.isNullOrEmpty()) {
        EmptyScreen(modifier = modifier)
    } else {
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(top = 40.dp)
        ) {

            item {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface {
                        Text(
                            text = sensorData.name,
                            style = MaterialTheme.typography.displaySmall,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Surface {
                        Image(
                            painter = painterResource(sensorData.imageurl), // Replace with your image resource
                            contentDescription = "My Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(marginNormal)
                        )
                    }
                }
            }
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Surface {
                        Text(
                            text = sensorData.description,
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .padding(horizontal = marginNormal)
                        )
                    }
                }
            }
            item {
                Surface {
                    Text(
                        text = "The sensor ID is: ${sensorData.id}",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier
                            .padding(horizontal = marginNormal)
                    )
                }
            }
            item {
                Surface {
                    Text(
                        text = "Below are some sensor data: ",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .padding(marginNormal)
                    )
                }
            }
            item {
                Surface {
                    Column(modifier = modifier.fillMaxWidth()) {
                        listData(
                            point = state.selectedPoint ?: DetailedPoint(
                                "",
                                "",
                                "",
                                null,
                                "",
                                UnitType.UNKNOWN,
                                null,
                                SignalData(0.0, "", null),
                            ),
                            signalList = state.signalList ?: emptyList(),
                            aggregatedInfo = state.aggregatedInfo,
                            marginNormal = marginNormal
                        )
                    }
                }

            }
            /*
        item {

            //Reserved for input of date.
        }

         */
        }
    }
}




@Composable
fun listData(
    point: DetailedPoint,
    signalList: List<DetailedSignalData>,
    aggregatedInfo: AggregatedInfo?,
    modifier: Modifier = Modifier,
    marginNormal: Dp
) {
    Column (
        modifier
            .fillMaxWidth()
            .padding(horizontal = marginNormal)
    ){
        Text(
            text = "Last sampling: " + point.timestamp,
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sampled value: " + point.signalData.rawValue,
            style = MaterialTheme.typography.labelMedium
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(marginNormal)
    ) {
        if (aggregatedInfo != null) {
            AggregatedItem2(
                aggregatedInfo = aggregatedInfo,
                modifier = Modifier
            )
        }
    }
    /*
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(signalList) { signalData ->
                SignalItem2(
                    detailedSignalData = signalData,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)

                )
            }

             */
}


@Composable
fun SignalItem2(
    detailedSignalData: DetailedSignalData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = detailedSignalData.signalData.numericValue.toString(),
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        /*  Text(
         text = detailedSignalData.signalData.rawValue,
          fontSize = 15.sp
      )

     */
    }
}

@Composable
 fun AggregatedItem2(
    aggregatedInfo: AggregatedInfo,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //println("Aggregated info ")
        //println(aggregatedInfo.max.toString())
        Text(
            text ="Max Value: " + aggregatedInfo.max+"  ",
            style = MaterialTheme.typography.labelSmall

        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Time Of Max: " + aggregatedInfo.timeOfMax?.let { Helper.eliminateMilisecond(it) },
            style = MaterialTheme.typography.labelSmall
        )
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text ="Min Value: " + aggregatedInfo.min+"  ",
            style = MaterialTheme.typography.labelSmall

        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Time Of Min: " + aggregatedInfo.timeOfMin?.let { Helper.eliminateMilisecond(it) },
            style = MaterialTheme.typography.labelSmall
        )
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            //text = "Average value: " + aggregatedInfo.avg.toString().format("%.1f"),
            text = "Average value: " + String.format("%.1f", aggregatedInfo.avg),
            style = MaterialTheme.typography.labelSmall
        )
    }
}
@Composable
private fun EmptyScreen(modifier: Modifier = Modifier){
    ReportDrawn()

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.empty_screen),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

