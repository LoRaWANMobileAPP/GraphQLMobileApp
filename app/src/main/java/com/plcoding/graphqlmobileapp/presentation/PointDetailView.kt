package com.plcoding.graphqlmobileapp.presentation

import android.graphics.drawable.Drawable
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
import androidx.compose.foundation.layout.absoluteOffset

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.plcoding.graphqlmobileapp.R
import com.plcoding.graphqlmobileapp.domain.AggregatedInfo
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.graphqlmobileapp.domain.DetailedSignalData

data class Sensor(
    val id: String?,
    val name: String,
    val description: String,
    val imageurl: Int

)
@Composable
fun PointDetailScreen(
    onBackClick: () -> Unit,
    sensorId: String?,
    //viewModel: PointViewModel = hiltViewModel(),
    //modifier: Modifier = Modifier

){
    val sensorData = when(sensorId){
        "6505c5094543cbb034793ef2"->{
            Sensor("6505c5094543cbb034793ef2", stringResource(id = R.string.Comfort_sensor), stringResource(
                id = R.string.Comfort_sens_info), R.drawable.comfortsensor)
        }
        "6505c702f490ba356202544f"->{
            Sensor("6505c702f490ba356202544f", stringResource(id = R.string.Door_sensor), stringResource(
                id = R.string.Door_sens_info
            ), R.drawable.doorsensor)
        }
        "65102bf52a6d1ce45e3792e3"->{
            Sensor("65102bf52a6d1ce45e3792e3", stringResource(id = R.string.Temperature_sensor),
                stringResource(id = R.string.temp_sens_info), R.drawable.temperaturesensor)
        }

        else -> {
            Sensor(sensorId,"Unknown Sensor","unknown description", 2)
        }
    }

Box(
   // modifier = Modifier.fillMaxSize().fillMaxWidth(0.9f),
    //contentAlignment = Alignment.Center
    modifier = Modifier
        .padding(top=40.dp)
){
    LazyColumn(

            //.fillMaxSize().fillMaxWidth(0.9F)




    ){

        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally

            ){
                Surface(

                ) {

                    Text(
                        text = sensorData.name,
                        style = MaterialTheme.typography.displaySmall,

                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)

                    )
                }
            }

        }
        item {
            Image(
                painter = painterResource(sensorData.imageurl), // Replace with your image resource
                contentDescription = "My Image",
                modifier = Modifier.fillMaxWidth()
            )

        }
        item{
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,





            ) {
                Surface() {
                    Text(
                        text = sensorData.description,
                        style = MaterialTheme.typography.labelSmall,
                        textAlign= TextAlign.Left,
                        modifier = Modifier.padding(horizontal= 10.dp)
                        //modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }

        }
        item{
            Surface() {
                Text(
                    text = "The sensor iD is: ${sensorData.id}",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal= 10.dp)
                )
            }
        }
        item{
            Surface() {
                Text(
                    text = "Below are some sensor data: ",
                    style=MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(horizontal=10.dp)
                        .padding(top=10.dp)

                )
            }
        }
        item{
            //Reserved for input of date.
        }
    }

    //Text(text = "Sensor ID: $sensorId")





}
}




//@Preview(showSystemUi = true)
@Composable
fun PointDetailViewPreview(
    onBackClick: () -> Unit = {},
    sensorId: String?

){
    PointDetailScreen(onBackClick = onBackClick, sensorId=sensorId)
}

@Preview
@Composable
fun PointDetailPreview(

){
    LazyColumn(
modifier = Modifier
    ){
        item{
            Text(
                text = "test",
                textAlign = TextAlign.Left
            )

        }

        item{
            Surface(){
            Text(text = "test")
        }
        }
        item{
            Text(text = "test")
        }

    }
}