package com.example.income_and_expenses_application.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.income_and_expenses_application.data.local.converters.DateConverter
import com.example.income_and_expenses_application.data.local.models.Expense
import com.example.income_and_expenses_application.data.local.models.Income

@TypeConverters(value= [DateConverter::class])
@Database(
    entities = [Income::class, Expense::class],
    exportSchema = false,
    version=1)
abstract class IncomeExpenseDatabase:RoomDatabase() {
    abstract val expenseDao:ExpenseDao
    abstract val incomeDao:IncomeDao
}