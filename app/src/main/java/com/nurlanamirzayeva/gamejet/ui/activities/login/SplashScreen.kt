package com.nurlanamirzayeva.gamejet.ui.activities.login


import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.auth.FirebaseAuth
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.ui.activities.mainpage.MainPageActivity
import javax.inject.Inject


@Composable
fun SplashScreen(navController: NavHostController,auth: FirebaseAuth) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.logo))
        val logoAnimationState = animateLottieCompositionAsState(composition = composition)
        LottieAnimation(
            composition = composition,
            progress = { logoAnimationState.progress },
            modifier = Modifier.size(250.dp)
        )
        if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
            if (auth.currentUser != null) {
                val intent = Intent(context, MainPageActivity::class.java)
                context.startActivity(intent)
                (context as? Activity)?.finish()
            } else {
                navController.navigate(Screens.SignIn)
            }
        }

    }

}

