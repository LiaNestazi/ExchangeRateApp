package com.lianestazi.domain.repository

import com.lianestazi.domain.models.ConvertResponseResult
import com.lianestazi.domain.models.CurrenciesResponseResult

interface ExchangeRepository {

   suspend fun getAllCurrencies(): CurrenciesResponseResult

    suspend fun convert(from: String, to: String, amount: Double): ConvertResponseResult
}