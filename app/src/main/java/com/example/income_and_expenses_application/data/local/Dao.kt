package com.example.income_and_expenses_application.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.income_and_expenses_application.data.local.models.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense:Expense)

    @Query("DELETE FROM expense_table")
    suspend fun deleteAllExpenses()

    @Query("DELETE FROM expense_table WHERE expense_id=:id")
    suspend fun deleteExpense(id:Int):Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateExpense(expense: Expense)

    @Query("SELECT * FROM expense_table")
    fun getAllExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE expense_id=:id")
    fun getExpenseById(id:Int):Flow<Expense>

}