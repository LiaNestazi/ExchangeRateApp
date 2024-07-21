package com.lianestazi.domain.models

data class CurrenciesResponseResult (
    var errorMessage: String? = null,
    var currencies: MutableList<Currency>? = null
)