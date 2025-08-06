package com.example.income_and_expenses_application.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.income_and_expenses_application.presentation.expense.ExpenseViewModel
import com.example.income_and_expenses_application.presentation.home.HomeScreen
import com.example.income_and_expenses_application.presentation.home.HomeViewModel
import com.example.income_and_expenses_application.presentation.income.IncomeViewModel

@Composable
fun IncomeExpenseNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel,

    ) {
    NavHost(
        navController = navHostController,
        startDestination = HomeDestination.routePath
    ){
        composable(route = HomeDestination.routePath){
            HomeScreen(
                state = homeViewModel.homeUiState,
                modifier = modifier,
                onClickSeeAllExpense = {},
                onClickSeeAllIncome = {},
                onExpenseClick = {},
                onIncomeClick = {},
                onInsertIncome = homeViewModel::insertIncome,
                onInsertExpense = homeViewModel::insertExpense

            )
        }
    }
}