package com.kwtew.teddyandkitty.feature_transaction.domain.use_case

import com.kwtew.teddyandkitty.R
import com.kwtew.teddyandkitty.UiText

class ValidateAmount {
    fun execute(amount: String): ValidationResult {
        if(amount.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.add_edit_transaction_amount_blank_error)
            )
        }
        if(amount.toDoubleOrNull() == null) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.add_edit_transaction_amount_double_error)
            )
        }
        if(amount.toDouble() <= 0.0) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.add_edit_transaction_amount_negative_error)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}