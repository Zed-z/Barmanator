package pl.poznan.put.barmanator.data



import androidx.lifecycle.ViewModel
import com.plcoding.composegooglesignincleanarchitecture.presentation.sign_in.SignInResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel: ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

}