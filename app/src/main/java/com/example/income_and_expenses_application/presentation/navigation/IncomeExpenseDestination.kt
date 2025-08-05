package com.example.income_and_expenses_application.presentation.navigation

import com.example.income_and_expenses_application.R

//want to create destinations for navigating from one place to another
//create destinations, which inherit from IncomeExpenseDestination
sealed class IncomeExpenseDestination {
    //icon of particular destination (resource id)
    abstract val iconResId: Int
    //path(destination String) I want to navigate to
    abstract val routePath: String
    //name of the particular page
    abstract val pageTitle: String,

}

object HomeDestination: IncomeExpenseDestination(){
    override val iconResId: Int
        get() = R.drawable.ic_home
    override val routePath: String
        get() = "home"
    override val pageTitle: String
        get() = "Home"
}

object IncomeDestination: IncomeExpenseDestination(){
    override val iconResId: Int
        get() = R.drawable.ic_income_dollar
    override val routePath: String
        get() = "income"
    override val pageTitle: String
        get() = "Income"
}

object ExpenseDestination: IncomeExpenseDestination(){
    override val iconResId: Int
        get() = R.drawable.ic_expense_dollar
    override val routePath: String
        get() = "expense"
    override val pageTitle: String
        get() = "Expense"
}
