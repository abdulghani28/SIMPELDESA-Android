package com.cvindosistem.simpeldesa.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.presentation.navigation.Screen
import com.cvindosistem.simpeldesa.presentation.screens.auth.section.LoginForm
import com.cvindosistem.simpeldesa.presentation.screens.auth.section.LoginHeader
import com.cvindosistem.simpeldesa.presentation.screens.auth.section.RegisterLink
import com.cvindosistem.simpeldesa.presentation.screens.auth.section.SocialLoginSection
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val loginState by viewModel.loginState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginHeader()

            Spacer(modifier = Modifier.height(16.dp))

            LoginForm(
                email = email,
                password = password,
                isPasswordVisible = isPasswordVisible,
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onPasswordVisibilityChange = { isPasswordVisible = !isPasswordVisible },
                onLogin = { viewModel.login(email, password) },
                loginState = loginState
            )

            Spacer(modifier = Modifier.height(24.dp))

            SocialLoginSection()

            Spacer(modifier = Modifier.height(32.dp))

            RegisterLink()
        }

        when (loginState) {
            is LoginViewModel.LoginState.Success -> LaunchedEffect(Unit) {
                navController.navigate(Screen.Main.route)
            }
            else -> Unit
        }
    }
}