package com.example.income_and_expenses_application.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.income_and_expenses_application.presentation.home.HomeUiState
import com.example.income_and_expenses_application.util.Util
import com.example.income_and_expenses_application.util.formatAmount
import com.example.income_and_expenses_application.util.getColour

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

    OverViewCard(
        title = "Income",
        amount = account.totalIncome,
        onClickSeeAll = onClickSeeAll,
        data = account.income,
        values = {it.incomeAmount.toFloat()},
        colours = { getColour(
            it.incomeAmount.toFloat(),
            Util.incomeColour)
                  },
        row = 


    )
}

@Composable
fun IncomeRow(
    modifier: Modifier,
    name: String,
    description:String,
    amount:Float,
    colour: Color
) {
    
}

@Composable
private fun BaseRow(
    modifier: Modifier,
    colour: Color,
    title:String,
    subtitle: String,
    amount: Float,
    negative: Boolean

) {
    val poundSign = if (negative) "-£" else "£"
    val formattedAmount  = formatAmount(amount)
    Row(
        modifier = modifier
            .height(68.dp)
    ){
        val typography = MaterialTheme.typography
        AccountIndicator(
            colour = colour,
            modifier = modifier
        )
    }
}

@Composable
private fun AccountIndicator(colour:Color,modifier: Modifier) {
    Spacer(
        modifier = modifier
            .size(4.dp,36.dp)
            .background(colour)
    )
}


@Composable
private fun <T> OverViewCard(
    title: String,
    amount: Float,
    onClickSeeAll: () -> Unit,
    values: (T) -> Float,
    colours: (T) -> Color,
    data: List<T>,
    row:@Composable (T) -> Unit
) {
    Card {
        Column {
            Text(
                text= title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(12.dp)
            )
            OverViewDivider(
                data = data,
                values = values,
                colours = colours
            )
            Column(
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 4.dp
                )
            ) {
                data.takeLast(SHOWN_ITEMS).forEach{
                    row(it)
                }
                SeeAllButton(
                    modifier = Modifier.clearAndSetSemantics {
                        contentDescription = "All $title"
                    },
                    onClickSeeAll = onClickSeeAll
                )
            }
        }
    }
}

@Composable
fun SeeAllButton(
    modifier: Modifier,
    onClickSeeAll: () -> Unit
) {
    TextButton(
        onClick = onClickSeeAll,
        modifier = modifier
            .height(44.dp)
            .fillMaxWidth()
    ){
        Text("SEE ALL")
    }
}

@Composable
fun <T> OverViewDivider(
    data: List<T>,
    values: (T) -> Float,
    colours: (T) -> Color
) {
    Row(modifier = Modifier.fillMaxWidth()){
        data.forEach{item ->
            Spacer(
                modifier = Modifier
                    .weight(values(item))
                    .height(1.dp)
                    .background(colours(item))
            )
        }
    }
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

const val SHOWN_ITEMS = 3