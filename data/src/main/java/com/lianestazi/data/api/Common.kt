package com.lianestazi.data.api

object Common {
    private val URL = "https://api.currencybeacon.com/v1/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(URL).create(RetrofitServices::class.java)
}