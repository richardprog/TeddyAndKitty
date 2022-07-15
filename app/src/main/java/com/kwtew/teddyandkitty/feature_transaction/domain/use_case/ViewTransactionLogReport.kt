package com.kwtew.teddyandkitty.feature_transaction.domain.use_case

import com.kwtew.teddyandkitty.feature_transaction.domain.model.Transaction
import com.kwtew.teddyandkitty.feature_transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class ViewTransactionLogReport(
    private val repository: TransactionRepository
) {
    fun execute() : Flow<List<Transaction>> {
        return repository.getTransactions()
    }
}