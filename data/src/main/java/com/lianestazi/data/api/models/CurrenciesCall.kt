package com.lianestazi.data.api.models

import com.lianestazi.domain.models.Currency

data class CurrenciesCall(
    var error: Error? = null,
    var response: List<Currency>? = null
)