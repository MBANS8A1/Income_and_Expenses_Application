package com.example.income_and_expenses_application.presentation.home

import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.income_and_expenses_application.presentation.components.AccountCard
import com.example.income_and_expenses_application.presentation.components.ExpenseCard
import com.example.income_and_expenses_application.presentation.components.IncomeCard
import com.example.income_and_expenses_application.ui.theme.Income_and_Expenses_ApplicationTheme
import com.example.income_and_expenses_application.util.expenseList
import com.example.income_and_expenses_application.util.formatAmount
import com.example.income_and_expenses_application.util.incomeList

@Composable
fun HomeScreen(
    state: HomeUiState,
    modifier: Modifier,
    onIncomeClick: (Int) -> Unit,
    onExpenseClick: (Int) -> Unit,
    onClickSeeAllIncome: () -> Unit,
    onClickSeeAllExpense: () -> Unit

) {
    LazyColumn(
        modifier = modifier
    ) {
        item{
            Column {
                Row {
                    val balance = state.totalIncome - state.totalExpense
                    Text("Your total balance:")
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "£" +
                        formatAmount(balance),
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    AccountCard(
                        cardTitle = "TOTAL INCOME",
                        amount = "+£" + formatAmount(state.totalIncome),
                        cardIcon = Icons.Default.ArrowDownward,
                        modifier = Modifier
                            .weight(1f)
                            .padding(
                                end =4.dp,
                                top = 8.dp,
                                bottom = 8.dp
                            )
                    )
                    AccountCard(
                        cardTitle = "TOTAL EXPENSE",
                        amount = "-£" + formatAmount(state.totalExpense),
                        cardIcon = Icons.Default.ArrowUpward,
                        modifier = Modifier
                            .weight(1f)
                            .padding(
                                end =4.dp,
                                top = 8.dp,
                                bottom = 8.dp
                            )
                    )

                }
            }
            Spacer(modifier = Modifier.size(12.dp))
        }
        item{
            IncomeCard(
                account = state,
                onIncomeClick = onIncomeClick,
                onClickSeeAll = onClickSeeAllIncome
            )
            Spacer(modifier = Modifier.size(12.dp))
        }
        item{
            ExpenseCard(
                account = state,
                onExpenseClick = onExpenseClick,
                onClickSeeAll = onClickSeeAllExpense
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PrevHome() {
    Income_and_Expenses_ApplicationTheme{
        HomeScreen(
            state = HomeUiState(income = incomeList, expense = expenseList),
            modifier = Modifier,
            onIncomeClick = {},
            onClickSeeAllIncome = {},
            onExpenseClick = {},
            onClickSeeAllExpense = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PrevHomeDark() {
    Income_and_Expenses_ApplicationTheme(darkTheme = true){
        HomeScreen(
            state = HomeUiState(income = incomeList, expense = expenseList),
            modifier = Modifier,
            onIncomeClick = {},
            onClickSeeAllIncome = {},
            onExpenseClick = {},
            onClickSeeAllExpense = {}
        )
    }
}