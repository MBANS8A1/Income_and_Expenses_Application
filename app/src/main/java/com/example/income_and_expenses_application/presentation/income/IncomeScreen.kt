package com.example.income_and_expenses_application.presentation.income

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.income_and_expenses_application.data.local.models.Income
import com.example.income_and_expenses_application.presentation.components.IncomeRow
import com.example.income_and_expenses_application.presentation.components.TransactionStatement
import com.example.income_and_expenses_application.util.Util
import com.example.income_and_expenses_application.util.getColour
import com.example.income_and_expenses_application.util.incomeList
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun IncomeScreen(
    modifier: Modifier = Modifier,
    incomes: List<Income>,
    onIncomeItemClick: (id:Int) -> Unit,
    onIncomeItemDelete: (Int) -> Unit
) {
    TransactionStatement(
        modifier = modifier,
        items = incomes,
        colours = { getColour(it.incomeAmount.toFloat(),
                    Util.incomeColour
            ) },
        amounts = {it.incomeAmount.toFloat()},
        amountsTotal = incomes.sumOf{ it.incomeAmount }.toFloat(),
        circleLabel = "Receive",
        onItemSwiped = {
            onIncomeItemDelete.invoke(it.id)
        },
        key = {it.id}  //will increase performance of the composable
    ){
        //row composable scope
        IncomeRow(
                name = it.title,
                description = "Received ${formatDetailDate(it.date)}",
                amount= it.incomeAmount.toFloat(),
                colour = getColour(it.incomeAmount.toFloat(),
                    Util.incomeColour
                ),
                modifier = Modifier.clickable {
                onIncomeItemClick.invoke(it.id)
            }

        )
    }
}

//A function to format the Date
fun formatDetailDate(date: Date):String =
    SimpleDateFormat("dd MM", Locale.getDefault()).format(date)


@Preview(showSystemUi = true)
@Composable
private fun PrevIncomeScreen() {
    IncomeScreen(
        incomes = incomeList.mapIndexed{ index, income ->
            income.copy(id = index)
        },
        onIncomeItemClick = {},
        onIncomeItemDelete = {}
    )
}