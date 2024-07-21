package com.lianestazi.data.repository

import com.lianestazi.data.api.Common
import com.lianestazi.data.api.models.ConvertCall
import com.lianestazi.data.api.models.CurrenciesCall
import com.lianestazi.data.api.models.Error
import com.lianestazi.domain.models.ConvertResponseResult
import com.lianestazi.domain.models.CurrenciesResponseResult
import com.lianestazi.domain.repository.ExchangeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.await

const val API_KEY = "lSM7ITcoSRfJR8gLTvVP08bFh3EKcyxo"

class ExchangeRepositoryImpl: ExchangeRepository {
    override suspend fun getAllCurrencies(): CurrenciesResponseResult {
        val currenciesCall = CurrenciesCall()

        val job = CoroutineScope(Dispatchers.IO).async {
            var call = CurrenciesCall()
            try {
                call = Common.retrofitService.getCurrencies(API_KEY).await()
            } catch (e: HttpException){
                val error = JSONObject(e.response()?.errorBody()?.string()!!).getJSONObject("meta")
                currenciesCall.error = Error(code = error.getString("code"), error_type = error.getString("error_type"), error_detail = error.getString("error_detail"))
            }
            call
        }
        return currenciesToDomain(job.await())
    }

    override suspend fun convert(from: String, to: String, amount: Double): ConvertResponseResult {
        val convertCall = ConvertCall()
        val job = CoroutineScope(Dispatchers.IO).async {
            var call = ConvertCall()
            try {
                call = Common.retrofitService.convert(API_KEY, from, to, amount).await()
            } catch (e: HttpException){
                val error = JSONObject(e.response()?.errorBody()?.string()!!).getJSONObject("meta")
                convertCall.error = Error(code = error.getString("code"), error_type = error.getString("error_type"), error_detail = error.getString("error_detail"))
            }
            call
        }
        return convertToDomain(job.await())
    }

    private fun currenciesToDomain(call: CurrenciesCall): CurrenciesResponseResult{
        val currenciesResponseResult = CurrenciesResponseResult()

        if (call.error != null){
            currenciesResponseResult.errorMessage = call.error!!.error_detail
        } else{
            if (call.response != null){
                currenciesResponseResult.currencies = mutableListOf()
                call.response!!.forEach {curr ->
                    currenciesResponseResult.currencies!!.add(curr)
                }
            }
        }

        return currenciesResponseResult
    }

    private fun convertToDomain(call: ConvertCall): ConvertResponseResult{
        val convertResponseResult = ConvertResponseResult()

        if (call.error != null){
            convertResponseResult.errorMessage = call.error!!.error_detail
        } else{
            if (call.response != null){
                convertResponseResult.from = call.response!!.from
                convertResponseResult.to = call.response!!.to
                convertResponseResult.amount = call.response!!.amount
                convertResponseResult.value = call.response!!.value
            }
        }

        return convertResponseResult
    }
}