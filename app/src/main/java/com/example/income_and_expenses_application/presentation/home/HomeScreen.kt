package com.example.income_and_expenses_application.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.income_and_expenses_application.ui.theme.Income_and_Expenses_ApplicationTheme

@Composable
fun HomeScreen(
    state: HomeUiState,
    modifier: Modifier
) {

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