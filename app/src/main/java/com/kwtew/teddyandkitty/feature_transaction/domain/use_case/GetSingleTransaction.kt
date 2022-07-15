package com.kwtew.teddyandkitty.feature_transaction.domain.use_case

import com.kwtew.teddyandkitty.feature_transaction.domain.model.Transaction
import com.kwtew.teddyandkitty.feature_transaction.domain.repository.TransactionRepository

class GetSingleTransaction(
    private val repository: TransactionRepository
) {
    suspend fun execute(id: Int): Transaction? {
        return repository.getTransaction(id)
    }
}