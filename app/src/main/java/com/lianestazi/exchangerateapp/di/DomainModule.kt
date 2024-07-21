package com.lianestazi.exchangerateapp.di

import com.lianestazi.domain.usecase.ConvertUseCase
import com.lianestazi.domain.usecase.GetAllCurrenciesUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<GetAllCurrenciesUseCase> {
        GetAllCurrenciesUseCase(exchangeRepository = get())
    }
    factory<ConvertUseCase> {
        ConvertUseCase(exchangeRepository = get())
    }

}