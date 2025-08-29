package com.example.income_and_expenses_application.presentation.expense

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.income_and_expenses_application.data.local.models.Expense
import com.example.income_and_expenses_application.presentation.components.ExpenseRow
import com.example.income_and_expenses_application.presentation.components.TransactionStatement
import com.example.income_and_expenses_application.util.Util
import com.example.income_and_expenses_application.util.expenseList
import com.example.income_and_expenses_application.util.getColour
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ExpenseScreen(
    modifier: Modifier = Modifier,
    expenses: List<Expense>,
    onExpenseItemClick: (id:Int) -> Unit,
    onExpenseItemDelete: (id:Int) -> Unit
) {
    TransactionStatement(
        modifier = modifier,
        items = expenses,
        colours = { getColour(it.expenseAmount.toFloat(),
            Util.expenseColour
        ) },
        amounts = {it.expenseAmount.toFloat()},
        amountsTotal = expenses.sumOf{ it.expenseAmount }.toFloat(),
        circleLabel = "Pay",
        onItemSwiped = {
            onExpenseItemDelete.invoke(it.id)
        },
        key = {it.id}  //will increase performance of the composable

    ) { expense ->
        //row composable scope
        expense.apply{
        ExpenseRow(
            name = expense.title,
            description = "Receive ${formatDetailDate(expense.date)}",
            amount = expense.expenseAmount.toFloat(),
            colour = getColour(
                expense.expenseAmount.toFloat(),
                Util.expenseColour
            ),
            modifier = Modifier.clickable {
                onExpenseItemClick.invoke(expense.id)
            }
        )
     }
    }
}

//A function to format the Date
fun formatDetailDate(date: Date):String =
    SimpleDateFormat("dd MM", Locale.getDefault()).format(date)



@Preview(showSystemUi = true)
@Composable
private fun PrevExpenseScreen() {
    ExpenseScreen(
        expenses = expenseList.mapIndexed{ index, expense ->
            expense.copy(id = index)
        },
        onExpenseItemClick = {},
        onExpenseItemDelete = {}
    )
}