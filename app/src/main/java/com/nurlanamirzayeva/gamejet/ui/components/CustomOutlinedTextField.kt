package com.nurlanamirzayeva.gamejet.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.nurlanamirzayeva.gamejet.ui.theme.black

@Composable
fun CustomOutlinedTextField(

    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    shape: Shape = RoundedCornerShape(8.dp),
    singleLine: Boolean = true,
    labelText: String,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = black,
        unfocusedContainerColor = black,
        disabledContainerColor = Color.Gray,
        focusedTextColor = Color.White,
        unfocusedBorderColor = black,
        focusedBorderColor = Color.White,
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    OutlinedTextField(

        value = value,
        onValueChange = {
            onValueChange(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),

        shape = shape,
        colors = colors,
        placeholder = { if (!isFocused) Text(text = labelText, color = Color.Gray) },
        singleLine = singleLine,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation
    )

}