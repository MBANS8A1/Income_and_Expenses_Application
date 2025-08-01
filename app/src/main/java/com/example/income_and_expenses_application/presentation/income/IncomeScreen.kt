package com.example.income_and_expenses_application.presentation.income

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.income_and_expenses_application.data.local.models.Income

@Composable
fun IncomeScreen(
    modifier: Modifier = Modifier,
    incomes: List<Income>,
    onIncomeItemClick: (id:Int) -> Unit,
    onIncomeItemDelete: (Int) -> Unit
) {
    
}