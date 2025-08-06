package com.example.income_and_expenses_application.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.income_and_expenses_application.data.repository.Repository
import javax.inject.Inject

class TransactionViewModel @Inject constructor(
    private val repository: Repository,
    //assisted injection of transaction id at runtime
    private val transactionId: Int,
    private val transactionType:String
):ViewModel() {
    /*Now create a factory and help the the ViewModel to be constructed so that Dagger-Hilt
      recognises the variables and does not complain it cannot create them at compile time.
      Variables are needed at runtime. id is not know when an application is open, but it will
      be when an item is clicked. I need to create a ViewModel factory that can be used.
    */
}


class TransactionViewModelFactory(
    private val id:Int,
    private val transactionType: String?
):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return super.create(modelClass)
    }
}