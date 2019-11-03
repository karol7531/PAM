package com.apocalypse_survivors.przepisyapp.ui.modify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModifyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is modify Fragment"
    }
    val text: LiveData<String> = _text
}