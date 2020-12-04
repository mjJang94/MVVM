package com.mj.mvvm

import android.app.Activity
import android.app.Application
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mj.mvvm.API.RetrofitConnection
import com.mj.mvvm.ViewModel.APIViewModel
import com.mj.mvvm.Vo.DataVo
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.component.inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var externalViewModel: APIViewModel

    @Test
    fun addition_isCorrect() {
    }
}