package com.example.income_and_expenses_application.data.repository

import com.example.income_and_expenses_application.data.local.ExpenseDao
import com.example.income_and_expenses_application.data.local.IncomeDao
import com.example.income_and_expenses_application.data.local.models.Expense
import com.example.income_and_expenses_application.data.local.models.Income
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    private val incomeDao: IncomeDao,
    private val expenseDao: ExpenseDao,
    private val dispatcher: CoroutineDispatcher
):Repository {
    override val income: Flow<List<Income>>
        get() = TODO("Not yet implemented")
    override val expense: Flow<List<Expense>>
        get() = TODO("Not yet implemented")

    override suspend fun insertIncome(income: Income) {
        TODO("Not yet implemented")
    }

    override suspend fun insertExpense(expense: Expense) {
        TODO("Not yet implemented")
    }

    override fun getIncomeById(id: Int): Flow<Income> {
        TODO("Not yet implemented")
    }

    override fun getExpenseById(id: Int): Flow<Expense> {
        TODO("Not yet implemented")
    }

    override suspend fun updateIncome(income: Income) {
        TODO("Not yet implemented")
    }

    override suspend fun updateExpense(expense: Expense) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteIncome(id: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExpense(id: Int): Int {
        TODO("Not yet implemented")
    }
}

