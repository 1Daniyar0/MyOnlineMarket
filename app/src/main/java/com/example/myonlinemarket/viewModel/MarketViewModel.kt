package com.example.myonlinemarket.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.User
import com.example.domain.usecase.AddUserToDatabaseUseCase
import com.example.domain.usecase.CheckUserInDataBaseUseCase
import com.example.myonlinemarket.constant.ADDING_EXCEPTION
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarketViewModel(
    private val addUserToDatabaseUseCase: AddUserToDatabaseUseCase,
    private val checkUserInDataBaseUseCase: CheckUserInDataBaseUseCase
):ViewModel() {
    private val _userInDatabase: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    private val _currentScreen: MutableStateFlow<String> = MutableStateFlow("")

    val userInDatabase: StateFlow<Boolean?> = _userInDatabase
    val currentScreen: StateFlow<String> = _currentScreen
    fun addUserInDataBase(user: User){
        viewModelScope.launch {
            try {
                addUserToDatabaseUseCase(user = user)
                Log.e("Метод прошел","все четка")
            }catch(e: Exception) {
                Log.e(ADDING_EXCEPTION, e.toString())
            }
        }
    }
    fun checkUserInDateBase(user: User){
        viewModelScope.launch {
            try {
                _userInDatabase.value = checkUserInDataBaseUseCase(user = user)
            }catch(e: Exception) {
                Log.e(ADDING_EXCEPTION, e.toString())
            }
        }
    }

    fun addTopBarText(string: String){
        viewModelScope.launch {
            _currentScreen.value = string
        }
    }
}