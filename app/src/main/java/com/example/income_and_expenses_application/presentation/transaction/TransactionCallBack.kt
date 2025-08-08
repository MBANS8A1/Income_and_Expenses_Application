package com.example.income_and_expenses_application.presentation.transaction

import com.example.income_and_expenses_application.presentation.navigation.IncomeExpenseDestination
import com.example.income_and_expenses_application.util.Category

interface TransactionCallBack {
    fun onTitleChange(newValue:String)
    fun onAmountChange(newValue:String)
    fun onDescriptionChange(newValue: String)
    fun onTransactionTypeChange(newValue: String)
    fun onDateChange(newValue:Long?)
    fun onScreenTypeChange(newValue:IncomeExpenseDestination)
    fun onOpenDialog(newValue:Boolean)
    fun onCategoryChange(newValue:Category)
    fun addIncome()
    fun addExpense()
    fun getIncome(id:Int)
    fun getExpense(id:Int)
    fun updateIncome()
    fun updateExpense()

}