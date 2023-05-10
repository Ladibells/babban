package com.example.bgrecruitment.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bgrecruitment.data.Recruitment
import com.example.bgrecruitment.data.User


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM user_table WHERE user_id = :id")
    fun findById(id: Int): User?

    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUser(): User?


    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT EXISTS(SELECT 1 FROM user_table WHERE user_email = :email)")
    suspend fun isEmailExists(email: String): Boolean

    @Query("SELECT * FROM user_table WHERE user_email = :email")
    fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM user_table ORDER BY user_id ASC")
    fun getAllUsers(): LiveData<List<User>>

//    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password")
//    suspend fun getUser(email: String, password: String): User?


}

@Dao
interface RecDao{
    @Insert
    suspend fun insertRec(recruitment: Recruitment): Long

    @Update
    suspend fun updateRec(recruitment: Recruitment)

    @Delete
    suspend fun deleteRec(recruitment: Recruitment)

    @Query("SELECT * FROM recruitment_table ORDER BY rec_id ASC")
    fun getAllRecruitments(): LiveData<List<Recruitment>>

}