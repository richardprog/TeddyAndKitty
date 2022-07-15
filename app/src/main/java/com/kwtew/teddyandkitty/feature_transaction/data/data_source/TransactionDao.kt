package com.kwtew.teddyandkitty.feature_transaction.data.data_source

import androidx.room.*
import com.kwtew.teddyandkitty.feature_transaction.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM `Transaction`")
    fun getTransactions() : Flow<List<Transaction>>

    @Query("SELECT * FROM `Transaction` WHERE id = :id")
    suspend fun getTransaction(id: Int) : Transaction?

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteMultipleTransactions(transactions: List<Transaction>)
}