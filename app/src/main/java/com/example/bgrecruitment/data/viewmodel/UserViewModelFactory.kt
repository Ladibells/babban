package com.example.bgrecruitment.data.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bgrecruitment.db.*
import com.example.bgrecruitment.repository.QuizRepository

class UserViewModelFactory(
    private val dao: UserDao, private val recDao: RecDao,
    private val questionDao: QuestionDao, private val userResponseDao: UserResponseDao,
    private val recruitmentDao: RecruitmentDao
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(dao, recDao) as T
        } else if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            val repository = QuizRepository(questionDao, userResponseDao)
            @Suppress("UNCHECKED_CAST")
            return QuizViewModel(repository, userResponseDao) as T
        } else if (modelClass.isAssignableFrom(RecruitmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecruitmentViewModel(recruitmentDao) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}