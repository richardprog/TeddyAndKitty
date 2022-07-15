package com.kwtew.teddyandkitty.feature_transaction.domain.use_case

import com.kwtew.teddyandkitty.feature_transaction.domain.model.Transaction
import com.kwtew.teddyandkitty.feature_transaction.domain.repository.TransactionRepository

class DeleteOneTransaction(
    private val repository: TransactionRepository
) {
    suspend fun execute(transaction: Transaction) {
        repository.deleteTransaction(transaction)
    }
}