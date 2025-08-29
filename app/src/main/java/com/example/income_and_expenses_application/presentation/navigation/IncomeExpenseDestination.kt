package com.example.income_and_expenses_application.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.income_and_expenses_application.R

//want to create destinations for navigating from one place to another
//create destinations, which inherit from IncomeExpenseDestination
sealed class IncomeExpenseDestination {
    //icon of particular destination (resource id)
    abstract val iconResId: Int
    //path(destination String) I want to navigate to
    abstract val routePath: String
    //name of the particular page
    abstract val pageTitle: String

}

object HomeDestination: IncomeExpenseDestination(){
    override val iconResId: Int = R.drawable.ic_home
    override val routePath: String = "home"
    override val pageTitle: String = "Home"
}

object IncomeDestination: IncomeExpenseDestination(){
    override val iconResId: Int
        get() = R.drawable.ic_income_dollar
    override val routePath: String = "income"
    override val pageTitle: String = "Income"
}

object ExpenseDestination: IncomeExpenseDestination(){
    override val iconResId: Int
        get() = R.drawable.ic_expense_dollar
    override val routePath: String
        get() = "expense"
    override val pageTitle: String
        get() = "Expense"
}


object TransactionDestination:IncomeExpenseDestination(){
    override val iconResId: Int = R.drawable.add_entry
    override val routePath: String = "transaction"
    override val pageTitle: String = "Add Transaction"

     const val TRANSACTIONTYPEARG = "Type"
     const val IDTYPEARG = "id"
    val arguments = listOf(
        navArgument(TRANSACTIONTYPEARG){
           type =  NavType.StringType
            defaultValue = ""
        },
        navArgument(TRANSACTIONTYPEARG){
            type =  NavType.IntType
            defaultValue = -1
        }
        )
    val routeWithArgs =
        "$routePath?$TRANSACTIONTYPEARG={$TRANSACTIONTYPEARG}&$IDTYPEARG={$IDTYPEARG}"
}
