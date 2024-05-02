package com.nurlanamirzayeva.gamejet.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nurlanamirzayeva.gamejet.ui.theme.grey


fun ContentDrawScope.drawWithLayer(block:ContentDrawScope.()->Unit){
    with(drawContext.canvas.nativeCanvas){
        val checkPoint=saveLayer(null,null)
        block()
        restoreToCount(checkPoint)
    }

}
@Composable
fun TextSwitch (
    modifier: Modifier =Modifier,
    selectedIndex:Int,
    items:List<String>,
    onSelection:(Int)->Unit
) {
BoxWithConstraints(
    modifier
        .padding(top=16.dp,start=14.dp,end=14.dp)
        .height(56.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(color = grey)
        .padding(8.dp)
) {

    if (items.isNotEmpty()){
        val maxWidth=this.maxWidth
        val tabWidth=maxWidth/items.size

        val indicatorOffset by animateDpAsState(
            targetValue = tabWidth*selectedIndex,
            animationSpec = tween(durationMillis =250, easing = FastOutSlowInEasing),
            label="indicator offset"
        )

      Row(modifier= Modifier
          .fillMaxWidth()
          .drawWithContent {
              val padding = 8.dp.toPx()
              drawRoundRect(
                  topLeft = Offset(x = indicatorOffset.toPx() + padding, padding),
                  size = Size(size.width / 2 - padding * 2, size.height - padding * 2),
                  color = Color.White,
                  cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
              )

              drawWithLayer {
                  drawContent()

                  // This is white top rounded rectangle
                  drawRoundRect(
                      topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                      size = Size(size.width / 2, size.height),
                      color = Color.Gray,
                      cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                      blendMode = BlendMode.SrcOut
                  )
              }

          }

      )
      {
          items.forEachIndexed { index, text ->
              Box(
                  modifier= Modifier
                      .width(tabWidth)
                      .fillMaxHeight()
                      .clickable(
                          interactionSource = remember {
                          MutableInteractionSource()
                      },
                          indication = null,
                          onClick ={
                          onSelection(index)
                      }
                      ),
                  contentAlignment = Alignment.Center
              ){
                  Text(
                      text = text,
                      fontSize = 18.sp,
                      fontWeight = FontWeight.Bold,
                      color = Color.White

                  )
              }


          }


      }

    }
}

}