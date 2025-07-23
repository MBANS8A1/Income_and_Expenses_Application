package com.example.income_and_expenses_application.data.local.converters

import androidx.room.TypeConverter
import java.util.Date

open class DateConverter {

    //receive a long and return a date
    @TypeConverter
    fun toDate(date:Long?): Date?{
        return date?.let{ Date(it) }
    }

    @TypeConverter
    fun fromDate(date:Date?):Long?{
        return date?.time
    }
}

