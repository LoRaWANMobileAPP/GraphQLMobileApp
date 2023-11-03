package com.plcoding.graphqlmobileapp.presentation

import androidx.activity.compose.ReportDrawn
import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.graphqlmobileapp.R
import com.plcoding.graphqlmobileapp.domain.AggregatedInfo
import com.plcoding.graphqlmobileapp.domain.DetailedPoint
import com.plcoding.graphqlmobileapp.domain.DetailedSignalData
import com.plcoding.graphqlmobileapp.domain.LastSignalData
import com.plcoding.graphqlmobileapp.domain.SimpleEdge
import com.plcoding.graphqlmobileapp.domain.SimpleNode
import com.plcoding.graphqlmobileapp.ui.theme.GraphQlMobileAppTheme

@Composable
fun PointScreenTest(
    modifier: Modifier = Modifier,
    viewModel: PointViewModel = hiltViewModel(),
    onPointClick: (SimpleNode) -> Unit,
    //onSelectPoint: (id: String) -> Unit
    //onDismissPointDialog: () -> Unit
){
    val state by viewModel.state.collectAsState()

    PointScreenTest(
        state = state,
        modifier = modifier,
        onPointClick = onPointClick
        //onSelectPoint = onSelectPoint
        //onDismissPointDialog = onDismissPointDialog
    )
}
// Replace PointScreen if this works properly - based on GardenScreen
@Composable
fun PointScreenTest(
    state: PointViewModel.PointState,
    modifier: Modifier = Modifier,
    onPointClick: (SimpleNode) -> Unit = {},
    //onSelectPoint: (id: String) -> Unit = {},
    //onDismissPointDialog: () -> Unit
    ){
    if (state.isLoading){
        Box(){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
    else if (state.point?.edges.isNullOrEmpty()) {
        EmptyScreen(modifier = modifier)
    } else {
        PointsList(state = state, onPointClick = onPointClick, modifier = modifier)
    }
}

// Define the Empty screen layout (ie. nothing loaded from the database)
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

// Create the PointList function based on the PointsScreen function and GardenList
@Composable
private fun PointsList(
    state: PointViewModel.PointState,
    onPointClick: (SimpleNode) -> Unit,
    //onSelectPoint: (id: String) -> Unit,
    //onDismissPointDialog: () -> Unit,
    modifier: Modifier = Modifier
){
    // Call reportFullyDrawn when the garden list has been rendered
    val gridState = rememberLazyGridState()
    ReportDrawnWhen { gridState.layoutInfo.totalItemsCount > 0 }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier,
        state = gridState,
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.card_side_margin),
            vertical = dimensionResource(id = R.dimen.margin_normal)
        )
    ) {
        items(
            items = state.point?.edges ?: emptyList(),
            key = {it.node.id}
        )
        {edge ->
            PointsListItem(
                edge = edge,
                state = state,
                onPointClick = onPointClick,
                //onSelectPoint = onSelectPoint,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PointsListItem(
    edge: SimpleEdge,
    state: PointViewModel.PointState,
    onPointClick: (SimpleNode) -> Unit
    //onSelectPoint: (id: String) -> Unit
){
    // Dimensions
    val cardSideMargin = dimensionResource(id = R.dimen.card_side_margin)
    val marginNormal = dimensionResource(id = R.dimen.margin_normal)

    ElevatedCard(
        onClick = { onPointClick(edge.node) },
        modifier = Modifier.padding(
            start = cardSideMargin,
            end = cardSideMargin,
            bottom = dimensionResource(id = R.dimen.card_bottom_margin)
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ){
        Column(Modifier.fillMaxWidth()) {
            // Sensor name
            Text(
                text = edge.node.name ?: "No Name",
                Modifier
                    .padding(vertical = marginNormal)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium,
            )
            // Sensor reading
            edge.node.lastSignals?.forEach { data ->
                DataItemTest(
                    dataItem = data
                )
            }
        }
    }
}

@Composable
fun PointsScreen(
    state: PointViewModel.PointState,
    onSelectPoint: (id: String) -> Unit,
    onDismissPointDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.point?.edges ?: emptyList()) { edge ->
                    EdgeItem(
                        edge = edge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectPoint(edge.node.id) }
                            .padding(16.dp)
                    )
                    edge.node.lastSignals?.forEach { data ->
                        DataItem(
                            dataItem = data
                        )
                    }
                }
            }

            if (state.selectedPoint != null) {
                PointDialog(
                    point = state.selectedPoint,
                    signalList = state.signalList!!,
                    aggregatedInfo = state.aggregatedInfo,
                    onDismiss = onDismissPointDialog,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White)
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun PointDialog(
    point: DetailedPoint,
    signalList: List<DetailedSignalData>,
    aggregatedInfo: AggregatedInfo?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {


        Column(
            modifier = modifier
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = point.name ?: "No Name",
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Last sampling: " + point.timestamp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Sampled value: " + point.signalData.rawValue)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (aggregatedInfo != null) {
                    items(listOf(aggregatedInfo)){
                        AggregatedItem(
                            aggregatedInfo = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(signalList ?: emptyList()) { signalData ->
                    SignalItem(
                        detailedSignalData = signalData,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }

    }
}

// EdgeItem -> GardenListItem
@Composable
private fun EdgeItem(
    edge: SimpleEdge,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = edge.node.name ?: "No Name",
            fontSize = 10.sp
        )
    }
}

@Composable
private fun DataItemTest(
    dataItem: LastSignalData,
    modifier: Modifier = Modifier
) {
    val marginNormal = dimensionResource(id = R.dimen.margin_normal)

        Text(
            text = dataItem.type + ":" ?: "No Value",
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = dataItem.rawValue + " " + dataItem.unit ?: "No Value",
            Modifier.padding(bottom = marginNormal),
            style = MaterialTheme.typography.labelSmall
        )
}

@Composable
private fun DataItem(
    dataItem: LastSignalData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = dataItem.rawValue,
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = dataItem.unit,
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = dataItem.type,
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun SignalItem(
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
private fun AggregatedItem(
    aggregatedInfo: AggregatedInfo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = aggregatedInfo.max.toString(),
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = aggregatedInfo.timeOfMax.toString(),
            fontSize = 15.sp
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignalItemPreview(){
    GraphQlMobileAppTheme {
        val viewModel = hiltViewModel<PointViewModel>()
        val state by viewModel.state.collectAsState()
        PointsScreen(
            state = state,
            onSelectPoint = viewModel::selectPoint,
            onDismissPointDialog = viewModel::dismissPointDialog
        )
    }
    //SignalItem(detailedSignalData = DetailedSignalData(UnitType.DEGREES, SignalData(10.3, "10.4", time = 2023-10-27)))
}


