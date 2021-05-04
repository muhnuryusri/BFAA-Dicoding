package com.bangkit.consumerapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.consumerapp.retrofit.FollowingResponse
import com.bangkit.consumerapp.retrofit.LoggingInterceptor
import com.bangkit.consumerapp.retrofit.UserResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    private val TAG = FollowingViewModel::class.java.simpleName
    private val listFollowingUser = MutableLiveData<ArrayList<FollowingResponse>>()

    fun setFollowingUser(query: String) {

        val userItems = LoggingInterceptor.providesHttpAdapter().create(UserResource::class.java)
        userItems.followingUser(query)
                .enqueue(object : Callback<ArrayList<FollowingResponse>> {
                    override fun onResponse(
                            call: Call<ArrayList<FollowingResponse>>,
                            response: Response<ArrayList<FollowingResponse>>
                    ) {
                        val items = response.body()
                        listFollowingUser.postValue(items)
                        Log.d(TAG, "setFollowingUser: onResponse")
                    }

                    override fun onFailure(call: Call<ArrayList<FollowingResponse>>, t: Throwable) {
                        Log.d(TAG, "setFollowingUser: onFailure = $t")
                    }
                })

    }

    fun getFollowingUser(): LiveData<ArrayList<FollowingResponse>> {
        Log.d(TAG, "getFollowingUser")
        return listFollowingUser
    }
}