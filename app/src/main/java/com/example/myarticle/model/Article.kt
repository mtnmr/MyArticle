package com.example.myarticle.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity
data class Article(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val title:String = "",
    val link:String = "",
    val date:Date? = null
)

class DateConverter {
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimeStamp(date:Date?): Long?{
        return date?.time?.toLong()
    }
}
