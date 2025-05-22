package com.cvindosistem.simpeldesa.presentation.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.presentation.navigation.Screen
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
            .background(Color(0xFFF5F7FA))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            LoginHeader()

            Spacer(modifier = Modifier.height(40.dp))

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

@Composable
fun LoginHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VillageIllustration()

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Selamat Datang!",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Silakan masukkan Email dan Password yang sudah\nterdaftar untuk mengakses aplikasi Digital Desa",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        )
    }
}

@Composable
fun VillageIllustration() {
    Box(
        modifier = Modifier
            .size(200.dp, 120.dp)
    ) {

    }
}

@Composable
fun LoginForm(
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

        Spacer(modifier = Modifier.height(24.dp))

        LoginButton(
            onClick = onLogin,
            isLoading = loginState is LoginViewModel.LoginState.Loading
        )

        Spacer(modifier = Modifier.height(16.dp))

        OrDivider()
    }
}

@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false
) {
    Column {
        Text(
            text = "Email / Nomor Telepon",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFF374151),
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = "example@email.com",
                    color = Color(0xFF9CA3AF)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF3B82F6),
                unfocusedBorderColor = if (isError) Color(0xFFDC2626) else Color(0xFFD1D5DB),
                errorBorderColor = Color(0xFFDC2626),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@Composable
fun PasswordTextField(
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
                color = Color(0xFF374151),
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
                        painter = painterResource(if (isPasswordVisible) R.drawable.ic_eye else R.drawable.ic_eye_closed),
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                        tint = Color(0xFF6B7280)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF3B82F6),
                unfocusedBorderColor = if (isError) Color(0xFFDC2626) else Color(0xFFD1D5DB),
                errorBorderColor = Color(0xFFDC2626),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
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
fun LoginButton(
    onClick: () -> Unit,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4338CA)
        ),
        shape = RoundedCornerShape(8.dp),
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
                    tint = Color.White,
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
fun OrDivider() {
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

@Composable
fun SocialLoginSection() {
    Column {
        SocialLoginButton(
            text = "Masuk dengan Google",
            icon = R.drawable.ic_google,
            backgroundColor = Color.White,
            textColor = Color(0xFF374151),
            onClick = { /* Handle Google login */ }
        )

        Spacer(modifier = Modifier.height(12.dp))

        SocialLoginButton(
            text = "Masuk dengan Facebook",
            icon = R.drawable.ic_facebook,
            backgroundColor = Color.White,
            textColor = Color(0xFF374151),
            onClick = { /* Handle Facebook login */ }
        )
    }
}

@Composable
fun SocialLoginButton(
    text: String,
    icon: Int,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = backgroundColor
        ),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun RegisterLink() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Belum memiliki akun?",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFF6B7280)
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        TextButton(
            onClick = { /* Handle register navigation */ },
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "DAFTAR SEKARANG",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF3B82F6),
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}