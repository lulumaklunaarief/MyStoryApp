package com.dicoding.mystoryapp.data

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.mystoryapp.data.pref.UserModel
import com.dicoding.mystoryapp.data.pref.UserPreference
import com.dicoding.mystoryapp.data.retrofit.api.ApiService
import com.dicoding.mystoryapp.data.retrofit.response.SignupResponse
import com.dicoding.mystoryapp.utils.EventHandler
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(
    private val userPreference: UserPreference, privat val apiService: ApiService
){
    private val _signupResponse = MutableLiveData<SignupResponse>()
    val signupResponse: LiveData<SignupResponse> = _signupResponse


    private val _customEvent = MutableLiveData<String>()
    val customEvent: LiveData<String> = _customEvent


    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    fun signup(name: String, email: String, password: String) {
        _showLoading.value = true
        val client = apiService.signup(name, email, password)

        client.enqueue(object : Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>, response: Response<SignupResponse>
            ) {
                _showLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _signupResponse.value = response.body()
                } else {
                    _customEvent.value = "Terjadi kesalahan ketika daftar"
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                _showLoading.value = false
                Toast.makeText(this, "Silahkan isi semua data terlebih dahulu", Toast.LENGTH_SHORT)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}