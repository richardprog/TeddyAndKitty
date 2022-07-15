package com.kwtew.teddyandkitty.feature_transaction.domain.use_case

import com.kwtew.teddyandkitty.R
import com.kwtew.teddyandkitty.UiText

class CheckIfAtLeastOneDetail {
    fun execute(vararg details: String): ValidationResult {
        if (details.isEmpty() || !details.any { it.isNotBlank() }) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.add_edit_transaction_at_least_one_detail_error)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}