package com.kwtew.teddyandkitty.feature_transaction.presentation.main_menu

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kwtew.teddyandkitty.feature_transaction.presentation.add_edit_transaction.AddEditTransactionScreen
import com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_log.TransactionLogScreen
import com.kwtew.teddyandkitty.feature_transaction.presentation.transaction_maint.TransactionMaintScreen
import com.kwtew.teddyandkitty.feature_transaction.presentation.util.Screen

fun NavGraphBuilder.mainMenuNavGraph(
    navController: NavController,
    onShowSnackbar: (String) -> Unit,
) {
    composable(route = Screen.MainMenuScreen.route) {
        MainMenuScreen(navController = navController)
    }
    composable(
        route = Screen.AddTransactionScreen().route + "?mode={mode}&trx_id={trx_id}",
        arguments = listOf(
            navArgument(
                name = "mode"
            ) {
                type = NavType.StringType
            },
            navArgument(
                name = "trx_id"
            ) {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    ) {
        AddEditTransactionScreen(
            navController = navController,
            onShowSnackbar = onShowSnackbar
        )
    }
    composable(route = Screen.TransactionLogScreen.route) {
        TransactionLogScreen(navController = navController)
    }
    composable(route = Screen.TransactionMaintScreen.route) {
        TransactionMaintScreen(navController = navController, onShowSnackbar = onShowSnackbar)
    }
}