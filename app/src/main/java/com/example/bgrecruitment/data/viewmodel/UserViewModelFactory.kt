package com.example.bgrecruitment.data.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bgrecruitment.db.RecDao
import com.example.bgrecruitment.db.UserDao

class UserViewModelFactory(
    private val dao: UserDao, private val recDao: RecDao
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(dao, recDao) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}