package com.kwtew.teddyandkitty

import android.app.Application
import androidx.room.Room
import com.kwtew.teddyandkitty.feature_transaction.data.repository.TransactionRepositoryImpl
import com.kwtew.teddyandkitty.feature_transaction.domain.repository.TransactionRepository
import com.kwtew.teddyandkitty.feature_transaction.domain.use_case.*
import com.kwtew.teddyandkitty.feature_transaction.domain.use_case.CheckIfAtLeastOneDetail
import com.kwtew.teddyandkitty.feature_transaction.domain.use_case.ValidateAmount
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMasterDatabase(app: Application): MasterDatabase {
        return Room.databaseBuilder(
            app,
            MasterDatabase::class.java,
            MasterDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(db: MasterDatabase): TransactionRepository {
        return TransactionRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: TransactionRepository): UseCaseHub {
        return UseCaseHub(
            createNewTransaction = CreateNewTransaction(repository),
            viewTransactionLogReport = ViewTransactionLogReport(repository),
            deleteOneTransaction = DeleteOneTransaction(repository),
            deleteMultipleTransactions = DeleteMultipleTransactions(repository),
            viewAllDetailedTransactions = ViewAllDetailedTransactions(repository),
            getSingleTransaction = GetSingleTransaction(repository),
            validateAmount = ValidateAmount(),
            checkIfAtLeastOneDetail = CheckIfAtLeastOneDetail()
        )
    }
}