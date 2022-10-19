package com.example.bloknot.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataFragModel:ViewModel() {
    val message:MutableLiveData<String> by lazy {  //переменная для сбора данных с фрагментов, by lazy для обновления данных при их изменении
        MutableLiveData<String>()
    }
}