package com.siele.unitconverter.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.siele.unitconverter.data.model.UnitOfMeasure
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
        content = { ContentCompose(navController) },
    )
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
fun ContentCompose(navController: NavController) {
    val  listState = rememberLazyGridState()
    Column(
        modifier = Modifier
            .padding(start = 10.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(120.dp),
            state = listState,
            content = {
                items(Constants.unitsOfMeasure.size) { position ->
                    UnitItem(Constants.unitsOfMeasure[position]) { selectedUnit ->
                        navController.navigate(Screen.ConvertScreen.route + "/${selectedUnit.unit}")
                    }
                }
            }
        )
    }

}

@Composable
fun UnitItem(unitMeasure: UnitOfMeasure, selectedMeasure:(UnitOfMeasure)->Unit) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 10.dp,
        modifier = Modifier
            .wrapContentHeight()
            .padding(end = 10.dp, bottom = 10.dp, top = 5.dp)
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

