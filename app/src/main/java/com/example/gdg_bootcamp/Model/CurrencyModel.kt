package com.example.gdg_bootcamp.Model

import com.google.gson.annotations.SerializedName

data class CurrencyModel(
 @SerializedName("conversion_rates")
    val conversion_rates:Map<String,Double>
    )



