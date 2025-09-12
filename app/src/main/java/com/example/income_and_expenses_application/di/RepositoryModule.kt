package com.example.income_and_expenses_application.di

import com.example.income_and_expenses_application.data.repository.Repository
import com.example.income_and_expenses_application.data.repository.RepositoryImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(
        repositoryImplementation: RepositoryImplementation
    ):Repository
}
/*binding the repository to its implementation with @Binds with an abstract method
in an abstract class* -important for when the repository is used in the viewModel*/