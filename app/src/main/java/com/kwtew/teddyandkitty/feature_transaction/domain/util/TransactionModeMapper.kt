package com.kwtew.teddyandkitty.feature_transaction.domain.util

object TransactionModeMapper {
    fun toTransactionMode(mode: String): TransactionMode {
        return when (mode) {
            "RT" -> TransactionMode.PrimaryToThirdParty
            "WT" -> TransactionMode.SecondaryToThirdParty
            "WR" -> TransactionMode.SecondaryToPrimary
            "RW" -> TransactionMode.PrimaryToSecondary
            "TR" -> TransactionMode.ThirdPartyToPrimary
            "TW" -> TransactionMode.ThirdPartyToSecondary
            else -> null
        }!!
    }
}