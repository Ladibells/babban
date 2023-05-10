package com.example.bgrecruitment.data.viewmodel



import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bgrecruitment.data.Recruitment
import com.example.bgrecruitment.data.User
import com.example.bgrecruitment.db.RecDao
import com.example.bgrecruitment.db.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

class UserViewModel(private val dao: UserDao, private val recDao: RecDao): ViewModel() {

    val users = dao.getAllUsers()
    val recruitments = recDao.getAllRecruitments()

    //User
    fun insertUser(user: User) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            dao.insertUser(user)
        }
    }

    suspend fun isEmailExists(email: String): Boolean {
        return withContext(Dispatchers.IO) {
            dao.isEmailExists(email)
        }
    }

    fun getUserByEmail(email: String): User? {
        return dao.getUserByEmail(email)
    }

    fun updateUser(user: User) = viewModelScope.launch {
        dao.updateUser(user)
    }

    fun deleteUser(user: User) = viewModelScope.launch {
        dao.deleteUser(user)
    }

    //Recruitment
    suspend fun insertRec(recruitment: Recruitment, context: Context, imageUri: Uri): Long {
        var rowId = -1L
                //First, save the image to internal storage
                val imageFile = File(context.filesDir, UUID.randomUUID().toString())
                val inputStream = context.contentResolver.openInputStream(imageUri)
                inputStream.use {input ->
                    imageFile.outputStream().use { output ->
                        input?.copyTo(output)
                    }
                }

                // Then, update the officer identification object with the image path
                recruitment.IdImage = imageFile.absolutePath
                rowId = recDao.insertRec(recruitment)

        return rowId
    }

    fun updateRec(recruitment: Recruitment) = viewModelScope.launch {
        recDao.updateRec(recruitment)
    }

    fun deleteRec(recruitment: Recruitment) = viewModelScope.launch {
        recDao.deleteRec(recruitment)
    }

}