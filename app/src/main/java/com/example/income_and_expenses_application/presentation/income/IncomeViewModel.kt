package com.example.income_and_expenses_application.presentation.income

import androidx.lifecycle.ViewModel
import com.example.income_and_expenses_application.data.local.models.Income
import com.example.income_and_expenses_application.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {
}


data class IncomeState(
    val incomes: List<Income> = emptyList()
)