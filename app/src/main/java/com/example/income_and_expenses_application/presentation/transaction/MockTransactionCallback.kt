package com.example.income_and_expenses_application.presentation.transaction

import com.example.income_and_expenses_application.presentation.navigation.IncomeExpenseDestination
import com.example.income_and_expenses_application.util.Category

class MockTransactionCallback: TransactionCallBack {
    override fun onTitleChange(newValue: String) {
        TODO("Not yet implemented")
    }

    override fun onAmountChange(newValue: String) {
        TODO("Not yet implemented")
    }

    override fun onDescriptionChange(newValue: String) {
        TODO("Not yet implemented")
    }

    override fun onTransactionTypeChange(newValue: String) {
        TODO("Not yet implemented")
    }

    override fun onDateChange(newValue: Long?) {
        TODO("Not yet implemented")
    }

    override fun onScreenTypeChange(newValue: IncomeExpenseDestination) {
        TODO("Not yet implemented")
    }

    override fun onOpenDialog(newValue: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onCategoryChange(newValue: Category) {
        TODO("Not yet implemented")
    }

    override fun addIncome() {
        TODO("Not yet implemented")
    }

    override fun addExpense() {
        TODO("Not yet implemented")
    }

    override fun getIncome(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getExpense(id: Int) {
        TODO("Not yet implemented")
    }

    override fun updateIncome() {
        TODO("Not yet implemented")
    }

    override fun updateExpense() {
        TODO("Not yet implemented")
    }
}