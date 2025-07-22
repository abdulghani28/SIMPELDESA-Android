package com.cvindosistem.simpeldesa.core.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun AppOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
            )
        },
        modifier = modifier.fillMaxWidth(),
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isError) Color(0xFFF1706A) else Color(0xFFDCE2FB),
            unfocusedBorderColor = if (isError) Color(0xFFF1706A) else Color(0xFFDCE2FB),
            errorBorderColor = Color(0xFFF1706A),
            focusedContainerColor = Color(0xFFF8F7FD),
            unfocusedContainerColor = Color(0xFFF8F7FD)
        ),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation
    )
}

@Composable
fun LabelFieldText(
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
            fontWeight = FontWeight.Medium
        ),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    isError: Boolean,
    placeholder: String = "Kata Sandi",
    imeAction: ImeAction = ImeAction.Done
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
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
            focusedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.surface,
            unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.surface,
            errorBorderColor = MaterialTheme.colorScheme.error,
            focusedContainerColor = Color(0xFFF8F7FD),
            unfocusedContainerColor = Color(0xFFF8F7FD)
        ),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { /* Handle login when done is pressed */ }
        )
    )
}

@Composable
fun AppPasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    isError: Boolean,
    errorMessage: String?,
    placeholder: String = "Kata Sandi",
    imeAction: ImeAction = ImeAction.Done
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
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun AppTextField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailingIcon: @Composable (() -> Unit)? = {}
) {
    Column {
        LabelFieldText(label)

        AppOutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            isError = isError,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            trailingIcon = trailingIcon
        )

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun AppNumberField(
    label: String,
    placeholder: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    isError: Boolean,
    errorMessage: String?,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Number
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    var textValue by remember(value) { mutableStateOf(if (value == 0) "" else value.toString()) }

    Column {
        LabelFieldText(label)

        AppOutlinedTextField(
            value = textValue,
            onValueChange = { input ->
                if (input.all { it.isDigit() } || input.isEmpty()) {
                    textValue = input
                    onValueChange(input.toIntOrNull() ?: 0)
                }
            },
            placeholder = placeholder,
            isError = isError,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )

        if (isError && !errorMessage.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    options: List<String>,
    isError: Boolean,
    errorMessage: String?,
    onDropdownExpanded: () -> Unit = {}
) {
    Column {
        if (label != "") {
            LabelFieldText(label)
        }

        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                onDropdownExpanded()
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = value,
                placeholder = { PlaceholderTitleSmallText("Pilih Opsi") },
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFDCE2FB),
                    unfocusedBorderColor = Color(0xFFDCE2FB),
                    focusedContainerColor = Color(0xFFF8F7FD),
                    unfocusedContainerColor = Color(0xFFF8F7FD)
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.heightIn(max = 200.dp)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(
                            text = option,
                            color = MaterialTheme.colorScheme.onBackground
                        ) },
                        onClick = {
                            onValueChange(option)
                            expanded = false
                        }
                    )
                }
            }
        }

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun MultilineTextField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?
) {
    Column {
        LabelFieldText(label)

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) Color(0xFFF1706A) else Color(0xFFDCE2FB),
                unfocusedBorderColor = if (isError) Color(0xFFF1706A) else Color(0xFFDCE2FB),
                errorBorderColor = Color(0xFFF1706A),
                focusedContainerColor = Color(0xFFF8F7FD),
                unfocusedContainerColor = Color(0xFFF8F7FD)
            ),
            shape = RoundedCornerShape(8.dp),
            maxLines = 3
        )

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold
        ),
        color = MaterialTheme.colorScheme.onBackground
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?
) {
    Column {
        LabelFieldText(label)

        var showDatePicker by remember { mutableStateOf(false) }
        val datePickerState = rememberDatePickerState()

        OutlinedTextField(
            value = value,
            onValueChange = {},
            enabled = false,
            placeholder = {
                Text(
                    text = "YYYY-MM-DD",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                )
            },
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date",
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFDCE2FB),
                unfocusedBorderColor = Color(0xFFDCE2FB),
                disabledBorderColor = Color(0xFFDCE2FB),
                focusedContainerColor = Color(0xFFF8F7FD),
                unfocusedContainerColor = Color(0xFFF8F7FD),
                disabledContainerColor = Color(0xFFF8F7FD),
                disabledTextColor = MaterialTheme.colorScheme.onBackground,
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true }
        )

        if (showDatePicker) {
            AppDatePickerDialog(
                datePickerState = datePickerState,
                onDateSelected = { selectedDateMillis ->
                    selectedDateMillis?.let {
                        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val formattedDate = formatter.format(Date(it))
                        onValueChange(formattedDate)
                    }
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
        }

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePickerDialog(
    datePickerState: DatePickerState,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { onDateSelected(datePickerState.selectedDateMillis) }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false
        )
    }
}

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?
) {
    Column {
        LabelFieldText(label)
        var showTimePicker by remember { mutableStateOf(false) }
        val timePickerState = rememberTimePickerState()

        OutlinedTextField(
            value = value,
            onValueChange = {},
            enabled = false,
            placeholder = {
                Text(
                    text = "HH:MM",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                )
            },
            trailingIcon = {
                IconButton(onClick = { showTimePicker = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select time",
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFDCE2FB),
                unfocusedBorderColor = Color(0xFFDCE2FB),
                disabledBorderColor = Color(0xFFDCE2FB),
                focusedContainerColor = Color(0xFFF8F7FD),
                unfocusedContainerColor = Color(0xFFF8F7FD),
                disabledContainerColor = Color(0xFFF8F7FD),
                disabledTextColor = MaterialTheme.colorScheme.onBackground,
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showTimePicker = true }
        )

        if (showTimePicker) {
            AppTimePickerDialog(
                timePickerState = timePickerState,
                onTimeSelected = { hour, minute ->
                    val formattedTime = String.format("%02d:%02d", hour, minute)
                    onValueChange(formattedTime)
                    showTimePicker = false
                },
                onDismiss = { showTimePicker = false }
            )
        }

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTimePickerDialog(
    timePickerState: TimePickerState,
    onTimeSelected: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onTimeSelected(timePickerState.hour, timePickerState.minute)
                }
            ) {
                Text("OK")
            }
        },
        text = {
            TimePicker(
                state = timePickerState
            )
        }
    )
}

@Composable
fun PhotoUploadField(
    label: String,
    onPhotoSelected: () -> Unit,
    isError: Boolean,
    errorMessage: String?
) {
    Column {
        LabelFieldText(label)

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Add, // Using default Material icon
                        contentDescription = "Upload",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Upload Foto",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable { onPhotoSelected() },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) Color(0xFFF1706A) else Color(0xFFDCE2FB),
                unfocusedBorderColor = if (isError) Color(0xFFF1706A) else Color(0xFFDCE2FB),
                errorBorderColor = Color(0xFFF1706A),
                focusedContainerColor = Color(0xFFF8F7FD),
                unfocusedContainerColor = Color(0xFFF8F7FD)
            ),
            shape = RoundedCornerShape(8.dp)
        )

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
