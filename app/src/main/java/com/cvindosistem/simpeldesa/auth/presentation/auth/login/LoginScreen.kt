package com.cvindosistem.simpeldesa.auth.presentation.auth.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.auth.presentation.auth.login.section.LoginForm
import com.cvindosistem.simpeldesa.auth.presentation.auth.login.section.LoginHeader
import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferences
import com.cvindosistem.simpeldesa.main.navigation.Screen
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = koinViewModel(),
    userPreferences: UserPreferences
) {
    val context = LocalContext.current
    var isPasswordVisible by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        if (userPreferences.isLoggedIn()) {
            navController.navigate(Screen.MainScreen.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loginEvent.collectLatest { event ->
            when (event) {
                is AuthViewModel.LoginEvent.Success -> {
                    Log.d("LoginScreen", "Login successful")
                    navController.navigate(Screen.MainScreen.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
                is AuthViewModel.LoginEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LoginHeader()

            Spacer(modifier = Modifier.height(16.dp))

            LoginForm(
                viewModel = viewModel,
                passwordVisible = isPasswordVisible,
                onPasswordToggle = { isPasswordVisible = !isPasswordVisible }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}