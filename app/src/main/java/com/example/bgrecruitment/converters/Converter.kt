package com.example.bgrecruitment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.Editable
import android.text.SpannableStringBuilder
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.*

class Converter {
    @TypeConverter
    fun fromEditable(editable: Editable): String? {
        return editable?.toString()
    }

    @TypeConverter
    fun toEditable(value: String): Editable? {
        return value?.let { SpannableStringBuilder(it) }
    }
}



class DateConverter {
    @TypeConverter
    fun toDate(timeLong: Long): Date {
        return Date(timeLong)
    }

    @TypeConverter
    fun toTimeLong(date: Date): Long {
        return date.time
    }
}

class ImageConverter {
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray? {
        if (bitmap == null) {
            return null
        }
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray?): Bitmap? {
        if (byteArray == null) {
            return null
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}