package com.example.income_and_expenses_application.util

import androidx.compose.ui.graphics.Color
import com.example.income_and_expenses_application.data.local.models.Expense
import com.example.income_and_expenses_application.data.local.models.Income
import java.text.DecimalFormat
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

//returns the date object a String in dd/MM/yyyy format
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

//maps the week number to the String equivalent
fun formatDays(days:Int):String{
    return when(days) {
        1 -> "Mon"
        2 -> "Tue"
        3 -> "Wed"
        4 -> "Thurs"
        5 -> "Fri"
        6 -> "Sat"
        7 -> "Sun"
        else -> "Unknown"
    }
}

//will return the weekday in short weekday format (e.g."Mon")
fun formatDays(date:Date):String{
    val sdf = SimpleDateFormat("EE",Locale.getDefault())
    return sdf.format(date)
}

//categorize the expenses into colours
fun getColour(amount: Float, colours: List<Color>): Color {
    return when {
        amount < 500 -> {
            colours[0]
        }
        amount < 1000 -> {
            colours[1]
        }
        amount < 5000 -> {
            colours[2]
        }
        amount < 10000 -> {
            colours[3]
        }
        else -> {
            colours[4]
        }
    }
}


val incomeList = listOf(
    Income(
        incomeAmount = 1000.0,
        title= "Freelancing",
        description = "Payment from upwork project",
        entryDate = formatDate(date),
        date =  date.time
    ),
    Income(
        incomeAmount = 6000.0,
        title= "Salary",
        description = "Payment from permanent job",
        entryDate = formatDate(date),
        date =  date.time
    ),
    Income(
        incomeAmount = 3000.0,
        title= "Business",
        description = "Income from music beats licensing",
        entryDate = formatDate(date),
        date =  date.time
    ),
    Income(
        incomeAmount = 1200.0,
        title= "Tutor Project",
        description = "Payment from students for coding sessions",
        entryDate = formatDate(date),
        date =  date.time
    ),
    Income(
        incomeAmount = 1500.0,
        title= "Vending Machine",
        description = "Monthly soft drinks and snacks sales income",
        entryDate = formatDate(date),
        date =  date.time
    )
)

val expenseList = listOf(
    Expense(
        entryDate = formatDate(date),
        expenseAmount = 50.0,
        category= "Entertainment",
        title = "Netflix Subscription",
        description = "Paid Netflix for monthly subscription",
        date = date.time
    ),
    Expense(
        entryDate = formatDate(date),
        expenseAmount = 100.0,
        category= "Food and Drinks",
        title = "Groceries",
        description = "Paid for monthly groceries",
        date = date.time
    ),
    Expense(
        entryDate = formatDate(date),
        expenseAmount = 500.0,
        category= "Vehicle",
        title = "Car Maintenance",
        description = "Paid car tyre, brake pad and oil change",
        date = date.time
    ),
    Expense(
        entryDate = formatDate(date),
        expenseAmount = 1000.0,
        category= "Housing",
        title = "Rent",
        description = "Paid for monthly rent for apartment",
        date = date.time
    ),
    Expense(
        entryDate = formatDate(date),
        expenseAmount = 230.0,
        category= "Business Expense",
        title = "Website Hosting",
        description = "Paid Hostinger to host my UX/UI bootcamp site",
        date = date.time
    )
)

fun formatAmount(amount:Float):String{
    return amountDecimalFormat.format(amount)
}

private val amountDecimalFormat = DecimalFormat("#,###.##")