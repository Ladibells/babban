package com.example.bgrecruitment.data

import android.text.Editable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Int,

    @ColumnInfo(name = "user_name")
    val name: String,

    @ColumnInfo(name = "user_email")
    val email: String,

    @ColumnInfo(name = "user_password")
    val password: String
)

@Entity(tableName = "recruitment_table")
data class Recruitment(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rec_id") val id: Int,
    @ColumnInfo(name = "name") val Name: String,
    @ColumnInfo(name = "phone_number") val PhoneNumber: String,
    @ColumnInfo(name = "sex") val Sex: Editable,
    @ColumnInfo(name = "dob") val DOB: Editable?,
    @ColumnInfo(name = "bvn") val BVN: String,
    @ColumnInfo(name = "nin") val NIN: String,
    @ColumnInfo(name = "state") val State: Editable,
    @ColumnInfo(name = "lga") val LGA: String,
    @ColumnInfo(name = "hub") val Hub: String,
    @ColumnInfo(name = "gov_id") val GovID: String,
    @ColumnInfo(name = "id_type") val IdType: Editable,
    @ColumnInfo(name = "id_image") var IdImage: String,
    //@ColumnInfo(name = "user_id") val userId: Int = 0
)