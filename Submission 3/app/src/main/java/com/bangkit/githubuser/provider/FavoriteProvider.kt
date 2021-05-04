package com.bangkit.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.bangkit.githubuser.database.DatabaseContract.AUTHORITY
import com.bangkit.githubuser.database.DatabaseContract.FavColumns.Companion.CONTENT_URI
import com.bangkit.githubuser.database.DatabaseContract.FavColumns.Companion.TABLE_NAME
import com.bangkit.githubuser.database.DatabaseHelper.toFavorite
import com.bangkit.githubuser.database.FavoriteDatabase

class FavoriteProvider : ContentProvider() {
    companion object {
        private const val USER = 1
        private const val USERID = 2

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var favDb: FavoriteDatabase

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)
            uriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", USERID)
        }
    }

    override fun onCreate(): Boolean {
        favDb = FavoriteDatabase.getInstance(context as Context)
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            USER -> favDb.favoriteDao().getUsers()
            USERID -> uri.lastPathSegment?.toInt()?.let { favDb.favoriteDao().getUserById(it) }
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (USER) {
            uriMatcher.match(uri) -> values?.toFavorite()?.let {
                favDb.favoriteDao().insertUser(it)
            } ?: 0
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (USERID) {
            uriMatcher.match(uri) -> uri.lastPathSegment?.toInt()?.let {
                favDb.favoriteDao().deleteUser(
                    it
                )
            } ?: 0
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }
}