package com.example.myapplication.data.dto.dataSource

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.data.utils.Constants.USER_TOKEN

fun saveToken(context: Context, token: String?) {

    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("userPreferences", Context.MODE_PRIVATE)

    val editor: SharedPreferences.Editor = sharedPreferences.edit()

    editor.putString(USER_TOKEN, token)

    editor.apply()
}

fun getToken(context: Context): String? {

    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("userPreferences", Context.MODE_PRIVATE)

    return sharedPreferences.getString(USER_TOKEN, null)
}
