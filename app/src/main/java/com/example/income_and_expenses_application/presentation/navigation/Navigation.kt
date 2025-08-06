package com.example.income_and_expenses_application.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.income_and_expenses_application.presentation.expense.ExpenseScreen
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
                onClickSeeAllExpense = {
                    navHostController
                        .navigateToSingleTop(route = ExpenseDestination.routePath)
                },
                onClickSeeAllIncome = {
                    navHostController
                        .navigateToSingleTop(route = IncomeDestination.routePath)
                },
                onExpenseClick = {},
                onIncomeClick = {},
                onInsertIncome = homeViewModel::insertIncome,
                onInsertExpense = homeViewModel::insertExpense

            )
        }
        composable(route = ExpenseDestination.routePath){
            ExpenseScreen(
                expenses = expenseViewModel.expenseState.expenses,
                onExpenseItemDelete = {
                    expenseViewModel.deleteExpense(it)
                },
                onExpenseItemClick = { expenseId ->

                }
            )
        }
    }
}

/* From the HomeScreen I want to navigate to different destinations but without the backStack
 containing multiple entries, so the user is not distracted when he/she pushes the back button.
 Extension function below should help with fixing this issue.
 */

fun NavHostController.navigateToSingleTop(route:String){
     navigate(route){
         popUpTo(graph.findStartDestination().id){
             saveState = true
         }
         launchSingleTop = true
         restoreState = true
     }
}