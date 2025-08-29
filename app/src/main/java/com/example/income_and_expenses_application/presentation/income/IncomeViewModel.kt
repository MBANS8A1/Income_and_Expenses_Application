package com.example.income_and_expenses_application.presentation.income

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.income_and_expenses_application.data.local.models.Income
import com.example.income_and_expenses_application.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {
    var incomeState by mutableStateOf(IncomeState())
        private set
    init{
        getAllIncomes()
    }

    private fun getAllIncomes() = viewModelScope.launch {
        repository.income.collectLatest {
            incomeState.copy(
                incomes = it
            )
        }
    }

    fun deleteIncome(id:Int) = viewModelScope.launch {
        repository.deleteIncome(id)
    }


}

data class IncomeState(
    val incomes: List<Income> = emptyList()
)