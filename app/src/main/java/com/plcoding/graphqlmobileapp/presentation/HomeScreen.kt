package com.plcoding.graphqlmobileapp.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.TabRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.graphqlmobileapp.R
import com.plcoding.graphqlmobileapp.domain.SimpleNode
import kotlinx.coroutines.launch


enum class LoRaWanPage(
    @StringRes val titleResId: Int,
    @DrawableRes val drawableResId: Int
) {
    MY_SENSORS(R.string.my_sensors_view, R.drawable.ic_wireless_sensor_symbol),
    INFO_VIEW(R.string.info_view, R.drawable.ic_info_symbol)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onPointClick: (SimpleNode) -> Unit = {},
    viewModel: PointViewModel = hiltViewModel(),
    //onSelectPoint: (id: String) -> Unit,
    //onDismissPointDialog: () -> Unit,
    //state: PointViewModel.PointState
) {
    val pagerState = rememberPagerState(pageCount = {
        2
    })
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeTopAppBar(
                //pagerState = pagerState,
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        HomePagerScreen(
            viewModel = viewModel,
            onPointClick = onPointClick,
            //onSelectPoint = onSelectPoint,
            //onDismissPointDialog = onDismissPointDialog,
            pagerState = pagerState,
            modifier = Modifier.padding(it)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePagerScreen(
    modifier: Modifier = Modifier,
    onPointClick: (SimpleNode) -> Unit,
    viewModel: PointViewModel = hiltViewModel(),
    //onSelectPoint: (id: String) -> Unit,
    //onDismissPointDialog: () -> Unit,
    pagerState: PagerState,

    pages: Array<LoRaWanPage> = LoRaWanPage.values()
) {
    // Use Modifier.nestedScroll + rememberNestedScrollInteropConnection() here so that this
    // composable participates in the nested scroll hierarchy so that HomeScreen can be used in
    // use cases like a collapsing toolbar
    Column(modifier) {
        val coroutineScope = rememberCoroutineScope()
        //val imageModifier = Modifier.background(MaterialTheme.colorScheme.background)

        // Tab row
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = MaterialTheme.colorScheme.background
        ){
            pages.forEachIndexed { index, page ->
                val title = stringResource(id = page.titleResId)
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(text = title) },
                    icon = {
                        Icon(
                            painter = painterResource(id = page.drawableResId),
                            contentDescription = title,
                            //tint = Color.Black,
                            //modifier = imageModifier
                        )
                    },
                    unselectedContentColor = MaterialTheme.colorScheme.secondary
                )
            }
        }

        // Pages
        HorizontalPager(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            state = pagerState,
            //pageCount = pages.size,
            //state = pagerState,
            verticalAlignment = Alignment.Top
        ) { index ->
            when (pages[index]) {
                LoRaWanPage.MY_SENSORS -> {
                    PointScreenTest(
                        Modifier.fillMaxSize(),
                        onPointClick = onPointClick,
                        viewModel = viewModel,
                        //onSelectPoint = onSelectPoint,
                        //onDismissPointDialog = onDismissPointDialog
                    )
                }
                        /*
                        // Put in the detailed sensor view here
                        onPlantClick = {
                            onPlantClick(it.plant)
                        })

                         */
                LoRaWanPage.INFO_VIEW -> {
                    InfoScreen(
                        //onPlantClick = onPlantClick,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }

}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(
    //pagerState: PagerState,
    scrollBehavior: TopAppBarScrollBehavior,
    //modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displaySmall
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

