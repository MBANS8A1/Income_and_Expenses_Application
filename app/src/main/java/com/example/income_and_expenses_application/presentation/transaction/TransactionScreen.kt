package com.example.income_and_expenses_application.presentation.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TransactionScreen(
    state: TransactionState,
    transactionCallBack: TransactionCallBack,
    isExpenseTransaction: Boolean,
) {
    Column {
        
    }
}

@Composable
private fun TransactionTextField(
   value:String,
   onValueChange: (String) -> Unit, //this will update the state
   labelText: String,
   keyboardOptions: KeyboardOptions = KeyboardOptions.Default, //text but can change to other types

) {
    
}