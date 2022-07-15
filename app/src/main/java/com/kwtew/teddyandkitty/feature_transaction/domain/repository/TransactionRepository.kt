package com.kwtew.teddyandkitty.feature_transaction.domain.repository

import com.kwtew.teddyandkitty.feature_transaction.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun insertTransaction(transaction: Transaction)

    fun getTransactions() : Flow<List<Transaction>>

    suspend fun deleteTransaction(transaction: Transaction)

    suspend fun deleteMultipleTransactions(transactions: List<Transaction>)

    suspend fun getTransaction(id: Int) : Transaction?
}