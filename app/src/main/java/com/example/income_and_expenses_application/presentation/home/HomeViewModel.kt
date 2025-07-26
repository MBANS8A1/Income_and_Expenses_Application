package com.example.income_and_expenses_application.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.income_and_expenses_application.data.local.models.Expense
import com.example.income_and_expenses_application.data.local.models.Income
import com.example.income_and_expenses_application.data.repository.Repository
import com.example.income_and_expenses_application.util.expenseList
import com.example.income_and_expenses_application.util.incomeList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository:Repository
)
    : ViewModel(){
    val income = repository.income
    val expense = repository.expense

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    init{
        viewModelScope.launch{
            combine(income,expense){ incomeList: List<Income>,
                                     expenseList: List<Expense>
                ->
                homeUiState.copy(
                    income = incomeList,
                    expense =expenseList,
                    totalExpense = expenseList
                        .sumOf { it.expenseAmount }.toFloat(),
                    totalIncome = incomeList
                        .sumOf { it.incomeAmount }.toFloat()
                )

            }
                .collectLatest {
                    homeUiState = it
                }
        }
    }//init

    fun insertIncome() = viewModelScope.launch {
        repository.insertIncome(incomeList.random())
    }

    fun insertExpense() = viewModelScope.launch {
        repository.insertExpense(expenseList.random())
    }
}

data class HomeUiState(
    val income : List<Income> = emptyList(),
    val expense: List<Expense> = emptyList(),
    val totalExpense:Float = 0f,
    val totalIncome:Float = 0f,
)