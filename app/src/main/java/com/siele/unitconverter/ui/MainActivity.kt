package com.siele.unitconverter.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.siele.unitconverter.ui.navigation.Navigation
import com.siele.unitconverter.ui.theme.ComposerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var pressedTime:Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposerTheme {
                Navigation(navController = rememberNavController())
            }
        }
    }

    override fun onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
            finish()
        }else{
            Toast.makeText(baseContext, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        pressedTime  = System.currentTimeMillis()
    }
}


