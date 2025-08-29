package com.example.income_and_expenses_application.presentation.transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.income_and_expenses_application.data.local.models.Expense
import com.example.income_and_expenses_application.data.local.models.Income
import com.example.income_and_expenses_application.data.repository.Repository
import com.example.income_and_expenses_application.presentation.navigation.ExpenseDestination
import com.example.income_and_expenses_application.presentation.navigation.IncomeDestination
import com.example.income_and_expenses_application.presentation.navigation.IncomeExpenseDestination
import com.example.income_and_expenses_application.util.Category
import com.example.income_and_expenses_application.util.formatDate
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

class TransactionViewModel @AssistedInject constructor(
    private val repository: Repository,
    //assisted injection of transaction id at runtime
   @Assisted private val transactionId: Int,
   @Assisted private val transactionType:String
):ViewModel(),TransactionCallBack {
    var state by mutableStateOf(
        TransactionState()
    ) //state observable for TransactionState
      private set

    //get Income
    val income:Income
        get() = state.run { //TransactionState reference (the context object)
            Income(
                title = title,
                description = description,
                incomeAmount = amount.toDouble(),
                entryDate = formatDate(date),
                date = date,
                id = id
            )//receiver
        }
    val expense: Expense
        get() = state.run{//TransactionState reference (the context object)
            Expense(
                title = title,
                description = description,
                expenseAmount = amount.toDouble(),
                entryDate = formatDate(date),
                category = category.title,
                date =date,
                id = id
            )//receiver

        }
    override val areFieldsNotEmpty: Boolean
        get() = state.title.isNotEmpty() &&
                state.amount.isNotEmpty() &&
                state.description.isNotEmpty()
    /*Now create a factory and help the the ViewModel to be constructed so that Dagger-Hilt
      recognises the variables and does not complain it cannot create them at compile time.
      Variables are needed at runtime. id is not know when an application is open, but it will
      be when an item is clicked. I need to create a ViewModel factory that can be used.
    */

    //Events going towards the transaction screen
    override fun onTitleChange(newValue: String) {
        state = state.copy(
            title = newValue
        )
    }

    override fun onAmountChange(newValue: String) {
        state = state.copy(
            amount = newValue
        )
    }

    override fun onDescriptionChange(newValue: String) {
        state = state.copy(
            description = newValue
        )
    }

    override fun onTransactionTypeChange(newValue: String) {
        state = state.copy(
            title = newValue
        )
    }

    override fun onDateChange(newValue: Long?) {
        newValue?.let{
            state = state.copy(
                date = Date(it)
            )
        }
    }

    override fun onScreenTypeChange(newValue: IncomeExpenseDestination) {
        state = state.copy(
            transactionScreen = newValue
        )
    }

    override fun onOpenDialog(newValue: Boolean) {
        state = state.copy(
            openDialog = newValue
        )
    }

    override fun onCategoryChange(newValue: Category) {
        state = state.copy(
            category = newValue
        )
    }

    override fun addIncome() {
        viewModelScope.launch {
            repository.insertIncome(income)
        }
    }

    override fun addExpense() {
        viewModelScope.launch {
            repository.insertExpense(expense)
        }
    }

    override fun getIncome(id: Int) {
        viewModelScope.launch{
            repository.getIncomeById(id).collectLatest {
               state = state.copy(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    amount = it.incomeAmount.toString(),
                    transactionScreen = IncomeDestination,
                    date = it.date
                )
            }
        }
    }

    override fun getExpense(id: Int) {
        viewModelScope.launch{
            repository.getExpenseById(id).collectLatest {
              state =  state.copy(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    amount = it.expenseAmount.toString(),
                    transactionScreen = ExpenseDestination,
                    date = it.date,
                    category = Category.entries.toTypedArray()
                        .find{category->
                            category.title == it.category
                        } ?: Category.CLOTHING
                )
            }
        }
    }

    override fun updateIncome() {
        viewModelScope.launch{
            repository.updateIncome(income)
        }
    }

    override fun updateExpense() {
        viewModelScope.launch{
            repository.updateExpense(expense)
        }
    }


    init{
        if(transactionId != -1){
            //valid transaction id
            when(transactionType){
                IncomeDestination.routePath ->{
                    //update the state and get the Income
                    getIncome(id = transactionId)
                }
                ExpenseDestination.routePath->{
                    getExpense(id = transactionId)
                }
            }
            state = state.copy(
                isUpdatingTransaction = true
            )
        }else{
            state = state.copy(
                isUpdatingTransaction = false
            )
        }
    }
}

//class is going to help keep track of the transaction state
data class TransactionState(
    val id:Int  = 0,
    val title:String = "",
    val amount:String= "",
    val category: Category = Category.CLOTHING,
    val date: Date = Date(),
    //a transaction can be an Income or Expense (I want to vary between them in the edit screen)
    val description:String = "",
    val transactionScreen: IncomeExpenseDestination = IncomeDestination,
    //For opening a dialog
    var openDialog: Boolean = true,
    val isUpdatingTransaction:Boolean = false


)


class TransactionViewModelFactory(
    private val assistedFactory: TransactionAssistedFactory,
    private val id:Int,
    private val transactionType: String?
):ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        /*return an assisted injection factory; provide annotation so Dagger-Hilt knows to
        use this factory when I pass in an id and/or transactionType
        */
        return assistedFactory.create(id,transactionType) as T
    }
}

@AssistedFactory
interface TransactionAssistedFactory{
    //create function returns a ViewModel
    fun create(id:Int,transactionType: String?):TransactionViewModel
}