package com.example.income_and_expenses_application.presentation.home

import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.income_and_expenses_application.ui.theme.Income_and_Expenses_ApplicationTheme
import com.example.income_and_expenses_application.util.formatAmount

@Composable
fun HomeScreen(
    state: HomeUiState,
    modifier: Modifier
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
                Row{

                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PrevHome() {
    Income_and_Expenses_ApplicationTheme{
        HomeScreen(
            state = HomeUiState(),
            modifier = Modifier
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PrevHomeDark() {
    Income_and_Expenses_ApplicationTheme(darkTheme = true){
        HomeScreen(
            state = HomeUiState(),
            modifier = Modifier
        )
    }
}