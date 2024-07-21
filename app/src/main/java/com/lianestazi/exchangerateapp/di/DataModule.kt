package com.lianestazi.exchangerateapp.di

import com.lianestazi.data.repository.ExchangeRepositoryImpl
import com.lianestazi.domain.repository.ExchangeRepository
import org.koin.dsl.module

val dataModule = module {
    single<ExchangeRepository> {
        ExchangeRepositoryImpl()
    }
}