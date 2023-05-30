package com.example.bgrecruitment.helpers

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

