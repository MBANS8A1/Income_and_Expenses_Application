package com.example.income_and_expenses_application.util

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Util {
    val incomeColour = listOf(
        Color(0xFF37EFBA),
        Color(0xFF04B97F),
        Color(0xFF005D57),
        Color(0xFF29B82F),
        Color(0xFF008605)
    )
    val expenseColour = listOf(
        Color(0xFFFFD7D0),
        Color(0xFFFFDC78),
        Color(0xFFFFAC12),
        Color(0xFFFFAC12),
        Color(0xFFFF6951),
    )
}

//returns the date object a String in dd-MM-yyyy format
 fun formatDate(
    date: Date
):String{
   return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
       .format(date)
}
private val date = Calendar.getInstance()

//returns a Calendar instance as a random date range from now over the following week
private fun formatDate(
   date: Calendar
):String{
    val cal = date
    val dayRange = 1..7
    cal.set(Calendar.DAY_OF_WEEK,dayRange.random())
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        .format(cal.time)
}