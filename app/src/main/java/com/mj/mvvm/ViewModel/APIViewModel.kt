package com.mj.mvvm.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mj.mvvm.API.RetrofitConnection
import com.mj.mvvm.ContactData.Contact
import com.mj.mvvm.ContactData.ContactRepository
import com.mj.mvvm.Vo.DataVo
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class APIViewModel(application: Application) : AndroidViewModel(application),
    KoinComponent {

    val TAG = this.javaClass.simpleName

    private val exampleLiveData: MutableLiveData<DataVo> = MutableLiveData()

    //koin, inject Dependency
    private val connectService: RetrofitConnection by inject()

    val exampleData: LiveData<DataVo>
        get() = exampleLiveData

    fun requestData() {

        DataVo().apply {

            connectService.getNumber().enqueue(object : Callback<DataVo?> {
                override fun onFailure(call: Call<DataVo?>, t: Throwable) {
                    name = "데이터 연결이 정상인지 확인해 주세요."
                    age = "데이터 연결이 정상인지 확인해 주세요."
                }

                override fun onResponse(call: Call<DataVo?>, response: Response<DataVo?>) {

                    if (response.isSuccessful) {
                        val result = response.body() as DataVo

                        exampleLiveData.value = result
                    } else {
                        name = "데이터를 정상적으로 불러오지 못했습니다."
                        age = "데이터를 정상적으로 불러오지 못했습니다."
                    }
                }
            })
        }


    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "==== cleared")
    }
}




