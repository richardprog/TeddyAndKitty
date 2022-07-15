package com.kwtew.teddyandkitty.feature_transaction.domain.use_case

import com.kwtew.teddyandkitty.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)
