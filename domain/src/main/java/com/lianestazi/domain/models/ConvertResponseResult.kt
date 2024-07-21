package com.lianestazi.domain.models

data class ConvertResponseResult (
    var errorMessage: String? = null,
    var from: String? = null,
    var to: String? = null,
    var amount: Double? = null,
    var value: Double? = null
)