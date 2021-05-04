package com.bangkit.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.githubuser.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private val TAG = MainViewModel::class.java.simpleName
    private val listUser = MutableLiveData<ArrayList<User>>()

    fun setUser(username: String) {
        val listItems = ArrayList<User>()

        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=${username}"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    Log.d(TAG, result)
                    val responseObject = JSONObject(result)
                    val item = responseObject.getJSONArray("items")

                    for (i in 0 until item.length()) {
                        val user = item.getJSONObject(i)
                        val userItems = User()
                        userItems.username = user.getString("login")
                        userItems.avatar = user.getString("avatar_url")
                        listItems.add(userItems)
                    }
                    listUser.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getUsers() : LiveData<ArrayList<User>> {
        return listUser
    }
}