package com.example.income_and_expenses_application.presentation.components

import android.graphics.Color
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
    LazyColumn(
        modifier = modifier
    ) {
        item{
            Box(modifier = Modifier.padding(16.dp)){

            }
        }
    }
}


@Composable
fun AnimateCircle(
    proportions: List<Float>,
    colours: List<Color>,
    modifier: Modifier = Modifier

) {
    val currentState = remember{
        MutableTransitionState
    }
}