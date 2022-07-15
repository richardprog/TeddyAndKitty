package com.kwtew.teddyandkitty.feature_transaction.presentation.util

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    data class ShowDatePicker(val timestamp: Long) : UiEvent()
    object NavigateUp : UiEvent()
}
