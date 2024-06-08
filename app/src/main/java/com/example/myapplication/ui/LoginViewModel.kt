package com.example.myapplication.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.utils.isValidEmail
import com.example.myapplication.ui.utils.isValidPassword

class LoginViewModel : ViewModel() {

    private val _validateFields = MutableLiveData<Boolean>()
    val validateFields = _validateFields

    fun checkFields(email: String?, password: String?) {
        _validateFields.postValue(email.isValidEmail() && password.isValidPassword())
    }
}
