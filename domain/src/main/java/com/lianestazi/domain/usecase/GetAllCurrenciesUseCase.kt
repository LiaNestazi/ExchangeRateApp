package com.lianestazi.domain.usecase

import com.lianestazi.domain.models.CurrenciesResponseResult
import com.lianestazi.domain.repository.ExchangeRepository

class GetAllCurrenciesUseCase(private val exchangeRepository: ExchangeRepository) {
    suspend fun execute(): CurrenciesResponseResult{
        return exchangeRepository.getAllCurrencies()
    }
}