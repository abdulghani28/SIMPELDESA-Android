package com.cvindosistem.simpeldesa.auth.presentation.auth.resetpassword

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.AuthButton
import com.cvindosistem.simpeldesa.core.components.SmallText
import com.cvindosistem.simpeldesa.main.navigation.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OtpVerificationScreen(
    navController: NavController,
    email: String,
    viewModel: PasswordResetViewModel
) {
    val context = LocalContext.current

    // Set email in viewModel if not already set
    LaunchedEffect(email) {
        if (viewModel.email != email) {
            viewModel.onEmailChanged(email)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.passwordResetEvent.collectLatest { event ->
            when (event) {
                is PasswordResetViewModel.PasswordResetEvent.OtpValid -> {
                    navController.navigate("${Screen.ResetPassword.route}/${email}")
                }
                is PasswordResetViewModel.PasswordResetEvent.OtpSent -> {
                    Toast.makeText(context, "Kode OTP telah dikirim ulang", Toast.LENGTH_LONG).show()
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
                title = "Verifikasi Reset Password",
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                OtpVerificationHeader(email)

                Spacer(modifier = Modifier.height(48.dp))

                OtpInputField(viewModel)

                Spacer(modifier = Modifier.height(32.dp))

                ResendOtpSection(viewModel)

                Spacer(modifier = Modifier.weight(1f))

                VerifyOtpButton(viewModel)

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun OtpVerificationHeader(email: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SmallText("Kode Verifikasi telah dikirim ke email")
        Spacer(modifier = Modifier.height(8.dp))
        SmallText(email)
    }
}

@Composable
private fun OtpInputField(viewModel: PasswordResetViewModel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(6) { index ->
            val digit = if (index < viewModel.otp.length) viewModel.otp[index].toString() else ""
            val isFilled = digit.isNotEmpty()
            val lineColor = if (isFilled) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

            OutlinedTextField(
                value = digit,
                onValueChange = { newValue ->
                    if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                        val newOtp = viewModel.otp.toMutableList()

                        // Ensure the list is long enough
                        while (newOtp.size <= index) {
                            newOtp.add(' ')
                        }

                        if (newValue.isEmpty()) {
                            // Remove digit
                            if (index < newOtp.size) {
                                newOtp[index] = ' '
                            }
                        } else {
                            // Add digit
                            newOtp[index] = newValue[0]
                        }

                        // Clean up the OTP string
                        val cleanedOtp = newOtp.joinToString("").replace(" ", "")
                        viewModel.onOtpChanged(cleanedOtp)
                    }
                },
                modifier = Modifier
                    .size(width = 50.dp, height = 60.dp)
                    .drawBehind {
                        // Gambar garis di bawah
                        drawLine(
                            color = lineColor,
                            start = Offset(0f, size.height - 4.dp.toPx()),
                            end = Offset(size.width, size.height - 4.dp.toPx()),
                            strokeWidth = 2.dp.toPx()
                        )
                    },
                singleLine = true,
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = if (index == 5) ImeAction.Done else ImeAction.Next
                )
            )
        }
    }
}

@Composable
private fun ResendOtpSection(viewModel: PasswordResetViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.countdownTime > 0) {
            SmallText("Belum menerima kode? Mohon tunggu ${viewModel.countdownTime} detik")
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                SmallText("Belum menerima kode? ")
                TextButton(
                    onClick = { viewModel.requestOtp() },
                    enabled = viewModel.canResendOtp() && !viewModel.isLoading,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "KIRIM ULANG",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun VerifyOtpButton(viewModel: PasswordResetViewModel) {
    AuthButton(
        onClick = { viewModel.validateOtp() },
        isLoading = viewModel.isLoading,
        enabled = !viewModel.isLoading,
        text = "Verifikasi"
    )
}