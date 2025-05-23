package com.cvindosistem.simpeldesa.auth.presentation.auth.resetpassword

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.auth.presentation.auth.login.section.VillageIllustration
import com.cvindosistem.simpeldesa.core.components.AuthButton
import com.cvindosistem.simpeldesa.core.components.ErrorText
import com.cvindosistem.simpeldesa.core.components.LabelFieldText
import com.cvindosistem.simpeldesa.core.components.PasswordField
import com.cvindosistem.simpeldesa.main.navigation.Screen
import com.cvindosistem.simpeldesa.core.components.LargeText
import com.cvindosistem.simpeldesa.core.components.SmallText
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    email: String,
    viewModel: PasswordResetViewModel
) {
    val context = LocalContext.current
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // Set email in viewModel if not already set
    LaunchedEffect(email) {
        if (viewModel.email != email) {
            viewModel.onEmailChanged(email)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.passwordResetEvent.collectLatest { event ->
            when (event) {
                is PasswordResetViewModel.PasswordResetEvent.PasswordResetSuccess -> {
                    Toast.makeText(context, "Password berhasil diubah", Toast.LENGTH_LONG).show()
                    // Navigate back to login and clear the back stack
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
                is PasswordResetViewModel.PasswordResetEvent.Error -> {
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
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ResetPasswordHeader()

            Spacer(modifier = Modifier.height(16.dp))

            ResetPasswordForm(
                viewModel = viewModel,
                newPasswordVisible = newPasswordVisible,
                confirmPasswordVisible = confirmPasswordVisible,
                onNewPasswordToggle = { newPasswordVisible = !newPasswordVisible },
                onConfirmPasswordToggle = { confirmPasswordVisible = !confirmPasswordVisible },
                navController = navController
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun ResetPasswordHeader() {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        VillageIllustration()

        Spacer(modifier = Modifier.height(24.dp))

        LargeText(
            text = "Ganti Password"
        )

        Spacer(modifier = Modifier.height(8.dp))

        SmallText(
            text = "Masukkan password baru untuk akun Anda.\nPastikan password aman dan mudah diingat."
        )
    }
}

@Composable
private fun ResetPasswordForm(
    viewModel: PasswordResetViewModel,
    newPasswordVisible: Boolean,
    confirmPasswordVisible: Boolean,
    onNewPasswordToggle: () -> Unit,
    onConfirmPasswordToggle: () -> Unit,
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // New Password Field
        PasswordTextField(
            label = "Password Baru",
            placeholder = "Kata Sandi Baru",
            value = viewModel.newPassword,
            onValueChange = { viewModel.onNewPasswordChanged(it) },
            isPasswordVisible = newPasswordVisible,
            onPasswordVisibilityChange = onNewPasswordToggle,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password Field
        PasswordTextField(
            label = "Konfirmasi Password",
            placeholder = "Konfirmasi Kata Sandi",
            value = viewModel.confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChanged(it) },
            isPasswordVisible = confirmPasswordVisible,
            onPasswordVisibilityChange = onConfirmPasswordToggle,
            imeAction = ImeAction.Done
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password requirements
        SmallText("Password minimal 6 karakter")

        Spacer(modifier = Modifier.height(24.dp))

        ResetPasswordButton(viewModel)

        Spacer(modifier = Modifier.height(16.dp))

        BackToLoginText(navController)
    }
}

@Composable
private fun PasswordTextField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    imeAction: ImeAction = ImeAction.Done,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    Column {
        LabelFieldText(label)

        PasswordField(
            value = value,
            onValueChange = onValueChange,
            isPasswordVisible = isPasswordVisible,
            onPasswordVisibilityChange = onPasswordVisibilityChange,
            isError = isError,
            placeholder = placeholder,
            imeAction = imeAction
        )

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            ErrorText(errorMessage)
        }
    }
}

@Composable
private fun ResetPasswordButton(viewModel: PasswordResetViewModel) {
    AuthButton(
        onClick = { viewModel.resetPassword() },
        isLoading = viewModel.isLoading,
        enabled = !viewModel.isLoading,
        text = "Simpan Password"
    )
}

@Composable
private fun BackToLoginText(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Kembali ke Halaman Login",
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.clickable {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
        )
    }
}