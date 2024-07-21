package com.lianestazi.exchangerateapp.di


import com.lianestazi.exchangerateapp.presentation.MainViewModel
import com.lianestazi.exchangerateapp.presentation.ResultViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<MainViewModel>{
        MainViewModel(
            getAllCurrenciesUseCase = get()
        )
    }
    viewModel<ResultViewModel>{
        ResultViewModel(
            convertUseCase = get()
        )
    }
}