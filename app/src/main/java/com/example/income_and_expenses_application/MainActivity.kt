package com.example.income_and_expenses_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.income_and_expenses_application.presentation.expense.ExpenseViewModel
import com.example.income_and_expenses_application.presentation.home.HomeScreen
import com.example.income_and_expenses_application.presentation.home.HomeViewModel
import com.example.income_and_expenses_application.presentation.income.IncomeViewModel
import com.example.income_and_expenses_application.presentation.navigation.IncomeExpenseNavHost
import com.example.income_and_expenses_application.presentation.transaction.TransactionAssistedFactory
import com.example.income_and_expenses_application.ui.theme.Income_and_Expenses_ApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /*To initialize the assistedFactory variable annotate it with @Inject.
      At runtime DH will inject this variable
     */
    @Inject
    lateinit var assistedFactory: TransactionAssistedFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val homeViewModel: HomeViewModel = viewModel()
            val expenseViewModel: ExpenseViewModel = viewModel()
            val incomeViewModel: IncomeViewModel = viewModel()
            val navHostController = rememberNavController()
            Income_and_Expenses_ApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color= MaterialTheme.colorScheme.background
                ){
                    IncomeExpenseNavHost(
                        modifier = Modifier.padding(16.dp),
                        navHostController = navHostController,
                        homeViewModel = homeViewModel,
                        incomeViewModel = incomeViewModel,
                        expenseViewModel = expenseViewModel,
                        assistedFactory =  assistedFactory
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Income_and_Expenses_ApplicationTheme {
        Greeting("Android")
    }
}