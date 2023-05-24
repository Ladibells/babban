package com.example.bgrecruitment.data.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bgrecruitment.db.QuestionDao
import com.example.bgrecruitment.db.RecDao
import com.example.bgrecruitment.db.UserDao
import com.example.bgrecruitment.db.UserResponseDao
import com.example.bgrecruitment.repository.QuizRepository

class UserViewModelFactory(
    private val dao: UserDao, private val recDao: RecDao,
    private val questionDao: QuestionDao, private val userResponseDao: UserResponseDao
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(dao, recDao) as T
        } else if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            val repository = QuizRepository(questionDao, userResponseDao)
            @Suppress("UNCHECKED_CAST")
            return QuizViewModel(repository, recDao,userResponseDao) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}