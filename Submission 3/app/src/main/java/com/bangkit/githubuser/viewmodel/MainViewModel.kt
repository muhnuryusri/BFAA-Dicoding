package com.bangkit.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.githubuser.retrofit.Items
import com.bangkit.githubuser.retrofit.LoggingInterceptor
import com.bangkit.githubuser.retrofit.MainResponse
import com.bangkit.githubuser.retrofit.UserResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val TAG = MainViewModel::class.java.simpleName
    private val listUser = MutableLiveData<ArrayList<Items>>()

    fun setUser(query: String) {

        val userItems = LoggingInterceptor.providesHttpAdapter().create(UserResource::class.java)
        userItems.searchUser(query).enqueue(object :
                Callback<MainResponse> {

            override fun onResponse(
                    call: Call<MainResponse>,
                    response: Response<MainResponse>
            ) {
                val items = response.body()?.items
                listUser.postValue(items)
                Log.d(TAG, "setSearchUser: onResponse")
            }

            override fun onFailure(call: Call<MainResponse>, t: Throwable) {
                Log.d(TAG, "setSearchUser: onFailure = $t")
            }
        })
    }

    fun getUser(): LiveData<ArrayList<Items>> {
        Log.d(TAG, "getSearchUser")
        return listUser
    }
}