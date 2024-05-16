package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import android.widget.RadioGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.leanback.widget.Row
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.utils.ThemeMode
import com.nurlanamirzayeva.gamejet.viewmodel.SettingsViewModel
import org.w3c.dom.Text


@Composable
fun DarkModeScreen(settingsViewModel:SettingsViewModel) {
    val currentThemeModel= settingsViewModel.currentThemeMode.collectAsState()
    var selectedTheme by remember { mutableStateOf(currentThemeModel.value) }
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(horizontal = 14.dp, vertical = 40.dp)
        ) {

        val radioOptions = listOf(
            "Auto" to ThemeMode.AUTO,
            "Light" to ThemeMode.DAY,
            "Night" to ThemeMode.NIGHT
        )

        Text("Choose how your CineCorner experience looks for this device", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Normal)



            radioOptions.forEach { (label, themeMode) ->
                Row(modifier=Modifier.padding(top=10.dp)) {
                    RadioButton(
                        selected = selectedTheme == themeMode,
                        onClick = {
                            selectedTheme = themeMode
                            settingsViewModel.setTheme(themeMode)
                            settingsViewModel.refreshTheme()
                        },
                       colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.tertiary),


                    )
                    Text(text = label, modifier = Modifier.padding(start=2.dp,top=14.dp),color=Color.White, fontWeight = FontWeight.Normal, fontSize =18.sp )

                }
            }

            Text("If you choose Device settings, this app will use the mode that's already selected in this device's settings.",color=Color.White,modifier=Modifier.padding(top=16.dp))

    }

    }
