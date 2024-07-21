package com.lianestazi.data.api.models

data class Error (
    var code: String? = null,
    var error_type: String? = null,
    var error_detail: String? = null
)