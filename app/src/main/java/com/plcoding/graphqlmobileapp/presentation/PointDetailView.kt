package com.plcoding.graphqlmobileapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.plcoding.graphqlmobileapp.R
import com.plcoding.graphqlmobileapp.domain.AggregatedInfo
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.graphqlmobileapp.domain.DetailedSignalData

import com.plcoding.graphqlmobileapp.domain.SignalData
import com.plcoding.graphqlmobileapp.domain.UnitType

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
            ){
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
                    text = "The sensor iD is: ${sensorData.id}",
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
        if (!state.signalList.isNullOrEmpty()) {
            item {
                Surface {
                    Column(modifier = modifier.fillMaxWidth()
                    ) {
                        LineChartExample(state.signalList!!)
                    }
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
            .padding(marginNormal)
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
            text ="Max Value: " + aggregatedInfo.max.toString()+"  ",
            style = MaterialTheme.typography.labelSmall

        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Time Of Max: " + aggregatedInfo.timeOfMax.toString(),
            style = MaterialTheme.typography.labelSmall
        )
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text ="Min Value: " + aggregatedInfo.min.toString()+"  ",
            style = MaterialTheme.typography.labelSmall

        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Time Of Min: " + aggregatedInfo.timeOfMin.toString(),
            style = MaterialTheme.typography.labelSmall
        )
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Average value: " + aggregatedInfo.avg.toString(),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun LineChartExample(signalList: List<DetailedSignalData>) {
    val entries = signalList.mapIndexed { index, detailedSignalData ->
        Entry(index.toFloat(), (detailedSignalData.signalData.numericValue ?: 0).toFloat())
    }

    val dataSet = LineDataSet(entries, "Sampled data")
    dataSet.colors = ColorTemplate.VORDIPLOM_COLORS.toList()

    val lineData = LineData(dataSet)

    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                data = lineData
                description.text = "My Line Chart"
            }
        },
        modifier = Modifier.height(200.dp)
            .fillMaxWidth()
    )
}

