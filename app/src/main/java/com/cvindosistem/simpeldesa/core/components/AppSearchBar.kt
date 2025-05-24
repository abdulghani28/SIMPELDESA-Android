package com.cvindosistem.simpeldesa.core.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.R

@Composable
fun AppSearchBar(
    value: String,
    onValueSearch: (String) -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueSearch,
        modifier = modifier.fillMaxWidth(),
        placeholder = placeholder,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "Search",
                tint = Color.Unspecified
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFDCE2FB),
            unfocusedBorderColor = Color(0xFFDCE2FB),
            errorBorderColor = Color(0xFFF1706A),
            focusedContainerColor = Color(0xFFF8F7FD),
            unfocusedContainerColor = Color(0xFFF8F7FD)
        ),
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun FilterButton(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(56.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F7FD)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 0.5.dp,
            color = Color(0xFFDCE2FB)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_filter),
                contentDescription = "Filter",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}