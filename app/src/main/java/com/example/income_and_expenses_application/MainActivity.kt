package com.example.income_and_expenses_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.income_and_expenses_application.presentation.components.IncomeExpenseAppBar
import com.example.income_and_expenses_application.presentation.expense.ExpenseViewModel
import com.example.income_and_expenses_application.presentation.home.HomeScreen
import com.example.income_and_expenses_application.presentation.home.HomeViewModel
import com.example.income_and_expenses_application.presentation.income.IncomeViewModel
import com.example.income_and_expenses_application.presentation.navigation.ExpenseDestination
import com.example.income_and_expenses_application.presentation.navigation.HomeDestination
import com.example.income_and_expenses_application.presentation.navigation.IncomeDestination
import com.example.income_and_expenses_application.presentation.navigation.IncomeExpenseDestination
import com.example.income_and_expenses_application.presentation.navigation.IncomeExpenseNavHost
import com.example.income_and_expenses_application.presentation.navigation.TransactionDestination
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
    //variable for all the screens the user navigates to and use in my bottom navigation bar
    //Transaction screen destination is going to be via the floating action button
    private val allScreens = listOf(IncomeDestination,HomeDestination,ExpenseDestination)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

        }
    }

    //Going to add another composable, which will help to separate the concerns

    @Composable
    private fun IncExpApp(){
        Income_and_Expenses_ApplicationTheme {
            val homeViewModel: HomeViewModel = viewModel()
            val expenseViewModel: ExpenseViewModel = viewModel()
            val incomeViewModel: IncomeViewModel = viewModel()
            val navHostController = rememberNavController()

            //A state to help track the theme
            val systemTheme = isSystemInDarkTheme()

            var currentTheme by remember {
                mutableStateOf(
                    if (systemTheme) Theme.SYSTEM else Theme.LIGHT
                )
            }
            CompositionLocalProvider(LocalAppTheme provides currentTheme){
                Income_and_Expenses_ApplicationTheme(
                    currentTheme == Theme.DARK
                ) {
                    //Give the current backstack entry and access to the current screen
                    var currentScreen = rememberCurrentScreen(navHostController)
                    //Icons used inside the screens
                    val icon = when(currentTheme){
                        Theme.DARK -> R.drawable.ic_switch_on
                        else -> R.drawable.ic_switch_off
                    }
                    //Surface container for the background colour from the colorScheme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Scaffold(
                            topBar = {
                                IncomeExpenseAppBar(
                                    onSwitchClick ={
                                     currentTheme = when(currentTheme){
                                            Theme.DARK -> Theme.LIGHT
                                            else -> Theme.DARK
                                        }
                                    },
                                    icon = icon,
                                    title = currentScreen.pageTitle,

                                ){
                                    navHostController.navigateUp()
                                }
                            },

                            bottomBar = {

                            }
                        ) { paddingValues ->
                            IncomeExpenseNavHost(
                                modifier = Modifier.padding(paddingValues).padding(16.dp),
                                navHostController = navHostController,
                                homeViewModel = homeViewModel,
                                incomeViewModel = incomeViewModel,
                                expenseViewModel = expenseViewModel,
                                assistedFactory = assistedFactory
                            )
                        }
                    }
                }
            }
        }
    }


    @Composable
    private fun rememberCurrentScreen(navController: NavController):IncomeExpenseDestination{
        //Track the state when moving from one screen to the next
        //A mutable state of the current back stack entry (observable)
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination
        //get the current screen
        //If there is no match the Transaction destination screen will be the default one
        return allScreens.find {
             it.routePath == currentDestination?.route
        } ?:TransactionDestination
    }
    /*If there is no theme show an error because application should not operate
     without a theme */
    private val LocalAppTheme = staticCompositionLocalOf<Theme> {
        error("No Theme Provided")
    }

    //For the themes
    //This will help propagate the type of colours I want to use
    enum class Theme{
        SYSTEM,
        LIGHT,
        DARK
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