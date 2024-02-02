package com.example.myonlinemarket.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ListProduct
import com.example.domain.models.User
import com.example.domain.usecase.AddUserToDatabaseUseCase
import com.example.domain.usecase.CheckUserInDataBaseUseCase
import com.example.domain.usecase.GetListOfProductUseCase
import com.example.myonlinemarket.constant.ADDING_EXCEPTION
import com.example.myonlinemarket.constant.GET_API_EXCEPTION
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarketViewModel(
    private val addUserToDatabaseUseCase: AddUserToDatabaseUseCase,
    private val checkUserInDataBaseUseCase: CheckUserInDataBaseUseCase,
    private val getListOfProductUseCase: GetListOfProductUseCase
):ViewModel() {
    private val _userInDatabase: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _currentScreen: MutableStateFlow<String> = MutableStateFlow("")
    private val _listOfProduct: MutableStateFlow<ListProduct> = MutableStateFlow(ListProduct())

    val userInDatabase: StateFlow<Boolean> = _userInDatabase
    val currentScreen: StateFlow<String> = _currentScreen
    val listOfProduct: StateFlow<ListProduct> = _listOfProduct

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
    fun checkUserInDateBase(){
        viewModelScope.launch {
            try {
                _userInDatabase.value = checkUserInDataBaseUseCase()
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

    fun getListOfProduct(){
        viewModelScope.launch {
            try {
                val result = getListOfProductUseCase.invoke()
                _listOfProduct.value = result
            }catch(e: Exception) {
                Log.e(GET_API_EXCEPTION, e.toString())
            }
        }
    }

}