package com.siele.unitconverter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.siele.unitconverter.ui.navigation.Navigation
import com.siele.unitconverter.ui.theme.ComposerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposerTheme {
                Navigation()
            }

        }
    }
}

