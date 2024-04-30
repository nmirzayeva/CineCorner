package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nurlanamirzayeva.gamejet.ui.components.TextSwitch
import com.nurlanamirzayeva.gamejet.ui.theme.black
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.ui.theme.grey
import org.w3c.dom.Text

@Preview
@Composable
fun DetailScreen() {

    val tabItems=remember{ listOf("Actors","Similar Movies") }
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    Column(modifier= Modifier
        .fillMaxSize()
        .background(color = dark_grey)) {

        Box(modifier= Modifier
            .fillMaxWidth()
            .height(350.dp)
            .background(color = black)
            .clip(shape = RoundedCornerShape(8.dp))){

            Row(modifier= Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    "Dark",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier=Modifier.align(Alignment.CenterVertically)
                )
                Box(modifier= Modifier
                    .width(100.dp)
                    .height(30.dp)
                    .background(color = Color.Yellow, shape = RoundedCornerShape(8.dp))
                    .align(Alignment.CenterVertically)){

                    Text("IMDB 8.6",color=Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp,modifier=Modifier.align(
                        Alignment.Center))
                }
            }
            }

        Row(modifier= Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)){

            Box(modifier= Modifier
                .width(100.dp)
                .height(36.dp)
                .background(color = black, shape = RoundedCornerShape(8.dp))
                .align(Alignment.CenterVertically)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))){

                Text("Action",color=Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp,modifier=Modifier.align(
                    Alignment.Center))
            }
            Box(modifier= Modifier
                .width(100.dp)
                .height(36.dp)
                .background(color = black, shape = RoundedCornerShape(8.dp))
                .align(Alignment.CenterVertically)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))){

                Text("Comedy",color=Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp,modifier=Modifier.align(
                    Alignment.Center))
            }
            Box(modifier= Modifier
                .width(100.dp)
                .height(36.dp)
                .background(color = black, shape = RoundedCornerShape(8.dp))
                .align(Alignment.CenterVertically)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))){

                Text("1h 20m",color=Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp,modifier=Modifier.align(
                    Alignment.Center))
            }
        }

      Text("Movie Story:",fontSize= 20.sp, fontWeight = FontWeight.SemiBold,color= Color.White,modifier=Modifier.padding(start=14.dp,top=16.dp))
       Text("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",color= Color.LightGray, fontSize = 16.sp,modifier= Modifier
           .padding(horizontal = 14.dp, vertical = 10.dp)
           .wrapContentHeight())
        TextSwitch(selectedIndex =selectedIndex , items =tabItems , onSelection ={selectedIndex=it} )

    }
    
}
