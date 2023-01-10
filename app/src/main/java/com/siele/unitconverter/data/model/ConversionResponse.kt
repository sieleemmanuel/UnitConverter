package com.siele.unitconverter.data.model

import com.google.gson.annotations.SerializedName

data class ConversionResponse(
    @SerializedName("from-type")
    val fromType: String,
    @SerializedName("from-value")
    val fromValue: String,
    @SerializedName("result")
    val result: String,
    @SerializedName("result-float")
    val resultFloat: Double,
    @SerializedName("to-type")
    val toType: String,
    @SerializedName("valid")
    val valid: Boolean
)

data class CurrencyResponse(
    @SerializedName("old_currency")
    val old_currency:String,
    @SerializedName("old_amount")
    val old_amount:Double,
    @SerializedName("new_amount")
    val new_amount:Double,
    @SerializedName("new_currency")
    val new_currency:String,
)