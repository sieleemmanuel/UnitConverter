package com.siele.unitconverter.ui.screens.convert

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.siele.unitconverter.ui.screens.main.MainViewModel
import com.siele.unitconverter.ui.theme.ComposerTheme
import com.siele.unitconverter.util.Constants
import com.siele.unitconverter.util.NetworkMonitor
import com.siele.unitconverter.util.Resource
import kotlinx.coroutines.launch
import java.util.*

@Composable
@ExperimentalMaterialApi
fun ConvertScreen(navController: NavController, unitMeasure: String) {
    Surface {
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { ConvertTopBar(unitMeasure, navController = navController) },
            content = { innerPadding ->
                ConvertScreenContent(
                    unitMeasure = unitMeasure,
                    paddingValues = innerPadding,
                    scaffoldState = scaffoldState
                )
            }
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
    scaffoldState: ScaffoldState
) {
    val options = when (unitMeasure) {
        stringResource(R.string.temperature_label) -> Constants.temperatureUnits
        stringResource(R.string.length_label) -> Constants.lengthUnits
        stringResource(R.string.electricity_label) -> Constants.electricCurrentUnits
        stringResource(R.string.volume_label) -> Constants.volumeUnits
        stringResource(R.string.weight_label) -> Constants.weightUnits
        stringResource(R.string.angle_label) -> Constants.angleUnits
        stringResource(R.string.time_label) -> Constants.timeUnits
        stringResource(R.string.energy_label) -> Constants.energyUnits
        stringResource(R.string.force_label) -> Constants.forceUnits
        stringResource(R.string.storage_label) -> Constants.storageUnits
        stringResource(R.string.currency_label) -> Constants.currencyUnits
        else -> {
            Constants.cryptoUnits
        }
    }
    val focusManager = LocalFocusManager.current
    val TAG = "ConvertScreen"
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
    ) {

        val boxWithConstraints = this
        var expandedItemFrom by rememberSaveable { mutableStateOf(false) }
        var selectedItemFrom by rememberSaveable { mutableStateOf(options[0]) }
        var expandedItemTo by rememberSaveable { mutableStateOf(false) }
        var selectedItemTo by rememberSaveable { mutableStateOf(options[1]) }
        var fromValue by rememberSaveable { mutableStateOf("") }
        var toValue by rememberSaveable { mutableStateOf("") }
        var showProgressBar by rememberSaveable { mutableStateOf(false) }
        var isError by rememberSaveable { mutableStateOf(false) }
        val context = LocalContext.current

        val focusRequester = remember { FocusRequester() }
        // var isConverted by rememberSaveable { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        val scrollState = rememberScrollState()

        //val resultValue by viewModel.responseState.observeAsState()
        val convertedResult by viewModel.unitValueEvent.collectAsState(initial = null)
        val convertedCurrencyResult by viewModel.currencyValueEvent.collectAsState(initial = null)

        if (unitMeasure == context.getString(R.string.currency_label)){
            Log.d(TAG, "ConvertScreenContent: $unitMeasure")
            Log.d(TAG, "ConvertScreenContent: $convertedResult")
            when (convertedCurrencyResult) {
                is Resource.Success -> {
                    showProgressBar = false
                    convertedCurrencyResult?.data?.let { result ->
                        toValue = result.new_amount.toString()
                        //isConverted = false
                        Log.d(TAG, "ConvertScreenContent: $result")
                    }
                }
                is Resource.Error -> {
                    showProgressBar = false
                    convertedCurrencyResult?.message?.let { message ->
                        Log.d("ConvertScreen", "Error: $message ")
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = message
                            )
                        }
                       // isConverted = false
                    }
                }
                is Resource.Loading -> {
                    showProgressBar = true
                    // isConverted = false
                }
                null -> Unit
            }
        }else{
        when (convertedResult) {
            is Resource.Success -> {
                showProgressBar = false
                convertedResult?.data?.let { conversionResult ->
                    toValue = conversionResult.resultFloat.toString()
                    // isConverted = false
                }
            }
            is Resource.Error -> {
                showProgressBar = false
                convertedResult?.message?.let { message ->
                    Log.d("ConvertScreen", "Error: $message ")
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = message
                        )
                    }
                    //isConverted = false
                }
            }
            is Resource.Loading -> {
                showProgressBar = true
               // isConverted = false
            }
            null -> Unit
        }
        }
        if (boxWithConstraints.maxWidth < 600.dp) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
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
                            onExpandedChange = { expandedItemFrom = !expandedItemFrom },
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
                                onDismissRequest = { expandedItemFrom = !expandedItemFrom },
                                modifier = Modifier.verticalScroll(rememberScrollState())
                            ) {
                                options.forEach { selectedTypeFrom ->
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedItemFrom = selectedTypeFrom
                                            expandedItemFrom = false
                                        }
                                    ) {
                                        Text(text = selectedTypeFrom)
                                    }
                                }
                            }
                        }

                        Text(
                            text = stringResource(R.string.enter_value_label),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )

                        OutlinedTextField(
                            value = fromValue,
                            onValueChange = {
                                fromValue = it
                                if (it.isNotEmpty()) {
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
                                .fillMaxWidth()
                                .focusRequester(focusRequester = focusRequester),
                            shape = RoundedCornerShape(10.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.clearFocus() }
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            isError = isError
                        )
                        if (isError) {
                            Text(
                                text = stringResource(R.string.empty_value_label),
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
                        text = stringResource(R.string.converted_to_label),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                    )
                    if (showProgressBar) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    Image(
                        modifier = Modifier
                            .size(40.dp)
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
                            text = stringResource(R.string.converted_value_label),
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
                        if (fromValue.isEmpty()) {
                            isError = true
                        } else {
                            if(unitMeasure == context.getString(R.string.currency_label)){
                                viewModel.triggerCurrencyValue(
                                    have = selectedItemFrom,
                                    want = selectedItemTo,
                                    amount = fromValue.toDouble())
                            }else{
                                viewModel.triggerUnitValue(
                                    fromValue = fromValue,
                                    fromType = selectedItemFrom,
                                    toType = selectedItemTo,
                                    isConnected = NetworkMonitor.isNetworkConnected(context = context)
                                )
                            }
                        }
                        if (unitMeasure == context.getString(R.string.currency_label)){
                            Log.d(TAG, "ConvertScreenContent: $unitMeasure")
                            Log.d(TAG, "ConvertScreenContent: $convertedResult")
                            when (convertedCurrencyResult) {
                                is Resource.Success -> {
                                    showProgressBar = false
                                    convertedCurrencyResult?.data?.let { result ->
                                        toValue = result.new_amount.toString()
                                        //isConverted = false
                                        Log.d(TAG, "ConvertScreenContent: $result")
                                    }
                                }
                                is Resource.Error -> {
                                    showProgressBar = false
                                    convertedCurrencyResult?.message?.let { message ->
                                        Log.d("ConvertScreen", "Error: $message ")
                                        coroutineScope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = message
                                            )
                                        }
                                        // isConverted = false
                                    }
                                }
                                is Resource.Loading -> {
                                    showProgressBar = true
                                    // isConverted = false
                                }
                                null -> Unit
                            }
                        }else{
                            when (convertedResult) {
                                is Resource.Success -> {
                                    showProgressBar = false
                                    convertedResult?.data?.let { conversionResult ->
                                        toValue = conversionResult.resultFloat.toString()
                                        // isConverted = false
                                        Log.d(TAG, "ConvertScreenContent: ${convertedResult!!.data}")
                                    }
                                }
                                is Resource.Error -> {
                                    showProgressBar = false
                                    convertedResult?.message?.let { message ->
                                        Log.d("ConvertScreen", "Error: $message ")
                                        coroutineScope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = message
                                            )
                                        }
                                        //isConverted = false
                                    }
                                }
                                is Resource.Loading -> {
                                    showProgressBar = true
                                    // isConverted = false
                                }
                                null -> Unit
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 56.dp),
                    content = {
                        Text(
                            text = stringResource(R.string.convert_label),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    shape = RoundedCornerShape(10.dp)
                )

            }
        } /*else {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .padding(8.dp),
                        elevation = 5.dp,
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                        ) {

                            Text(
                                text = "Select $unitMeasure type",
                                modifier = Modifier
                                    .padding(10.dp)
                            )

                            ExposedDropdownMenuBox(
                                expanded = expandedItemFrom,
                                onExpandedChange = { expandedItemFrom = !expandedItemFrom },
                                modifier = Modifier
                                    .padding(10.dp)
                            ) {
                                TextField(
                                    value = selectedItemFrom,
                                    readOnly = true,
                                    onValueChange = { },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedItemFrom)
                                    },
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
                                    onDismissRequest = { expandedItemFrom = !expandedItemFrom },
                                    modifier = Modifier.verticalScroll(rememberScrollState())
                                ) {
                                    options.forEach { selectedTypeFrom ->
                                        DropdownMenuItem(
                                            onClick = {
                                                selectedItemFrom = selectedTypeFrom
                                                expandedItemFrom = false
                                            }
                                        ) {
                                            Text(text = selectedTypeFrom)
                                        }
                                    }
                                }
                            }

                            Text(
                                text = stringResource(R.string.enter_value_label),
                                modifier = Modifier
                                    .padding(10.dp)
                            )

                            OutlinedTextField(
                                value = fromValue,
                                onValueChange = {
                                    fromValue = it
                                    if (it.isNotEmpty()) {
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
                                    .focusRequester(focusRequester = focusRequester),
                                shape = RoundedCornerShape(10.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = { focusManager.clearFocus() }
                                ),
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                ),
                                isError = isError
                            )

                            if (isError) {
                                Text(
                                    text = stringResource(R.string.empty_value_label),
                                    color = MaterialTheme.colors.error,
                                    modifier = Modifier
                                        .padding(start = 16.dp)

                                )
                            }
                        }

                    }

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "To",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Image(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = MaterialTheme.colors.primary,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(10.dp)
                                .rotate(90f),
                            painter = painterResource(id = R.drawable.exchange),
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onPrimary),
                            contentDescription = "Conversion image"
                        )

                        if (showProgressBar) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = 5.dp,
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                        ) {

                            Text(
                                text = "Select converted ${unitMeasure.lowercase(Locale.getDefault())} type",
                                modifier = Modifier
                                    .padding(8.dp)
                            )

                            ExposedDropdownMenuBox(
                                expanded = expandedItemTo,
                                onExpandedChange = {
                                    expandedItemTo = !expandedItemTo
                                },
                                modifier = Modifier
                                    .padding(8.dp)
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
                                text = stringResource(R.string.converted_value_label),
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
                }

                Button(
                    onClick = {
                        if (fromValue.isEmpty()) {
                            isError = true
                        } else {
                            viewModel.getValue(
                                fromValue = fromValue,
                                fromType = selectedItemFrom,
                                toType = selectedItemTo,
                                isConnected = NetworkMonitor.isNetworkConnected(context = context)
                            )
                            isConverted = true
                        }
                    },
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 56.dp),
                    content = {
                        Text(
                            text = stringResource(R.string.convert_label),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    shape = RoundedCornerShape(10.dp)
                )

            }
        }

*/
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Preview(
    showSystemUi = true,
)
@Composable
fun ConvertScreenPreview() {
    ComposerTheme {
        ConvertScreen(
            navController = rememberNavController(),
            unitMeasure = "Currency"
        )
    }

}
