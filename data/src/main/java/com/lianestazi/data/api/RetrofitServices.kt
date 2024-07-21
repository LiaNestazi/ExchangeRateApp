package com.lianestazi.data.api

import com.lianestazi.data.api.models.ConvertCall
import com.lianestazi.data.api.models.CurrenciesCall
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {
    @GET("currencies")
    fun getCurrencies(
        @Query("api_key") key: String
    ): Call<CurrenciesCall>


    @GET("convert")
    fun convert(
        @Query("api_key") key: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): Call<ConvertCall>

}