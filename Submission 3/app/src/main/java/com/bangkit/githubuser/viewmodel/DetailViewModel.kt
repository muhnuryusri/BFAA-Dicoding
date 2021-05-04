package com.bangkit.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.githubuser.retrofit.DetailResponse
import com.bangkit.githubuser.retrofit.LoggingInterceptor
import com.bangkit.githubuser.retrofit.UserResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val TAG = DetailViewModel::class.java.simpleName
    private val listDetailUser = MutableLiveData<DetailResponse>()

    fun setDetailUser(query: String) {

        val userItems = LoggingInterceptor.providesHttpAdapter().create(UserResource::class.java)
        userItems.detailUser(query)
                .enqueue(object : Callback<DetailResponse> {

                    override fun onResponse(
                            call: Call<DetailResponse>,
                            response: Response<DetailResponse>
                    ) {
                        listDetailUser.postValue(response.body())
                        Log.d(TAG, "setDetailUser: onResponse")
                    }

                    override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                        Log.d(TAG, "setDetailUser: onFailure = $t")
                    }
                })
    }

    fun getDetailUser(): LiveData<DetailResponse?> {
        Log.d(TAG, "getDetailUser")
        return listDetailUser
    }
}