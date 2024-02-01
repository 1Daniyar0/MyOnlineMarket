package com.example.myonlinemarket.viewModel

import androidx.lifecycle.ViewModel
import com.example.domain.models.User
import com.example.domain.usecase.AddUserToDatabaseUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class MarketViewModel(
    private val addUserToDatabaseUseCase: AddUserToDatabaseUseCase
):ViewModel() {
    private val _userInfoLiveData = MutableStateFlow(User("","",""))
}