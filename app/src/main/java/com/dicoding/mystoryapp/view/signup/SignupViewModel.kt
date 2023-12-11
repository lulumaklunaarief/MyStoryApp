package com.dicoding.mystoryapp.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystoryapp.data.UserRepository
import com.dicoding.mystoryapp.data.retrofit.response.SignupResponse
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: UserRepository) : ViewModel() {
    val signupResponse: LiveData<SignupResponse> = repository.signupResponse
    val showLoading: LiveData<Boolean> = repository.showLoading

    fun signup(name: String, email:String, password:String) {
        viewModelScope.launch {
            repository.signup(name, email, password)
        }
    }
}