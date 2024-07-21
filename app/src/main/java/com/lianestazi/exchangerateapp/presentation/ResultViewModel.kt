package com.lianestazi.exchangerateapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lianestazi.domain.usecase.ConvertUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultViewModel(
    private val convertUseCase: ConvertUseCase
): ViewModel() {

    private val valuePrivate = MutableLiveData<Double>()
    val valuePublic = valuePrivate

    private val errorMessagePrivate = MutableLiveData<String>()
    val errorMessagePublic = errorMessagePrivate

    fun convert(from: String, to: String, amount: Double){
        CoroutineScope(Dispatchers.IO).launch {
            val result = convertUseCase.execute(from, to, amount)
            if (result.errorMessage != null){
                errorMessagePrivate.postValue(result.errorMessage!!)
            } else{
                if (result.value != null){
                    valuePrivate.postValue(result.value!!)
                } else{
                    errorMessagePrivate.postValue("There are no results!")
                }
            }
        }
    }
}