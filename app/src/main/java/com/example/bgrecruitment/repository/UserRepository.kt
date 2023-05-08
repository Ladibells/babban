package com.example.bgrecruitment.repository

import androidx.lifecycle.LiveData
import com.example.bgrecruitment.data.Recruitment
import com.example.bgrecruitment.data.User
import com.example.bgrecruitment.db.RecDao
import com.example.bgrecruitment.db.UserDao

class UserRepository(private val userDao: UserDao, private val recDao: RecDao) {

    val getAllUsers: LiveData<List<User>> = userDao.getAllUsers()
    val getAllRecruitments: LiveData<List<Recruitment>> = recDao.getAllRecruitments()

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun insertRec(recruitment: Recruitment) {
        recDao.insertRec(recruitment)
    }
}