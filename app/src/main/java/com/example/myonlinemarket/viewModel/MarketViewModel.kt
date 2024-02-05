package com.example.myonlinemarket.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ListProduct
import com.example.domain.models.Product
import com.example.domain.models.User
import com.example.domain.usecase.AddProductFavoritesDbUseCase
import com.example.domain.usecase.AddUserToDatabaseUseCase
import com.example.domain.usecase.GetIdsProductsFavoritesInDbUseCase
import com.example.domain.usecase.CheckUserInDataBaseUseCase
import com.example.domain.usecase.DeleteProductFavoritesDbUseCase
import com.example.domain.usecase.DeleteUserFromDbUseCase
import com.example.domain.usecase.GetListOfProductUseCase
import com.example.domain.usecase.GetProductListFavoritesUseCase
import com.example.domain.usecase.GetUserFromDbUseCase
import com.example.myonlinemarket.constant.ADDING_EXCEPTION
import com.example.myonlinemarket.constant.CHECK_EXCEPTION
import com.example.myonlinemarket.constant.DELETE_DB_EXCEPTION
import com.example.myonlinemarket.constant.GET_API_EXCEPTION
import com.example.myonlinemarket.constant.GET_DB_EXCEPTION
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarketViewModel(
    private val addUserToDatabaseUseCase: AddUserToDatabaseUseCase,
    private val checkUserInDataBaseUseCase: CheckUserInDataBaseUseCase,
    private val getListOfProductUseCase: GetListOfProductUseCase,
    private val getProductListFavoritesUseCase: GetProductListFavoritesUseCase,
    private val deleteProductFavoritesDbUseCase: DeleteProductFavoritesDbUseCase,
    private val addProductFavoritesDbUseCase: AddProductFavoritesDbUseCase,
    private val getIdsProductsFavoritesInDbUseCase: GetIdsProductsFavoritesInDbUseCase,
    private val getUserFromDbUseCase: GetUserFromDbUseCase,
    private val deleteUserFromDbUseCase: DeleteUserFromDbUseCase
):ViewModel() {
    private val _userInDatabase: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _selectedProduct: MutableStateFlow<Product> = MutableStateFlow(Product())
    private val _listOfIdFavorites: MutableStateFlow<ArrayList<String>> = MutableStateFlow(arrayListOf())
    private val _currentScreen: MutableStateFlow<String> = MutableStateFlow("")
    private val _listOfProduct: MutableStateFlow<ListProduct> = MutableStateFlow(ListProduct())
    private val _listOfFavorites: MutableStateFlow<ArrayList<Product>> = MutableStateFlow(arrayListOf())
    private val _userFromDb: MutableStateFlow<User> = MutableStateFlow(User("","",""))

    val selectedProduct: StateFlow<Product> = _selectedProduct
    val userInDatabase: StateFlow<Boolean> = _userInDatabase
    val listOfIdFavorites: StateFlow<ArrayList<String>> = _listOfIdFavorites
    val currentScreen: StateFlow<String> = _currentScreen
    val listOfProduct: StateFlow<ListProduct> = _listOfProduct
    val userFromDb: StateFlow<User> = _userFromDb

    fun selectProduct(product: Product){
        _selectedProduct.value = product
    }
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
                Log.e(CHECK_EXCEPTION, e.toString())
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

    fun getFavoritesListFromDb(){
        viewModelScope.launch {
            try {
                val result = getProductListFavoritesUseCase.invoke()
                _listOfFavorites.value = result
            }catch(e: Exception) {
                Log.e(GET_DB_EXCEPTION, e.toString())
            }
        }
    }

    fun deleteFavoriteFromDb(product: Product){
        viewModelScope.launch {
            try {
                deleteProductFavoritesDbUseCase(product)
            }catch(e: Exception) {
                Log.e(DELETE_DB_EXCEPTION, e.toString())
            }
        }
    }

    fun addProductInFavorites(product: Product){
        viewModelScope.launch {
            try {
                addProductFavoritesDbUseCase.invoke(product)
            }catch(e: Exception) {
                Log.e(ADDING_EXCEPTION, e.toString())
            }
        }
    }

    fun getIdFavoriteProductDb(){
        viewModelScope.launch {
            try {
                val result = getIdsProductsFavoritesInDbUseCase.invoke()
                _listOfIdFavorites.value = result
            }catch(e: Exception) {
                Log.e(GET_DB_EXCEPTION, e.toString())
            }
        }
    }

    fun getUserFroDb(){
        viewModelScope.launch {
            try {
                val result = getUserFromDbUseCase.invoke()
                _userFromDb.value = result
            }catch(e: Exception) {
                Log.e(GET_DB_EXCEPTION, e.toString())
            }
        }
    }

    fun deleteUserFroDb(){
        viewModelScope.launch {
            try {
                deleteUserFromDbUseCase.invoke()
            }catch(e: Exception) {
                Log.e(DELETE_DB_EXCEPTION, e.toString())
            }
        }
    }

}