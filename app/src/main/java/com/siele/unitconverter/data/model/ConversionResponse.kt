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