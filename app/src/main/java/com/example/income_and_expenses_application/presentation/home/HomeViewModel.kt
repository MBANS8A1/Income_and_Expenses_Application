package com.example.income_and_expenses_application.presentation.home

import androidx.lifecycle.ViewModel
import com.example.income_and_expenses_application.data.local.models.Expense
import com.example.income_and_expenses_application.data.local.models.Income
import com.example.income_and_expenses_application.data.repository.Repository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository:Repository
)
    : ViewModel(){

}

data class HomeUiState(
    val income : List<Income> = emptyList(),
    val expense: List<Expense> = emptyList(),
    val totalExpense:Float = 0f,
    val totalIncome:Float = 0f,
)