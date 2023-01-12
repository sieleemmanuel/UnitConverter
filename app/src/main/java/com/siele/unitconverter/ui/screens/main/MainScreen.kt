package com.siele.unitconverter.ui.screens.main

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

@Composable
fun ScafoldCompose(navController: NavController) {
    Scaffold(
        topBar = { TopBarCompose() },
    ) { paddingValues ->
        ContentCompose(navController, paddingValues)
    }
}

@Composable
fun TopBarCompose() {
    TopAppBar(
        title = {
            Text(
                text = "Unit & Currency Converter",
                fontSize = 20.sp,
                maxLines = 1,
                fontWeight = FontWeight.ExtraBold,
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
fun ContentCompose(
    navController: NavController,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyGridState()
    val unitTypes = remember { Constants.unitsOfMeasure }

    Box(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(100.dp),
                state = listState,
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    count = unitTypes.size,
                    key = { unitTypes[it].unit }
                ) { position ->
                    UnitItem(unitMeasure = unitTypes[position]) { selectedUnit ->
                        navController.navigate(Screen.ConvertScreen.route + "/${selectedUnit.unit}")
                    }
                }
            }
        }
    }
}

@Composable
fun UnitItem(
    unitMeasure: UnitOfMeasure,
    modifier: Modifier = Modifier,
    selectedMeasure: (UnitOfMeasure) -> Unit
) {

    val measure = remember { unitMeasure }
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 10.dp,
        modifier = modifier
            .fillMaxSize()
            .height(120.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(16.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { selectedMeasure(measure) },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .padding(top = 16.dp),
                painter = painterResource(measure.symbol),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
            Text(
                modifier = Modifier
                    .padding(top = 16.dp),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 17.sp,
                text = measure.unit,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body2,
            )

        }
    }
}
/*

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    ComposerTheme {
         MainScreen(navController = rememberNavController())
        */
/*UnitItem(unitMeasure = Constants.unitsOfMeasure.last())
        {

        }*//*

    }
}*/


