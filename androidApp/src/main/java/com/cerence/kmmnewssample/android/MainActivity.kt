package com.cerence.kmmnewssample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cerence.kmmnewssample.android.ui.home.NavGraphs
import com.cerence.kmmnewssample.android.ui.theme.AppTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}

