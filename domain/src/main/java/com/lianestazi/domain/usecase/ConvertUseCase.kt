package com.lianestazi.domain.usecase

import com.lianestazi.domain.models.ConvertResponseResult
import com.lianestazi.domain.repository.ExchangeRepository

class ConvertUseCase(private val exchangeRepository: ExchangeRepository) {
    suspend fun execute(from: String, to: String, amount: Double): ConvertResponseResult {
        return exchangeRepository.convert(from,to,amount)
    }
}