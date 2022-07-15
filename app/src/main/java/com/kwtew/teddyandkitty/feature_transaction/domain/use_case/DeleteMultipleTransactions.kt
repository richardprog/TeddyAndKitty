package com.kwtew.teddyandkitty.feature_transaction.domain.use_case

import com.kwtew.teddyandkitty.feature_transaction.domain.model.Transaction
import com.kwtew.teddyandkitty.feature_transaction.domain.repository.TransactionRepository

class DeleteMultipleTransactions(
    private val repository: TransactionRepository
) {
    suspend fun execute(transactions: List<Transaction>) {
        repository.deleteMultipleTransactions(transactions)
    }
}