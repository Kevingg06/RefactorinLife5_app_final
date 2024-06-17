package com.example.myapplication.ui.login.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.dto.model.StateLoginViewModel
import com.example.myapplication.data.dto.request.LoginRequest
import com.example.myapplication.data.repository.LoginRepository
import com.example.myapplication.ui.utils.isValidEmail
import com.example.myapplication.ui.utils.isValidPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository = LoginRepository()) : ViewModel() {

    private val _data = MutableLiveData<StateLoginViewModel>()
    val data: LiveData<StateLoginViewModel> = _data

    private val _validateFields = MutableLiveData<Boolean>()
    val validateFields = _validateFields

    private val _checkBoxState = MutableLiveData<Boolean>()
    val checkBoxState = _checkBoxState

    fun checkFields(email: String?, password: String?) {
        _validateFields.postValue(email.isValidEmail() && password.isValidPassword())
    }

    fun setCheckBoxStatus(checkBoxStatus : Boolean){
        _checkBoxState.postValue(checkBoxStatus)
    }

    fun login(email: String, password: String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.login(LoginRequest(email,password))
            if (response.isSuccessful) {
                response.body()?.let {
                    _data.postValue(StateLoginViewModel.Success(it))
                } ?: _data.postValue(StateLoginViewModel.Error("No data"))
            } else {
                _data.postValue(StateLoginViewModel.Error("Service error"))
            }
        }
    }
}
