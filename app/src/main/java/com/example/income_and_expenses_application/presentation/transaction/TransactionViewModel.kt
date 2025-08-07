package com.example.income_and_expenses_application.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.income_and_expenses_application.data.repository.Repository
import com.example.income_and_expenses_application.presentation.navigation.IncomeDestination
import com.example.income_and_expenses_application.presentation.navigation.IncomeExpenseDestination
import com.example.income_and_expenses_application.util.Category
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import java.util.Date
import javax.inject.Inject

class TransactionViewModel @Inject constructor(
    private val repository: Repository,
    //assisted injection of transaction id at runtime
   @Assisted private val transactionId: Int,
   @Assisted private val transactionType:String
):ViewModel(),TransactionCallBack {
    /*Now create a factory and help the the ViewModel to be constructed so that Dagger-Hilt
      recognises the variables and does not complain it cannot create them at compile time.
      Variables are needed at runtime. id is not know when an application is open, but it will
      be when an item is clicked. I need to create a ViewModel factory that can be used.
    */
    override fun onTitleChange(newValue: String) {
        TODO("Not yet implemented")
    }

    override fun onAmountChange(newValue: String) {
        TODO("Not yet implemented")
    }

    override fun onDescriptionChange(newValue: String) {
        TODO("Not yet implemented")
    }

    override fun onTransactionTypeChange(newValue: String) {
        TODO("Not yet implemented")
    }

    override fun onDateChange(newValue: Long?) {
        TODO("Not yet implemented")
    }

    override fun onScreenTypeChange(newValue: IncomeExpenseDestination) {
        TODO("Not yet implemented")
    }

    override fun onOpenDialog(newValue: String) {
        TODO("Not yet implemented")
    }

    override fun addIncome() {
        TODO("Not yet implemented")
    }

    override fun addExpense() {
        TODO("Not yet implemented")
    }

    override fun getIncome(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getExpense(id: Int) {
        TODO("Not yet implemented")
    }

    override fun updateIncome() {
        TODO("Not yet implemented")
    }

    override fun updateExpense() {
        TODO("Not yet implemented")
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
    //I will be tracking the date
    val openDialog: Boolean = true,
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