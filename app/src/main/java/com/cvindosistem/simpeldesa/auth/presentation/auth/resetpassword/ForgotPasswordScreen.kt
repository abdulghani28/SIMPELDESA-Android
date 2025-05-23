package com.cvindosistem.simpeldesa.auth.presentation.auth.resetpassword

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.AuthButton
import com.cvindosistem.simpeldesa.core.components.ErrorText
import com.cvindosistem.simpeldesa.main.navigation.Screen
import com.cvindosistem.simpeldesa.core.components.AppOutlinedTextField
import com.cvindosistem.simpeldesa.core.components.SmallText
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: PasswordResetViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.passwordResetEvent.collectLatest { event ->
            when (event) {
                is PasswordResetViewModel.PasswordResetEvent.OtpSent -> {
                    Toast.makeText(context, "Kode OTP telah dikirim ke email Anda", Toast.LENGTH_LONG).show()
                    navController.navigate("${Screen.OtpVerification.route}/${viewModel.email}")
                }
                is PasswordResetViewModel.PasswordResetEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Lupa Password",
                showBackButton = true,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                ForgotPasswordHeader()

                Spacer(modifier = Modifier.height(32.dp))

                ForgotPasswordForm(viewModel)

                Spacer(modifier = Modifier.weight(1f))

                RequestOtpButton(viewModel)

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun ForgotPasswordHeader() {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        SmallText(
            text = "Silakan masukkan Email atau Nomor Telepon Anda untuk mengirim kode verifikasi reset password"
        )
    }
}

@Composable
private fun ForgotPasswordForm(viewModel: PasswordResetViewModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        EmailTextField(
            value = viewModel.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            isError = false,
            errorMessage = null
        )
    }
}

@Composable
private fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    Column {
        Text(
            text = "Email / Nomor Telepon",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        AppOutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = "example@email.com",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                )
            },
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            ErrorText(errorMessage)
        }
    }
}

@Composable
private fun RequestOtpButton(viewModel: PasswordResetViewModel) {
    AuthButton(
        onClick = { viewModel.requestOtp() },
        isLoading = viewModel.isLoading,
        enabled = !viewModel.isLoading,
        text = "Lanjutkan"
    )
}