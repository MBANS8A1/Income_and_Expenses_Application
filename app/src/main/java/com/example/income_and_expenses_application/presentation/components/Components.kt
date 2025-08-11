package com.example.income_and_expenses_application.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.income_and_expenses_application.R
import com.example.income_and_expenses_application.data.local.models.Expense
import com.example.income_and_expenses_application.data.local.models.Income
import com.example.income_and_expenses_application.presentation.home.HomeUiState
import com.example.income_and_expenses_application.presentation.navigation.HomeDestination
import com.example.income_and_expenses_application.presentation.navigation.IncomeExpenseDestination
import com.example.income_and_expenses_application.util.Util
import com.example.income_and_expenses_application.util.formatAmount
import com.example.income_and_expenses_application.util.getColour
import com.example.income_and_expenses_application.util.incomeList


//For the app bar(top bar)
//For back-navigation too if I am on the Income/Expense Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeExpenseAppBar(
    @DrawableRes icon: Int = R.drawable.ic_switch_off,
    title:String,
    onSwitchClick: () -> Unit,
    onNavigateUp: () -> Unit
) {
    TopAppBar(
        title = {
          Text(text=title)
        },
        navigationIcon = {
            //check if inside HomeScreen; if not then we don't show the navigation icon
            AnimatedVisibility(
                visible = title != HomeDestination.pageTitle
            ) {
                IconButton(
                  onClick = onNavigateUp
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null

                    )
                }
            }
        },
        actions = {
            //have icon at the end of the top bar to change the theme
            AnimatedContent(targetState = icon) { iconRes ->
                //AnimateContentScope it:Int
                IconButton(onClick = onSwitchClick) {
                    Icon(
                        painter = painterResource(id=iconRes),
                        contentDescription = null
                    )
                }
            }
        }
    )
    
}


@Composable
fun IncomeExpenseBottomBar(
    allScreens: List<IncomeExpenseDestination>,
    onTabSelected:(IncomeExpenseDestination) -> Unit,
    selectedTab:IncomeExpenseDestination,
    onFabClick: () -> Unit //floating action button click
) {
    BottomAppBar(
        floatingActionButton = {
            IncomeExpenseFab(
                onFabClick = onFabClick,
                selectedTab = selectedTab
            )
        },
        actions = {
            allScreens.forEach{ screen ->
                //I want to display the tabs here
                IncExpRowTab(
                    text = screen.pageTitle,
                    icon = screen.iconResId,
                    onSelected = {
                        onTabSelected(screen)
                    },
                    selected = selectedTab == screen
                )
            }
        },
    )
    
}

@Composable
fun IncomeExpenseFab(
    onFabClick: () -> Unit,
    selectedTab:IncomeExpenseDestination,

) {
    FloatingActionButton(
        onClick = onFabClick
    ){
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null
        )
    }
}

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
fun ExpenseCard(
    account: HomeUiState,
    onClickSeeAll: () -> Unit,
    onExpenseClick: (id:Int) -> Unit
    ){

    OverViewCard(
        title = "Expense",
        amount = account.totalExpense,
        onClickSeeAll = onClickSeeAll,
        data = account.expenses,
        values = {it.expenseAmount.toFloat()},
        colours = { getColour(
            it.expenseAmount.toFloat(),
            Util.expenseColour)
                  }
    ){ expense:Expense ->
        ExpenseRow(
            name = expense.title,
            description = expense.description,
            amount = expense.expenseAmount.toFloat(),
            colour = getColour(
                expense.expenseAmount.toFloat(),
                Util.expenseColour
            ),
            modifier = Modifier.clickable{
                onExpenseClick.invoke(expense.id)
            }
        )
    }
}

@Composable
fun IncomeCard(
    account: HomeUiState,
    onClickSeeAll: () -> Unit,
    onIncomeClick: (id:Int) -> Unit
){

    OverViewCard(
        title = "Income",
        amount = account.totalIncome,
        onClickSeeAll = onClickSeeAll,
        data = account.incomes,
        values = {it.incomeAmount.toFloat()},
        colours = { getColour(
            it.incomeAmount.toFloat(),
            Util.incomeColour)
        }
    ){ income: Income ->
        IncomeRow(
            name = income.title,
            description = income.description,
            amount = income.incomeAmount.toFloat(),
            colour = getColour(
                income.incomeAmount.toFloat(),
                Util.incomeColour),
            modifier = Modifier.clickable{
                onIncomeClick.invoke(income.id)
            }
        )
    }
}

@Composable
fun IncomeRow(
    modifier: Modifier = Modifier,
    name: String,
    description:String,
    amount:Float,
    colour: Color
) {
    BaseRow(
        modifier = modifier,
        colour = colour,
        title = name,
        subtitle = description,
        amount = amount,
        negative = false
    )
}

@Composable
fun ExpenseRow(
    modifier: Modifier = Modifier,
    name: String,
    description:String,
    amount:Float,
    colour: Color
) {
    BaseRow(
        modifier = modifier,
        colour = colour,
        title = name,
        subtitle = description,
        amount = amount,
        negative = true
    )
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
            .height(68.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        val typography = MaterialTheme.typography
        AccountIndicator(
            colour = colour,
            modifier = modifier
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                style = typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = poundSign,
                style = typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                text = formattedAmount,
                style = typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterVertically),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(24.dp)
        )
    }
    IncomeExpenseDivider()
}

@Composable
fun IncomeExpenseDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        color = MaterialTheme.colorScheme.background,
        thickness = 2.dp,
        modifier = modifier
        )
}



@Composable
private fun AccountIndicator(colour:Color,modifier: Modifier) {
    Spacer(
        modifier = modifier
            .size(4.dp, 36.dp)
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
@Composable()
fun PrevIncomeCard() {
    IncomeCard(
        account = HomeUiState(
            incomes = incomeList
        ),
        onClickSeeAll = {},
        onIncomeClick = {}
    )
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