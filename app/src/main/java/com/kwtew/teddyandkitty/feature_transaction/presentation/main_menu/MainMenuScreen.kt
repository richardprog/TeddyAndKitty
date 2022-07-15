package com.kwtew.teddyandkitty.feature_transaction.presentation.main_menu

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.kwtew.teddyandkitty.R
import com.kwtew.teddyandkitty.feature_transaction.domain.util.TransactionMode
import com.kwtew.teddyandkitty.feature_transaction.presentation.main_menu.components.ButtonEachOption
import com.kwtew.teddyandkitty.feature_transaction.presentation.util.Screen
import com.kwtew.teddyandkitty.ui.WindowInfo
import com.kwtew.teddyandkitty.ui.rememberWindowInfo
import com.kwtew.teddyandkitty.ui.spacing

@Composable
fun MainMenuScreen(
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Main Menu") },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val windowInfo = rememberWindowInfo()

            if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    ButtonEachOption(
                        text = stringResource(id = R.string.primary_to_third_party_title),
                        description = stringResource(id = R.string.primary_to_third_party_desc),
                        onClick = {
                            navController.navigate(
                                Screen.AddTransactionScreen(TransactionMode.PrimaryToThirdParty).route
                            )
                        }
                    )
                    ButtonEachOption(
                        text = stringResource(id = R.string.secondary_to_third_party_title),
                        description = stringResource(id = R.string.secondary_to_third_party_desc),
                        onClick = {
                            navController.navigate(
                                Screen.AddTransactionScreen(TransactionMode.SecondaryToThirdParty).route
                            )
                        }
                    )
                    ButtonEachOption(
                        text = stringResource(id = R.string.secondary_to_primary_title),
                        description = stringResource(id = R.string.secondary_to_primary_desc),
                        onClick = {
                            navController.navigate(
                                Screen.AddTransactionScreen(TransactionMode.SecondaryToPrimary).route
                            )
                        }
                    )
                    ButtonEachOption(
                        text = stringResource(id = R.string.primary_to_secondary_title),
                        description = stringResource(id = R.string.primary_to_secondary_desc),
                        onClick = {
                            navController.navigate(
                                Screen.AddTransactionScreen(TransactionMode.PrimaryToSecondary).route
                            )
                        }
                    )
                    ButtonEachOption(
                        text = stringResource(id = R.string.third_party_to_primary_title),
                        description = stringResource(id = R.string.third_party_to_primary_desc),
                        onClick = {
                            navController.navigate(
                                Screen.AddTransactionScreen(TransactionMode.ThirdPartyToPrimary).route
                            )
                        }
                    )
                    ButtonEachOption(
                        text = stringResource(id = R.string.third_party_to_secondary_title),
                        description = stringResource(id = R.string.third_party_to_secondary_desc),
                        onClick = {
                            navController.navigate(
                                Screen.AddTransactionScreen(TransactionMode.ThirdPartyToSecondary).route
                            )
                        }
                    )
                    ButtonEachOption(
                        text = stringResource(id = R.string.transaction_log_title),
                        description = stringResource(id = R.string.transaction_log_desc),
                        onClick = {
                            navController.navigate(
                                Screen.TransactionLogScreen.route
                            )
                        }
                    )
                    ButtonEachOption(
                        text = stringResource(id = R.string.transaction_maint_title),
                        description = stringResource(id = R.string.transaction_maint_desc),
                        onClick = {
                            navController.navigate(
                                Screen.TransactionMaintScreen.route
                            )
                        }
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.large))
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                    ) {
                        ButtonEachOption(
                            text = stringResource(id = R.string.primary_to_third_party_title),
                            description = stringResource(id = R.string.primary_to_third_party_desc),
                            onClick = {
                                navController.navigate(
                                    Screen.AddTransactionScreen(TransactionMode.PrimaryToThirdParty).route
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        ButtonEachOption(
                            text = stringResource(id = R.string.secondary_to_primary_title),
                            description = stringResource(id = R.string.secondary_to_primary_desc),
                            onClick = {
                                navController.navigate(
                                    Screen.AddTransactionScreen(TransactionMode.SecondaryToPrimary).route
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        ButtonEachOption(
                            text = stringResource(id = R.string.third_party_to_primary_title),
                            description = stringResource(id = R.string.third_party_to_primary_desc),
                            onClick = {
                                navController.navigate(
                                    Screen.AddTransactionScreen(TransactionMode.ThirdPartyToPrimary).route
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        ButtonEachOption(
                            text = stringResource(id = R.string.transaction_log_title),
                            description = stringResource(id = R.string.transaction_log_desc),
                            onClick = {
                                navController.navigate(
                                    Screen.TransactionLogScreen.route
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.large))
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                    ) {
                        ButtonEachOption(
                            text = stringResource(id = R.string.secondary_to_third_party_title),
                            description = stringResource(id = R.string.secondary_to_third_party_desc),
                            onClick = {
                                navController.navigate(
                                    Screen.AddTransactionScreen(TransactionMode.SecondaryToThirdParty).route
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        ButtonEachOption(
                            text = stringResource(id = R.string.primary_to_secondary_title),
                            description = stringResource(id = R.string.primary_to_secondary_desc),
                            onClick = {
                                navController.navigate(
                                    Screen.AddTransactionScreen(TransactionMode.PrimaryToSecondary).route
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        ButtonEachOption(
                            text = stringResource(id = R.string.third_party_to_secondary_title),
                            description = stringResource(id = R.string.third_party_to_secondary_desc),
                            onClick = {
                                navController.navigate(
                                    Screen.AddTransactionScreen(TransactionMode.ThirdPartyToSecondary).route
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        ButtonEachOption(
                            text = stringResource(id = R.string.transaction_maint_title),
                            description = stringResource(id = R.string.transaction_maint_desc),
                            onClick = {
                                navController.navigate(
                                    Screen.TransactionMaintScreen.route
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.large))
                }
            }
        }
    }

}