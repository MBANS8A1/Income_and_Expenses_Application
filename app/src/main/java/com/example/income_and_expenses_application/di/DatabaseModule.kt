package com.example.income_and_expenses_application.di

import android.content.Context
import androidx.room.Room
import com.example.income_and_expenses_application.data.local.IncomeExpenseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideIncomeExpenseDatabase(
        @ApplicationContext context: Context
    ): IncomeExpenseDatabase {
        return Room.databaseBuilder(
            context,
            IncomeExpenseDatabase::class.java,
            "transaction_db"
        )
//            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideExpenseDao(
        database: IncomeExpenseDatabase
    )= database.expenseDao



    @Provides
    @Singleton
    fun provideIncomeDao(
        database: IncomeExpenseDatabase
    )= database.incomeDao


}