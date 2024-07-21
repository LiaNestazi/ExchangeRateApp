package com.lianestazi.domain.models

class Currency(
    var short_code: String? = null,
    var name: String? = null
)
{
    override fun toString(): String {
        return "$short_code-$name"
    }
}
