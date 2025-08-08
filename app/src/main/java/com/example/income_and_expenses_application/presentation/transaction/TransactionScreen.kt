package com.example.income_and_expenses_application.presentation.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

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