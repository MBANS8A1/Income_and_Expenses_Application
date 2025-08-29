package com.example.income_and_expenses_application.presentation.transaction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.income_and_expenses_application.R
import com.example.income_and_expenses_application.presentation.navigation.ExpenseDestination
import com.example.income_and_expenses_application.presentation.navigation.IncomeDestination
import com.example.income_and_expenses_application.presentation.navigation.IncomeExpenseDestination
import com.example.income_and_expenses_application.util.Category
import com.example.income_and_expenses_application.util.formatDate
import java.util.Date

@Composable
fun TransactionScreen(
    modifier: Modifier,
    transactionId: Int,
    transactionType:String,
    assistedFactory: TransactionAssistedFactory,
    navigateUp: () -> Unit
){
    //get reference for the ViewModel
    val viewModel = viewModel(
        modelClass = TransactionViewModel::class.java,
        //pass in implementation of the factory
        factory = TransactionViewModelFactory(
            id = transactionId,
            transactionType = transactionType,
            assistedFactory = assistedFactory
        )//factory
    )
    TransactionScreen(
        modifier =  modifier,
        state = viewModel.state,
        transactionCallBack = viewModel, //viewModel implements the TransactionCallBack
        navigateUp = navigateUp
    )
}

@Composable
private fun TransactionScreen(
    modifier: Modifier,
    state:TransactionState,
    transactionCallBack: TransactionCallBack,
    navigateUp: () -> Unit
){ //going to be stateless
   /*When the user opens up the keyboard for typing, I want to collapse the other data
     But I want the user to be able to type any kind of data even if the keyboard is opened.
    */

    val scrollState =  rememberScrollState()
    val transactionScreenList = listOf(IncomeDestination,ExpenseDestination)
    val isExpenseTransaction = state.transactionScreen == ExpenseDestination
    val icon = when{
        isExpenseTransaction -> R.drawable.ic_expense_dollar
        else -> R.drawable.ic_income_dollar
    }
    Column(
        modifier
            .scrollable(
                state = scrollState,
                orientation = Orientation.Vertical
            )
    ) {
        //create the transaction title
        TransactionTitle(
            icon =icon,
            state = state,
            transactionCallBack =transactionCallBack,
            transactionScreenList = transactionScreenList
        )
        Spacer(modifier = Modifier.size(12.dp))
        TransactionDetails(
            state = state,
            transactionCallBack = transactionCallBack,
            isExpenseTransaction = isExpenseTransaction
        )
        Spacer(modifier = Modifier.height(24.dp))
        TransactionButton(
            state = state,
            transactionCallBack = transactionCallBack,
            navigateUp = navigateUp
            )
    }
}

@Composable
private fun TransactionButton(
    state:TransactionState,
    transactionCallBack: TransactionCallBack,
    navigateUp: () -> Unit
) {
    //When the transaction button is clicked the user can navigate back
    val buttonTitle = if(state.isUpdatingTransaction) "Update Transaction"
                else "Add Transaction"

    Button(
        onClick = {
            //check if I am updating or adding
            when (state.isUpdatingTransaction) {
                true -> {
                    //check if transaction item is an Income or Expense
                    if (state.transactionScreen == IncomeDestination) {
                        transactionCallBack.updateIncome()
                    } else {
                        transactionCallBack.updateExpense()
                    }
                }

                false -> {
                    if (state.transactionScreen == IncomeDestination) {
                        transactionCallBack.addIncome()
                    } else {
                        transactionCallBack.addExpense()

                    }
                }
            }
            //whether user is adding or updating a transaction item go back to their default screen
            navigateUp.invoke()
        },
        modifier = Modifier.fillMaxWidth(),
        //Button should be disabled if nothing is typed
        enabled = transactionCallBack.areFieldsNotEmpty
    ) {
        Text(text = buttonTitle)
    }
}

