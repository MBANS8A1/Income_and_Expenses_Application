package com.example.income_and_expenses_application.presentation.components

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> TransactionStatement(
    modifier: Modifier = Modifier,
    items: List<T>,
    colours: (T) -> Color,
    amount: Float,
    amountsTotal: Float,
    circleLabel: String,
    onItemSwiped: (T) -> Unit,
    key: (T) -> Int,
    rows:@Composable (T) -> Unit
) {
    
}