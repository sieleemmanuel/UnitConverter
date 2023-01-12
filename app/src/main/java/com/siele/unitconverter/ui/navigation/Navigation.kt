package com.siele.unitconverter.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.siele.unitconverter.ui.screens.convert.ConvertScreen
import com.siele.unitconverter.ui.screens.main.MainScreen
import com.siele.unitconverter.util.Screen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.MainScreen.route){

        composable(route = Screen.MainScreen.route){
            MainScreen(navController)
        }

        composable(
            route = Screen.ConvertScreen.route + "/{unit_measure}",
            arguments = listOf(
                navArgument("unit_measure"){
                    type = NavType.StringType
                }
            )
        ){entry->
            ConvertScreen(
                navController = navController,
                unitMeasure = entry.arguments?.getString("unit_measure")!!,
            )
        }

    }
}