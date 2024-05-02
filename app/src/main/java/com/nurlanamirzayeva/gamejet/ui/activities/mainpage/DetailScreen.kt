package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import coil.compose.AsyncImage
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.model.DetailsResponse
import com.nurlanamirzayeva.gamejet.model.GenresItem
import com.nurlanamirzayeva.gamejet.ui.components.TextSwitch
import com.nurlanamirzayeva.gamejet.ui.theme.black
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.ui.theme.grey
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel
import org.w3c.dom.Text
import kotlin.math.log


@Composable
fun DetailScreen(mainPageViewModel: MainPageViewModel,detail:DetailsResponse) {
    val detailItemState=mainPageViewModel.detailPageResponse.collectAsState()

    detailItemState.value?.let {
        val tabItems = remember { listOf("Actors", "Similar Movies") }
        var selectedIndex by remember {
            mutableIntStateOf(0)
        }

        val (hours, remainingMinutes) = convertToHours(detail.runtime!!)
        LaunchedEffect(key1 = Unit) {
            mainPageViewModel.getDetails()
        }
        Log.d("TAG", "DetailScreen:${detailItemState.value} ")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = dark_grey)
                .verticalScroll(rememberScrollState())
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .background(color = black)
                    .clip(shape = RoundedCornerShape(8.dp))
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = detail.title.toString(),
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(30.dp)
                            .background(color = Color.Yellow, shape = RoundedCornerShape(8.dp))
                            .align(Alignment.CenterVertically)
                    ) {

                        Text(
                            text = detail.voteAverage.toString(),
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.align(
                                Alignment.Center
                            )
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                detail.genres?.forEachIndexed { index, genre ->

                    when (index) {
                        0 -> {
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(36.dp)
                                    .background(color = black, shape = RoundedCornerShape(8.dp))
                                    .align(Alignment.CenterVertically)
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {

                                Text(
                                    text = genre?.name.toString(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.align(
                                        Alignment.Center
                                    )
                                )
                            }
                        }

                        1 -> {
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(36.dp)
                                    .background(color = black, shape = RoundedCornerShape(8.dp))
                                    .align(Alignment.CenterVertically)
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {

                                Text(
                                    text = genre?.name.toString(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.align(
                                        Alignment.Center
                                    )
                                )
                            }
                        }

                    }

                }

                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(36.dp)
                        .background(color = black, shape = RoundedCornerShape(8.dp))
                        .align(Alignment.CenterVertically)
                        .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
                ) {

                    Text(
                        text = "${hours}h ${remainingMinutes}m",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }
            }

            Text(
                "Movie Story:",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(start = 14.dp, top = 16.dp)
            )
            Text(
                text = detail.overview.toString(),
                color = Color.LightGray,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 14.dp, vertical = 10.dp)
                    .wrapContentHeight()
            )
            TextSwitch(
                selectedIndex = selectedIndex,
                items = tabItems,
                onSelection = { selectedIndex = it })

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(start = 14.dp, top = 14.dp)
            ) {
                items(5) {
                    DetailItem()
                }

            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
fun convertToHours(minutes:Int):Pair<Int,Int>{
    val hours= minutes/60
    val remainingMinutes = minutes%60
    return Pair(hours,remainingMinutes)
}




@Composable
fun DetailItem() {
AsyncImage(model = R.drawable.pp , contentDescription = "TabIndicatorImages", modifier = Modifier
    .height(130.dp)
    .width(80.dp)
    .clip(shape = RoundedCornerShape(8.dp)), contentScale = ContentScale.FillBounds)

}