@Composable
fun TransactionTitle(
    icon:Int,
    state:TransactionState,
    transactionCallBack: TransactionCallBack,
    transactionScreenList: List<IncomeExpenseDestination>

) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(
                id=icon
                ),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = state.transactionScreen.pageTitle
        )
        Spacer(modifier = Modifier.size(6.dp))
        IconButton(onClick = {transactionCallBack.onOpenDialog(!state.openDialog)}) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }
        if(!state.openDialog){
            Popup(
                onDismissRequest = {
                    transactionCallBack.onOpenDialog(!state.openDialog)
                }
            ){
                Surface(
                    modifier = Modifier.padding(16.dp)
                ){
                    Column{
                        transactionScreenList.forEach{
                            Text(
                                text = it.pageTitle,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        transactionCallBack.onScreenTypeChange(it)
                                        transactionCallBack.onOpenDialog(!state.openDialog)
                                    }
                            )
                        }
                    }
                }
            }

        }

    }
}

@Composable
fun TransactionDetails(
    state: TransactionState,
    transactionCallBack: TransactionCallBack,
    isExpenseTransaction: Boolean,
) {
    Column {
        TransactionTextField(
            value = state.title,
            onValueChange = transactionCallBack::onTitleChange,
            labelText = "Transaction Title"
        )
        Spacer(modifier = Modifier.size(12.dp))
        TransactionTextField(
            value = state.amount,
            onValueChange = transactionCallBack::onAmountChange,
            labelText = "Amount",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.size(12.dp))
        //Add the date picker via external composable
        IncExpDate(
            date = state.date,
            onDateChange = transactionCallBack::onDateChange
        )
        TransactionTextField(
            value = state.description,
            onValueChange = transactionCallBack::onDescriptionChange,
            labelText = "Transaction Description"
        )
        Spacer(modifier = Modifier.size(12.dp))
        //want to shop input points/chips where the user can choose the category of expense
        AnimatedVisibility(isExpenseTransaction){
            //AnimatedVisibility will track compose tree of the particular animation
            LazyRow {
                //enum.entries returns a List of the Category constants
                items(Category.entries.toTypedArray()){ category ->
                    InputChip(
                        selected = category == state.category,
                        onClick = {
                            transactionCallBack.onCategoryChange(category)
                        },
                        label = {Text(text= category.title)},
                        modifier = Modifier.padding(8.dp),
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = category.iconRes),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IncExpDate(
    date: Date,
    onDateChange: (Long?) -> Unit,

) {

    val datePickerState =  rememberDatePickerState() //still experimental
    //tracking opening of the dialog
    var openDateDialog by remember { mutableStateOf(value=false) }
    //Now to add the data arranged in a row format

    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = formatDate(date))
        Spacer(modifier = Modifier.size(4.dp))
        if(openDateDialog){
            DatePickerDialog(
                onDismissRequest = {openDateDialog = false},
                confirmButton = {
                    Button(
                        onClick = {
                            onDateChange(datePickerState.selectedDateMillis)
                        }
                    ){
                        Text(text="Submit")

                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDateDialog = false
                        }
                    ) {
                        Text(text="Dismiss")
                    }
                },
                content = {
                    DatePicker(
                        state = datePickerState,
                    )
                }
            )
        }
        //need to add an icon to be able to convert openDateDialog back to to true
        IconButton(
            onClick = {
                openDateDialog = true
            }
        ){
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun TransactionTextField(
   value:String,
   onValueChange: (String) -> Unit, //this will update the state
   labelText: String,
   keyboardOptions: KeyboardOptions = KeyboardOptions.Default, //text but can change to other types

) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {Text(text = labelText)},
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.extraLarge //will create the circular-edged pill shape
    )
}

@Preview(showSystemUi = true)
@Composable
private fun PrevTransaction() {
    TransactionScreen(
        state = TransactionState(),
        transactionCallBack = MockTransactionCallback(),
        modifier = Modifier,
        navigateUp = {}
    )
}