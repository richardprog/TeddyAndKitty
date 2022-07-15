package com.kwtew.teddyandkitty.feature_transaction.domain.util

import com.kwtew.teddyandkitty.R
import com.kwtew.teddyandkitty.feature_transaction.util.Constants.MODE_PT
import com.kwtew.teddyandkitty.feature_transaction.util.Constants.MODE_PS
import com.kwtew.teddyandkitty.feature_transaction.util.Constants.MODE_TP
import com.kwtew.teddyandkitty.feature_transaction.util.Constants.MODE_TS
import com.kwtew.teddyandkitty.feature_transaction.util.Constants.MODE_SP
import com.kwtew.teddyandkitty.feature_transaction.util.Constants.MODE_ST

sealed class TransactionMode(
    val mode: String,
    val resId: Int
) {
    object PrimaryToThirdParty : TransactionMode(MODE_PT, R.string.primary_to_third_party_title)
    object SecondaryToThirdParty : TransactionMode(MODE_ST, R.string.secondary_to_third_party_title)
    object SecondaryToPrimary : TransactionMode(MODE_SP, R.string.secondary_to_primary_title)
    object PrimaryToSecondary : TransactionMode(MODE_PS, R.string.primary_to_secondary_title)
    object ThirdPartyToPrimary : TransactionMode(MODE_TP, R.string.third_party_to_primary_title)
    object ThirdPartyToSecondary : TransactionMode(MODE_TS, R.string.third_party_to_secondary_title)
}