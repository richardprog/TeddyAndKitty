package com.kwtew.teddyandkitty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kwtew.teddyandkitty.feature_transaction.presentation.main_menu.mainMenuNavGraph
import com.kwtew.teddyandkitty.feature_transaction.presentation.util.Screen
import com.kwtew.teddyandkitty.ui.theme.TeddyAndKittyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeddyAndKittyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val scaffoldState = rememberScaffoldState()
                    val scope = rememberCoroutineScope()

                    Scaffold(
                        scaffoldState = scaffoldState
                    ) { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            contentAlignment = Alignment.Center
                        ) {
                            val navController = rememberNavController()
                            NavHost(
                                navController = navController,
                                startDestination = Screen.MainMenuScreen.route
                            ) {
                                mainMenuNavGraph(
                                    navController = navController,
                                    onShowSnackbar = {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(it)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
