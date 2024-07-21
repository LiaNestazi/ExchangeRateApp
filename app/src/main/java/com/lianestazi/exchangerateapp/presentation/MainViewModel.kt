package com.lianestazi.exchangerateapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lianestazi.domain.models.Currency
import com.lianestazi.domain.usecase.GetAllCurrenciesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase
): ViewModel() {

    private val currenciesListPrivate = MutableLiveData<List<Currency>>()
    val currenciesList: LiveData<List<Currency>> = currenciesListPrivate

    private val errorMessagePrivate = MutableLiveData<String>()
    val errorMessagePublic = errorMessagePrivate

    init {
        getAllCurrencies()
    }

    private fun getAllCurrencies(){
        CoroutineScope(Dispatchers.IO).launch {
            val result = getAllCurrenciesUseCase.execute()
            if (result.errorMessage != null){
                errorMessagePrivate.postValue(result.errorMessage!!)
            } else{
                if (result.currencies != null){
                    currenciesListPrivate.postValue(result.currencies!!)
                } else{
                    errorMessagePrivate.postValue("Невозможно получить список валют")
                }
            }
        }

    }

}
