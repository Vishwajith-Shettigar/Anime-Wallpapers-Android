package com.example.aotwallpaper.Util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveCurTime(time: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong("curtime", time)
        editor.apply()
    }

    fun getLastCurTime(): Long {
        return sharedPreferences.getLong("curtime", 0)
    }


}
