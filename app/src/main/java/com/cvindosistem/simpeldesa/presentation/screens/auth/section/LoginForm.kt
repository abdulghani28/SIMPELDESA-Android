package com.cvindosistem.simpeldesa.presentation.screens.auth.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.presentation.components.AppOutlinedTextField
import com.cvindosistem.simpeldesa.presentation.screens.auth.LoginViewModel

@Composable
internal fun LoginForm(
    email: String,
    password: String,
    isPasswordVisible: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityChange: () -> Unit,
    onLogin: () -> Unit,
    loginState: LoginViewModel.LoginState
) {
    Column {
        EmailTextField(
            value = email,
            onValueChange = onEmailChange,
            isError = loginState is LoginViewModel.LoginState.Error
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            isPasswordVisible = isPasswordVisible,
            onPasswordVisibilityChange = onPasswordVisibilityChange,
            isError = loginState is LoginViewModel.LoginState.Error
        )

        if (loginState is LoginViewModel.LoginState.Error) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Error Message",
                color = Color(0xFFDC2626),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = { /* Handle forgot password */ }
            ) {
                Text(
                    text = "LUPA PASSWORD",
                    color = Color(0xFF3B82F6),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }

        LoginButton(
            onClick = onLogin,
            isLoading = loginState is LoginViewModel.LoginState.Loading
        )

        Spacer(modifier = Modifier.height(16.dp))

        OrDivider()
    }
}

@Composable
private fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false
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
                    color = Color(0xFF9CA3AF)
                )
            },
            isError = isError
        )
    }
}

@Composable
private fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    isError: Boolean = false
) {
    Column {
        Text(
            text = "Password",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = "Kata Sandi",
                    color = Color(0xFF9CA3AF)
                )
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = onPasswordVisibilityChange) {
                    Icon(
                        painter = painterResource(if (!isPasswordVisible) R.drawable.ic_eye else R.drawable.ic_eye_closed),
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                        tint = Color.Unspecified
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFDCE2FB),
                unfocusedBorderColor = if (isError) Color(0xFFF1706A) else Color(0xFFDCE2FB),
                errorBorderColor = Color(0xFFF1706A),
                focusedContainerColor = Color(0xFFF8F7FD),
                unfocusedContainerColor = Color(0xFFF8F7FD)
            ),
            shape = RoundedCornerShape(8.dp)
        )

        if (isError) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Error Message",
                color = Color(0xFFDC2626),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun LoginButton(
    onClick: () -> Unit,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF292D8B)
        ),
        shape = RoundedCornerShape(48.dp),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(20.dp)
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_login),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Masuk Akun",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

@Composable
private fun OrDivider() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = Color(0xFFE5E7EB)
        )
        Text(
            text = "Atau",
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color(0xFF6B7280)
            )
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = Color(0xFFE5E7EB)
        )
    }
}