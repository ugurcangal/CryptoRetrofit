package com.ugurcangal.cryptoretrofit.service

import com.ugurcangal.cryptoretrofit.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {

    //https://api.nomics.com/v1/prices?key=bbf7b8c658b665892189bd256bc6052bad2b9e16

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getData() : Observable<List<CryptoModel>>

}