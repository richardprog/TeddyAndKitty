package com.kwtew.teddyandkitty.feature_transaction.presentation.add_edit_transaction

sealed class AddEditTransactionEvent {
    data class OnEnterTransactionDate(val text: String): AddEditTransactionEvent()
    data class OnEnterVenue(val text: String): AddEditTransactionEvent()
    data class OnEnterProduct(val text: String): AddEditTransactionEvent()
    data class OnEnterThirdPartyName(val text: String): AddEditTransactionEvent()
    data class OnEnterAmount(val text: String): AddEditTransactionEvent()
    data class OnEnterRemark(val text: String): AddEditTransactionEvent()
    object OnClickDatePickerButton: AddEditTransactionEvent()
    object OnClickCalculateAmount: AddEditTransactionEvent()
    object OnClickSave: AddEditTransactionEvent()
    object OnDone: AddEditTransactionEvent()
}
