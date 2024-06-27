package com.example.myapplication.ui.login.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.dto.model.StateLogin
import com.example.myapplication.data.dto.request.LoginRequest
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.data.utils.Constants
import com.example.myapplication.ui.utils.isValidEmail
import com.example.myapplication.ui.utils.isValidPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository = UserRepository()) : ViewModel() {

    private val _data = MutableLiveData<StateLogin>()
    val data: LiveData<StateLogin> = _data

    private val _validateFields = MutableLiveData<Boolean>()
    val validateFields = _validateFields

    private val _checkBoxState = MutableLiveData<Boolean>()
    val checkBoxState = _checkBoxState

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        _errorMessage.postValue(null)
    }

    fun checkFields(email: String?, password: String?) {
        _validateFields.postValue(email.isValidEmail() && password.isValidPassword())
    }

    fun setCheckBoxStatus(checkBoxStatus: Boolean) {
        _checkBoxState.postValue(checkBoxStatus)
    }

    fun login(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _data.postValue(StateLogin.Loading)
            val response = repository.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                response.body()?.let {
                    _data.postValue(StateLogin.Success(it))
                    _errorMessage.postValue(null)
                } ?: {
                    _data.postValue(StateLogin.Error(Constants.LOGIN_FAILED))
                    _errorMessage.postValue(Constants.LOGIN_FAILED)
                }
            } else {
                _data.postValue(StateLogin.Error(Constants.NETWORK_ERROR))
                _errorMessage.postValue(Constants.LOGIN_FAILED)
            }
        }
    }
}
