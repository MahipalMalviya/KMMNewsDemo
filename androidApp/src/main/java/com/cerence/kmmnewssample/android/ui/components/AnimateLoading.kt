package com.cerence.kmmnewssample.android.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.cerence.kmmnewssample.android.R


@Composable
fun Loader(isPlaying: Boolean, speed: Float) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Url("https://assets7.lottiefiles.com/packages/lf20_88jP8mNam2.json"))
    val progress by animateLottieCompositionAsState(composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = false
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
    )
}
