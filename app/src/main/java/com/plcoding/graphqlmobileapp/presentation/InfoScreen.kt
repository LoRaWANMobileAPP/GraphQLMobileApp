package com.plcoding.graphqlmobileapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.plcoding.graphqlmobileapp.R


@Composable
fun InfoScreen(modifier: Modifier = Modifier){
    val marginNormal = dimensionResource(id = R.dimen.margin_normal)

    LazyColumn (modifier.fillMaxWidth()) {
        item {
            // System image
            SystemImage(modifier = modifier, marginNormal = marginNormal)
        }
        item{
            Text(
                text = stringResource(R.string.system_description_heading),
                modifier
                    .padding(vertical = marginNormal),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
        item{
            Text(
                text = stringResource(R.string.system_description_text),
                modifier
                    .padding(vertical = marginNormal, horizontal = marginNormal),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Composable
private fun SystemImage(
    modifier: Modifier = Modifier,
    marginNormal: Dp
    ){
    Image(
        painter = painterResource(id = R.drawable.img_system_overview),
        contentDescription = stringResource(id = R.string.img_system_overview),
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = marginNormal),
        alignment = Alignment.TopCenter
    )
}

@Preview(showSystemUi = true)
@Composable
private fun SystemImagePreview(){
    InfoScreen()
}

