package com.kwtew.teddyandkitty.feature_transaction.domain.use_case

data class UseCaseHub(
    val createNewTransaction: CreateNewTransaction,
    val viewTransactionLogReport: ViewTransactionLogReport,
    val deleteOneTransaction: DeleteOneTransaction,
    val deleteMultipleTransactions: DeleteMultipleTransactions,
    val viewAllDetailedTransactions: ViewAllDetailedTransactions,
    val getSingleTransaction: GetSingleTransaction,
    val validateAmount: ValidateAmount,
    val checkIfAtLeastOneDetail: CheckIfAtLeastOneDetail
)
