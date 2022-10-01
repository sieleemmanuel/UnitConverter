package com.siele.unitconverter.ui

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.siele.unitconverter.R
import com.siele.unitconverter.ui.theme.ComposerTheme
import com.siele.unitconverter.util.Constants
import java.util.*

@Composable
fun ConvertScreen(navController: NavController, unitMeasure: String) {
    Surface {
        ConvertScreenScafold(
            navController = navController,
            unitMeasure = unitMeasure
        )
    }

}

@Composable
fun ConvertScreenScafold(navController: NavController, unitMeasure: String) {
    Scaffold(
        topBar = { ConvertTopBar(unitMeasure, navController = navController) },
        content = { ConvertScreenContent(unitMeasure) }
    )
}

@Composable
fun ConvertTopBar(title: String, navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "$title Conversion",
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Default.ArrowBack, "Navigate up")
            }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        contentColor = MaterialTheme.colors.onPrimary
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ConvertScreenContent(unitMeasure: String) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        val context = LocalContext.current
        val options = when (unitMeasure) {
            "Temperature" -> Constants.temperatureUnits
            "Length" -> Constants.lengthUnits
            "Electricity" -> Constants.electricUnits
            "Volume" -> Constants.volumeUnits
            "Weight" -> Constants.weightUnits
            "Angle" -> Constants.angleUnits
            "Time" -> Constants.timeUnits
            "Energy" -> Constants.energyUnits
            "Force" -> Constants.forceUnits
            "Storage" -> Constants.storageUnits
            "Currency" -> Constants.currencyUnits
            else -> {
                Constants.cryptoUnits
            }
        }
        var expandedItemFrom by remember { mutableStateOf(false) }
        var expandedItemTo by remember { mutableStateOf(false) }
        var selectedItemFrom by remember { mutableStateOf(options[0]) }
        var selectedItemTo by remember { mutableStateOf(options[1]) }
        var fromValue by remember { mutableStateOf("") }
        var toValue by remember { mutableStateOf("") }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            elevation = 5.dp,
            shape = RoundedCornerShape(15.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {

                Text(
                    text = "Select $unitMeasure type",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = expandedItemFrom,
                    onExpandedChange = {
                        expandedItemFrom = !expandedItemFrom
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    TextField(
                        value = selectedItemFrom,
                        readOnly = true,
                        onValueChange = { },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedItemFrom)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        visualTransformation = VisualTransformation.None,
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedItemFrom,
                        onDismissRequest = { expandedItemFrom = false },
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        options.forEach { selectedTextFrom ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedItemFrom = selectedTextFrom
                                    expandedItemFrom = false
                                }) {
                                Text(text = selectedTextFrom)
                            }
                        }

                    }

                }
                Text(
                    text = "Enter amount or value",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                OutlinedTextField(
                    value = fromValue,
                    onValueChange = {
                        fromValue = it
                    },
                    modifier = Modifier
                        .padding(
                            bottom = 10.dp,
                            start = 10.dp,
                            end = 10.dp
                        )
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Converted to",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            
            Image(
                modifier = Modifier
                    .weight(0.15f)
                    .background(
                        color = colorResource(id = R.color.purple_200),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(10.dp),
                painter = painterResource(id = R.drawable.exchange),
                contentDescription = "Conversion image")
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            elevation = 5.dp,
            shape = RoundedCornerShape(15.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {

                Text(
                    text = "Select converted ${unitMeasure.lowercase(Locale.getDefault())} type",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )


                ExposedDropdownMenuBox(
                    expanded = expandedItemTo,
                    onExpandedChange = {
                        expandedItemTo = !expandedItemTo
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    TextField(
                        value = selectedItemTo,
                        readOnly = true,
                        onValueChange = { },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedItemTo)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedItemTo,
                        onDismissRequest = {
                            expandedItemTo = false
                        },
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        options.forEach { selectedText ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedItemTo = selectedText
                                    expandedItemTo = false
                                }) {
                                Text(text = selectedText)
                            }
                        }
                    }
                }

                Text(
                    text = "Converted value",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                OutlinedTextField(
                    value = toValue,
                    onValueChange = {
                        toValue = it
                    },
                    modifier = Modifier
                        .padding(
                            bottom = 10.dp,
                            start = 10.dp,
                            end = 10.dp
                        )
                        .fillMaxWidth(),
                    readOnly = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }
        }

        Button(
            onClick = {
                Toast.makeText(
                    context,
                    "Value $fromValue $selectedItemFrom --> $toValue $selectedItemTo",
                    Toast.LENGTH_SHORT
                )
                    .show()
                toValue = fromValue
            },
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp),
            content = {
                Text(
                    text = "Convert",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            shape = RoundedCornerShape(10.dp)
        )
    }


}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun ConvertScreenPreview() {
    ComposerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ConvertScreenScafold(
                navController = rememberNavController(),
                unitMeasure = "Currency"
            )
        }
    }

}
