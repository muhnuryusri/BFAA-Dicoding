package com.bangkit.githubuser.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.bangkit.githubuser.database.*
import com.bangkit.githubuser.entity.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {

    private val TAG = FavoriteViewModel::class.simpleName
    private val queryAll = MutableLiveData<ArrayList<Favorite>>()
    private val queryById = MutableLiveData<ArrayList<Favorite>>()

    fun getQueryAll(context: Context): LiveData<ArrayList<Favorite>> {
        Log.d(TAG, "getQueryAll")
        viewModelScope.launch(Dispatchers.Main) {
            val deferredQueryAll = async(Dispatchers.IO) {
                val cursor = context.contentResolver?.query(
                        DatabaseContract.FavColumns.CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                )
                DatabaseHelper.mapCursorToArrayList(cursor)
            }
            val get = deferredQueryAll.await()
            queryAll.postValue(get)
        }
        return queryAll
    }

    fun getQueryById(context: Context, uriWithId: Uri): LiveData<ArrayList<Favorite>> {
        Log.d(TAG, "getQueryById")
        viewModelScope.launch(Dispatchers.Main) {
            val deferredQueryById = async(Dispatchers.IO) {
                val cursor = context.contentResolver?.query(uriWithId, null, null, null, null)
                DatabaseHelper.mapCursorToArrayList(cursor)
            }
            val get = deferredQueryById.await()
            queryById.postValue(get)
        }
        return queryById
    }
}