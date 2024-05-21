package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.network.repositories.MainPageRepository
import com.nurlanamirzayeva.gamejet.ui.theme.black
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel
import com.nurlanamirzayeva.gamejet.viewmodel.SettingsViewModel
import org.w3c.dom.Text


@Composable
fun FavoriteScreen(mainPageViewModel: MainPageViewModel) {

    val filmDetailsState=mainPageViewModel.filmDetails.collectAsState()

    LaunchedEffect(mainPageViewModel.movieId.intValue){

        mainPageViewModel.getFilmDetails()

    }


    Column(modifier= Modifier
        .fillMaxSize()
        .background(color = dark_grey)
        .padding(start = 10.dp, end = 10.dp, top = 16.dp)) {

        Box(modifier= Modifier
            .background(color = black, shape = RoundedCornerShape(8.dp))
            .height(170.dp)
            .fillMaxWidth()){

        Image(painter = painterResource(id = R.drawable.pp), contentDescription =null,modifier= Modifier
            .padding(all = 8.dp)
            .width(110.dp)
            .fillMaxHeight()
            .clip(shape = RoundedCornerShape(10.dp)), contentScale = ContentScale.FillBounds)

            Text(text= "Movie Name",color= Color.White, fontSize = 20.sp, fontWeight = FontWeight.SemiBold,modifier= Modifier
                .align(
                    Alignment.TopCenter
                )
                .padding(start = 56.dp, top = 14.dp)
                .width(170.dp))

            Icon(imageVector = Icons.Rounded.Delete, contentDescription =null,modifier= Modifier
                .align(
                    Alignment.TopEnd
                )
                .size(44.dp)
                .padding(top = 10.dp, end = 10.dp),tint= Color.White)

            Box(
                modifier = Modifier
                    .padding(end = 10.dp, bottom = 10.dp)
                    .width(100.dp)
                    .height(30.dp)
                    .background(color = Color.Yellow, shape = RoundedCornerShape(8.dp))
                    .align(Alignment.BottomEnd)


            ) {

                Text(
                    text = "IMDB: 7.6",
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
}






