package com.cvindosistem.simpeldesa.auth.presentation.auth.login.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.auth.presentation.auth.login.AuthViewModel
import com.cvindosistem.simpeldesa.core.components.AppPasswordField
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.AuthButton
import com.cvindosistem.simpeldesa.core.components.ClickableText

@Composable
internal fun LoginForm(
    viewModel: AuthViewModel,
    passwordVisible: Boolean,
    onPasswordToggle: () -> Unit
) {
    Column {
        EmailTextField(
            value = viewModel.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            isError = viewModel.emailError != null,
            errorMessage = viewModel.emailError
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            value = viewModel.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            isPasswordVisible = passwordVisible,
            onPasswordVisibilityChange = onPasswordToggle,
            isError = viewModel.passwordError != null,
            errorMessage = viewModel.passwordError
        )

        // General login error (from server response)
        if (viewModel.loginError != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = viewModel.loginError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ClickableText(
                onClick = {},
                text = "LUPA PASSWORD"
            )
        }

        AuthButton(
            onClick = { viewModel.login() },
            isLoading = viewModel.isLoading,
            enabled = viewModel.isFormValid() && !viewModel.isLoading,
            text = "Masuk Akun",
            icon = R.drawable.ic_login
        )

        Spacer(modifier = Modifier.height(16.dp))

        OrDivider()
    }
}

@Composable
private fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    AppTextField(
        label = "Email / Nomor Telepon",
        placeholder = "example@email.com",
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        errorMessage = errorMessage,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
    )
}

@Composable
private fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    AppPasswordField(
        label = "Password",
        value = value,
        onValueChange = onValueChange,
        isPasswordVisible = isPasswordVisible,
        onPasswordVisibilityChange = onPasswordVisibilityChange,
        isError = isError,
        errorMessage = errorMessage,
    )
}

@Composable
private fun OrDivider() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
        )
        Text(
            text = "Atau",
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
            )
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
        )
    }
}