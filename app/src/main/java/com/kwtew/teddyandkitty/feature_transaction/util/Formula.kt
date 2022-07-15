package com.kwtew.teddyandkitty.feature_transaction.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kwtew.teddyandkitty.feature_transaction.domain.util.TransactionMode

object Formula {
    @Composable
    fun setComposableColorByAmount(
        amount: Double,
        defaultColor: Color
    ): Color {
        return if (amount > 0.00) {
            if (isSystemInDarkTheme()) {
                Color(0xff35cf00)
            } else {
                Color(0xff1a6301)
            }
        } else if (amount < 0.00) {
            Color.Red
        } else defaultColor
    }

    fun to2DecimalString(amount: Double): String {
        return "%.2f".format(amount)
    }

    fun toCurrencyString(amount: Double, withSign: Boolean = false): String {
        return "RM ${
            if (withSign) {
                if (amount > 0) "+" else ""
            } else { "" }
        }${to2DecimalString(amount)}"
    }

    fun getBalanceChanges(
        mode: TransactionMode,
        amount: Double,
        depositBalVar: (Double) -> Unit,
        primaryBalVar: (Double) -> Unit,
        secondaryBalVar: (Double) -> Unit
    ) {
        when (mode) {
            is TransactionMode.PrimaryToThirdParty -> {
                depositBalVar(-amount)
                primaryBalVar(0.0)
                secondaryBalVar(0.0)
            }
            is TransactionMode.SecondaryToThirdParty -> {
                depositBalVar(amount)
                primaryBalVar(-amount)
                secondaryBalVar(-amount)
            }
            is TransactionMode.SecondaryToPrimary -> {
                depositBalVar(amount * 2)
                primaryBalVar(-amount)
                secondaryBalVar(-amount)
            }
            is TransactionMode.PrimaryToSecondary -> {
                depositBalVar(amount * 2)
                primaryBalVar(-amount * 2)
                secondaryBalVar(0.0)
            }
            is TransactionMode.ThirdPartyToPrimary -> {
                depositBalVar(amount)
                primaryBalVar(0.0)
                secondaryBalVar(0.0)
            }
            is TransactionMode.ThirdPartyToSecondary -> {
                depositBalVar(-amount)
                primaryBalVar(amount)
                secondaryBalVar(amount)
            }
        }
    }
}