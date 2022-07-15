package com.kwtew.teddyandkitty.feature_transaction.data.repository

import com.kwtew.teddyandkitty.feature_transaction.data.data_source.TransactionDao
import com.kwtew.teddyandkitty.feature_transaction.domain.model.Transaction
import com.kwtew.teddyandkitty.feature_transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl(
    private val dao: TransactionDao
) : TransactionRepository {
    override suspend fun insertTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction)
    }

    override fun getTransactions(): Flow<List<Transaction>> {
        return dao.getTransactions()
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        dao.deleteTransaction(transaction)
    }

    override suspend fun deleteMultipleTransactions(transactions: List<Transaction>) {
        dao.deleteMultipleTransactions(transactions)
    }

    override suspend fun getTransaction(id: Int): Transaction? {
        return dao.getTransaction(id)
    }
}