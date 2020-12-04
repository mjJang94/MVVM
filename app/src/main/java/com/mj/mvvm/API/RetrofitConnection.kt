package com.mj.mvvm.API

import com.mj.mvvm.Vo.DataVo
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitConnection {

    @GET("mvvm_sample_data.json")
    fun getNumber(): Call<DataVo>
}