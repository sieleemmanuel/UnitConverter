package com.siele.unitconverter.util

sealed class Screen(var route:String){
    object MainScreen: Screen("main_screen")
    object ConvertScreen: Screen("convert_screen")

}