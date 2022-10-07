package com.siele.unitconverter.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.siele.unitconverter.R
import com.siele.unitconverter.ui.theme.ComposerTheme
import com.siele.unitconverter.util.Constants
import com.siele.unitconverter.util.NetworkMonitor
import com.siele.unitconverter.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.util.*

@Composable
@ExperimentalMaterialApi
fun ConvertScreen(navController: NavController, unitMeasure: String) {
    Surface {
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState =scaffoldState,
            topBar = { ConvertTopBar(unitMeasure, navController = navController) },
            content = { innerPadding ->
                ConvertScreenContent(
                    unitMeasure = unitMeasure,
                    paddingValues = innerPadding,
                    scaffoldState = scaffoldState
                ) }
        )

    }

}

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")

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

@ExperimentalMaterialApi
@Composable
fun ConvertScreenContent(
    unitMeasure: String,
    viewModel: MainViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    scaffoldState:ScaffoldState
) {
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
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {

            var expandedItemFrom by rememberSaveable { mutableStateOf(false) }
            var selectedItemFrom by rememberSaveable { mutableStateOf(options[0]) }
            var expandedItemTo by rememberSaveable { mutableStateOf(false) }
            var selectedItemTo by rememberSaveable { mutableStateOf(options[1]) }
            var fromValue by rememberSaveable { mutableStateOf("") }
            var toValue by rememberSaveable { mutableStateOf("") }
            var showProgressBar by rememberSaveable { mutableStateOf(false) }
            var isError by rememberSaveable { mutableStateOf(false) }
            val context = LocalContext.current
            var isConverted by rememberSaveable { mutableStateOf(false) }
            val coroutineScope = rememberCoroutineScope()

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
                            if (it.isNotEmpty()){
                                isError = false
                            }
                        },
                        singleLine = true,
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
                        ),
                        isError = isError
                    )
                    if(isError){
                    Text(
                        text = "Value is empty!",
                        color = MaterialTheme.colors.error,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxWidth()
                    )
                }
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Converted to",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                )
                if (showProgressBar){
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp)
                    )
                }

                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(10.dp),
                    painter = painterResource(id = R.drawable.exchange),
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onPrimary),
                    contentDescription = "Conversion image"
                )
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
                        singleLine = true,
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
                    if (fromValue.isEmpty()){
                        isError = true
                    }else {
                        if (NetworkMonitor.isNetworkConnected(context = context)) {
                            showProgressBar = true

                            viewModel.getValue(
                                fromValue = fromValue,
                                fromType = selectedItemFrom,
                                toType = selectedItemTo
                            )
                            isConverted = true
                        }else{
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "No internet connection, check and try again"
                                )
                            }
                        }
                    }
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

            val resultValue by viewModel.responseState.observeAsState(null)
            if (isConverted) {
                when (resultValue) {
                    is Resource.Success -> {
                        showProgressBar = false
                        resultValue?.data?.let { conversionResult ->
                            toValue = conversionResult.resultFloat.toString()
                            isConverted = false
                        }
                    }
                    is Resource.Error -> {
                        showProgressBar = false
                        resultValue?.message?.let { message ->
                            Log.d("ConvertScreen", "Error: $message ")
                            Toast.makeText(
                                context,
                                "An error occurred during conversion, try again",
                                Toast.LENGTH_SHORT
                            ).show()
                            isConverted = false
                        }
                    }

                    else -> {
                        showProgressBar = false
                        Toast.makeText(
                            context,
                            "An error occurred during conversion, try again",
                            Toast.LENGTH_SHORT
                        ).show()

                        isConverted = false
                    }
                }
            }

        }
    }
}



@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    showSystemUi = true,
)
@Composable
@ExperimentalMaterialApi
fun ConvertScreenPreview() {
    ComposerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val scaffoldState = rememberScaffoldState()
            Scaffold(
                scaffoldState =scaffoldState,
                topBar = { ConvertTopBar(
                    "Currency",
                    navController = rememberNavController()
                ) },
                content = { innerPadding ->
                    ConvertScreenContent(
                        unitMeasure = "Currency",
                        paddingValues = innerPadding,
                        scaffoldState = scaffoldState
                    ) }
            )
        }
    }

}
