package com.example.income_and_expenses_application.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.income_and_expenses_application.data.repository.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import javax.inject.Inject

class TransactionViewModel @Inject constructor(
    private val repository: Repository,
    //assisted injection of transaction id at runtime
   @Assisted private val transactionId: Int,
   @Assisted private val transactionType:String
):ViewModel() {
    /*Now create a factory and help the the ViewModel to be constructed so that Dagger-Hilt
      recognises the variables and does not complain it cannot create them at compile time.
      Variables are needed at runtime. id is not know when an application is open, but it will
      be when an item is clicked. I need to create a ViewModel factory that can be used.
    */
}


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