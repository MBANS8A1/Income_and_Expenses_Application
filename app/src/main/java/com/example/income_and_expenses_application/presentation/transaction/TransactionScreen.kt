package com.example.income_and_expenses_application.presentation.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.income_and_expenses_application.util.formatDate
import java.util.Date

@Composable
fun TransactionScreen(
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
        //want to shop input points where the user can choose the category of expense


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