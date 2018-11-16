package com.mporject.interns.beatna

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitClient {
    companion object {
      private var instance: Retrofit? = null

        fun getInstance() : Retrofit?{
        instance= if (instance==null) Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")//localhost for android 10.0.2.2
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build() else null
            return instance
        }
    }
}