import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.dto.model.StateLogin
import com.example.myapplication.data.dto.model.StateRegister
import com.example.myapplication.data.dto.request.RegisterRequest
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.data.utils.Constants
import com.example.myapplication.ui.utils.isConfirmedPassword
import com.example.myapplication.ui.utils.isValidEmail
import com.example.myapplication.ui.utils.isValidPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository = UserRepository()) : ViewModel() {

    private val _data = MutableLiveData<StateRegister>()
    val data: LiveData<StateRegister> = _data

    private val _validateFields = MutableLiveData<Boolean>()
    val validateFields: LiveData<Boolean> get() = _validateFields

    private val _checkBoxPasswordState = MutableLiveData<Boolean>()
    val checkBoxPasswordState: LiveData<Boolean> get() = _checkBoxPasswordState

    private val _checkBoxConfirmPasswordState = MutableLiveData<Boolean>()
    val checkBoxConfirmPasswordState: LiveData<Boolean> get() = _checkBoxConfirmPasswordState

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    init {
        _errorMessage.value = null
    }

    fun checkFields(email: String, password: String, confirmPassword: String) {
        val isValid =
            email.isValidEmail() && password.isValidPassword() && password.isConfirmedPassword(
                confirmPassword
            )
        _validateFields.value = isValid
        if (!isValid) {
            _errorMessage.value = "Error en el usuario o contraseña!"
        } else {
            _errorMessage.value = null
        }
    }

    fun setCheckBoxPasswordStatus(isChecked: Boolean) {
        _checkBoxPasswordState.value = isChecked
    }

    fun setCheckBoxConfirmPasswordStatus(isChecked: Boolean) {
        _checkBoxConfirmPasswordState.value = isChecked
    }

    fun register(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _data.postValue(StateRegister.Loading)
            val response = repository.register(RegisterRequest(email, password))
            if (response.isSuccessful) {
                response.body()?.let {
                    _data.postValue(StateRegister.Success(it))
                } ?: _data.postValue(StateRegister.Error(Constants.LOGIN_FAILED))
            } else {
                _data.postValue(StateRegister.Error(Constants.NETWORK_ERROR))
            }
        }
    }
}
