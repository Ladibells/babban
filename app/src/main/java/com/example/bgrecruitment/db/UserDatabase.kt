package com.example.bgrecruitment.db

import android.content.Context
import androidx.room.*
import com.example.bgrecruitment.Converter
import com.example.bgrecruitment.DateConverter
import com.example.bgrecruitment.ImageConverter
import com.example.bgrecruitment.data.Recruitment
import com.example.bgrecruitment.data.User

@Database(entities = [User::class, Recruitment::class], version = 4, exportSchema = false)
@TypeConverters(Converter::class, DateConverter::class)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun recDao(): RecDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase?= null

        fun getInstance(context: Context): UserDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_data_database"
                    ).fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }
    }
}