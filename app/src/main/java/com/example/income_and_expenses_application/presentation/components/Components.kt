package com.example.income_and_expenses_application.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.income_and_expenses_application.util.Util

@Composable
fun AccountCard(
    modifier: Modifier = Modifier,
    cardTitle: String,
    amount:String,
    cardIcon: ImageVector?
) {

    Card(
        modifier = Modifier
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space=10.dp,
                alignment = Alignment.CenterVertically
                ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if(cardIcon != null){
                val iconColor = if(cardTitle == "TOTAL EXPENSE") Util.expenseColour.last()
                    else Util.incomeColour.last()
            }

        }
    }
}


@Composable
private fun AccountIconItem(
    modifier: Modifier = Modifier,
    cardIcon: ImageVector,
    color: Color
) {
    Surface(
        shape = CircleShape,
        color =color.copy(alpha=.1f),
        contentColor = color,
        modifier = modifier.size(36.dp)
    ) {
        Box(contentAlignment = Alignment.Center){
            Icon(
                imageVector = cardIcon,
                contentDescription = null,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}