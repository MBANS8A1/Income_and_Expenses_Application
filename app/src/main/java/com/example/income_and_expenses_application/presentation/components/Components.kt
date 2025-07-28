package com.example.income_and_expenses_application.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.income_and_expenses_application.presentation.home.HomeUiState
import com.example.income_and_expenses_application.util.Util

@Composable
fun AccountCard(
    modifier: Modifier = Modifier,
    cardTitle: String,
    amount:String,
    cardIcon: ImageVector?
) {

    Card(
        modifier = modifier
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
                AccountIconItem(
                    modifier = Modifier.align(Alignment.End),
                    cardIcon = cardIcon,
                    color = iconColor
                )
            }
            Text(
                text= cardTitle,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha=.8f)
            )
            Text(
                text = amount,
                style= MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
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


@Composable
fun IncomeCard(
    account: HomeUiState,
    onClickSeeAll: () -> Unit,
    onItemClick: (id:Int) -> Unit
    ){
}

@Composable
private fun <T> OverViewCard(
    title: String,
    amount: Float,
    onClickSeeAll: () -> Unit,
    values: (T) -> Float,
    colors: (T) -> Color,
    data: List<T>,
    row:@Composable (T) -> Unit
) {

}

@Preview(showBackground = true)
@Composable
private fun PrevAccountCard() {
    AccountCard(
        modifier = Modifier,
        cardTitle = "TOTAL INCOME",
        amount = "20000",
        cardIcon = Icons.Default.ArrowUpward
    )
}