package com.example.gdg_bootcamp

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.http.GET

interface CurrencyAPI {

    @GET("fede95ea452f105dd5526c50/latest/EUR")
    fun getEuroData():Call<CurrencyModel>
    @GET("fede95ea452f105dd5526c50/latest/USD")
    fun getDolarData():Call<CurrencyModel>
    @GET("fede95ea452f105dd5526c50/latest/TRY")
    fun getTLData():Call<CurrencyModel>
    @GET("fede95ea452f105dd5526c50/latest/GBP")
    fun getSterlinData():Call<CurrencyModel>

}