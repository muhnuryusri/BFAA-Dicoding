package com.bangkit.githubuser.database

import android.content.ContentValues
import android.database.Cursor
import com.bangkit.githubuser.database.DatabaseContract.FavColumns.Companion.AVATAR
import com.bangkit.githubuser.database.DatabaseContract.FavColumns.Companion.USERNAME
import com.bangkit.githubuser.database.DatabaseContract.FavColumns.Companion.USER_ID
import com.bangkit.githubuser.entity.Favorite

object DatabaseHelper {

    fun mapCursorToArrayList(favoriteCursor: Cursor?): ArrayList<Favorite> {
        val favoriteList = ArrayList<Favorite>()
        favoriteCursor?.apply {
            while (moveToNext()) {
                val userId = getInt(getColumnIndexOrThrow(USER_ID))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val avatar = getString(getColumnIndexOrThrow(AVATAR))
                favoriteList.add(Favorite(userId, username, avatar))
            }
        }
        return favoriteList
    }

    fun ContentValues.toFavorite(): Favorite =
        Favorite(
            userId = getAsInteger(USER_ID),
            username = getAsString(USERNAME),
            avatar = getAsString(AVATAR)
        )

}