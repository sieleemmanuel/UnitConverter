package com.siele.unitconverter.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.siele.unitconverter.data.model.UnitOfMeasure
import com.siele.unitconverter.ui.theme.ComposerTheme
import com.siele.unitconverter.util.Constants
import com.siele.unitconverter.util.Screen

@Composable
fun MainScreen(navController: NavController) {
    Surface {
        ScafoldCompose(navController)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")

@Composable
fun ScafoldCompose(navController: NavController) {
    Scaffold(
        topBar = { TopBarCompose() },
    ){ paddingValues ->
        ContentCompose(navController, paddingValues)
    }
}

@Composable
fun TopBarCompose() {
    TopAppBar(
        title = {
            Text(
                text = "Unit Converter",
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Home, "Menu")
            }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        contentColor = MaterialTheme.colors.onPrimary
    )
}

@Composable
fun ContentCompose(navController: NavController,
                   paddingValues: PaddingValues,
                   modifier: Modifier = Modifier) {
    val  listState = rememberLazyGridState()
    Box(modifier = modifier
        .padding(paddingValues)
        .fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                state = listState,
                contentPadding = PaddingValues( 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
                items(Constants.unitsOfMeasure.size) { position ->
                    UnitItem(unitMeasure = Constants.unitsOfMeasure[position]) { selectedUnit ->
                        navController.navigate(Screen.ConvertScreen.route + "/${selectedUnit.unit}")
                    }
                }
            }
        }
    }
}

@Composable
fun UnitItem(unitMeasure: UnitOfMeasure, modifier: Modifier = Modifier, selectedMeasure:(UnitOfMeasure)->Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = 10.dp,
        modifier = modifier
            .wrapContentHeight()
            .fillMaxSize()
            .clickable { selectedMeasure(unitMeasure) },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .padding(15.dp),
                painter = painterResource(unitMeasure.symbol),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Fit
            )

            Text(
                modifier = Modifier
                    .padding(vertical = 10.dp),
                fontWeight = FontWeight.Bold,
                text = unitMeasure.unit,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body2,
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    ComposerTheme {
        MainScreen(navController = rememberNavController())
    }
}


