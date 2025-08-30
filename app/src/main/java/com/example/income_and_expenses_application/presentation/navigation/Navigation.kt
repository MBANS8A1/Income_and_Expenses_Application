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
import com.example.income_and_expenses_application.presentation.income.IncomeScreen
import com.example.income_and_expenses_application.presentation.income.IncomeViewModel
import com.example.income_and_expenses_application.presentation.transaction.TransactionAssistedFactory
import com.example.income_and_expenses_application.presentation.transaction.TransactionScreen

@Composable
fun IncomeExpenseNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    assistedFactory: TransactionAssistedFactory,
    homeViewModel: HomeViewModel,
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel,

    ) {
    val incomeState = incomeViewModel.incomeState
    val expenseState = expenseViewModel.expenseState
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
                onExpenseClick = {
                    navHostController.navigateToTransactionScreen(
                        id = it,
                        transType = ExpenseDestination.routePath
                    )
                },
                onIncomeClick = {
                    navHostController.navigateToTransactionScreen(
                        id = it,
                        transType = IncomeDestination.routePath
                    )
                },
                onInsertIncome = homeViewModel::insertIncome,
                onInsertExpense = homeViewModel::insertExpense

            )
        }
        composable(route = ExpenseDestination.routePath){
            ExpenseScreen(
                modifier = modifier,
                expenses = expenseState.expenses,
                onExpenseItemDelete = {
                    expenseViewModel.deleteExpense(it)
                },
                onExpenseItemClick = { expenseId ->
                    navHostController.navigateToTransactionScreen(
                        id = expenseId,
                        transType = ExpenseDestination.routePath
                    )
                }
            )
        }
        composable(route = IncomeDestination.routePath){
            IncomeScreen(
                modifier = modifier,
                incomes = incomeState.incomes,
                onIncomeItemDelete = {
                    incomeViewModel.deleteIncome(it)
                },
                onIncomeItemClick = {
                    navHostController.navigateToTransactionScreen(
                        id = it,
                        transType = IncomeDestination.routePath
                    )
                }
            )
        }
        composable(
            route = TransactionDestination.routeWithArgs,
            //associate the arguments with the route so application is aware of these arguments
            arguments = TransactionDestination.arguments
        ) {navBackStackEntry ->
            val transType = navBackStackEntry.arguments?.getString(TransactionDestination.TRANSACTIONTYPEARG) ?: ""
            val transId = navBackStackEntry.arguments?.getInt(TransactionDestination.IDTYPEARG) ?: -1
            TransactionScreen(
                modifier = modifier,
                transactionId = transId,
                transactionType = transType,
                assistedFactory = assistedFactory
            ){ //navigateUp
                navHostController.navigateUp()
            }

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

/*Now I need to create a NavigationHost helper function to help in navigating from the Income
  and Expense screens to the Transaction screen.
 */

fun NavHostController.navigateToTransactionScreen(
    id: Int = -1,
    transType: String = ""

){
    //create a route to contain the data I have
    val route =  "${TransactionDestination.routePath}?${TransactionDestination.TRANSACTIONTYPEARG}=$transType&${TransactionDestination.IDTYPEARG}=$id"
    //Now all other routes are popped off the backstack until the user reaches "route"
    navigateToSingleTop(route)
}