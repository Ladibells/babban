package com.example.bgrecruitment.helpers

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast


class PreferenceHelper(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveSwitchState(recruitmentId: String, isChecked: Boolean) {
        sharedPreferences.edit().putBoolean(recruitmentId, isChecked).apply()
    }

    fun getSwitchState(recruitmentId: String): Boolean {
        return sharedPreferences.getBoolean(recruitmentId, false)
    }

    companion object {
        private const val PREF_NAME = "SwitchStatePrefs"
    }
}