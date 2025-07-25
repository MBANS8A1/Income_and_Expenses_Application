package com.example.income_and_expenses_application.data.repository

import com.example.income_and_expenses_application.data.local.ExpenseDao
import com.example.income_and_expenses_application.data.local.IncomeDao
import com.example.income_and_expenses_application.data.local.models.Expense
import com.example.income_and_expenses_application.data.local.models.Income
import com.example.income_and_expenses_application.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    private val incomeDao: IncomeDao,
    private val expenseDao: ExpenseDao,
  @IoDispatcher  private val dispatcher: CoroutineDispatcher
):Repository {
    override val income: Flow<List<Income>>
        get() = incomeDao.getAllIncomes()
    override val expense: Flow<List<Expense>>
        get() = expenseDao.getAllExpenses()

    override suspend fun insertIncome(income: Income) =
        withContext(dispatcher){
            incomeDao.insertIncome(income)
    }

    override suspend fun insertExpense(expense: Expense)=
    withContext(dispatcher){
        expenseDao.insertExpense(expense)
    }

    override fun getIncomeById(id: Int): Flow<Income> =
        incomeDao.getIncomeById(id)

    override fun getExpenseById(id: Int): Flow<Expense> =
        expenseDao.getExpenseById(id)

    override suspend fun updateIncome(income: Income) =
        withContext(dispatcher){
            incomeDao.updateIncome(income)
        }
    override suspend fun updateExpense(expense: Expense) =
        withContext(dispatcher){
            expenseDao.updateExpense(expense)
        }

    override suspend fun deleteIncome(id: Int): Int  =
        withContext(dispatcher){
            incomeDao.deleteIncome(id)
        }

    override suspend fun deleteExpense(id: Int): Int  =
        withContext(dispatcher){
            expenseDao.deleteExpense(id)
        }
}

