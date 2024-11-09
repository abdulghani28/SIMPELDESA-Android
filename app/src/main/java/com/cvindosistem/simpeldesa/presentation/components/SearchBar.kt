package com.cvindosistem.simpeldesa.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.ui.theme.FilterButtonBackground
import com.cvindosistem.simpeldesa.ui.theme.FilterButtonIcon
import com.cvindosistem.simpeldesa.ui.theme.SearchBarBackground
import com.cvindosistem.simpeldesa.ui.theme.SearchBarBorder
import com.cvindosistem.simpeldesa.ui.theme.SearchBarPlaceholder

@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .background(SearchBarBackground, shape = RoundedCornerShape(8.dp))
                .border(1.dp, SearchBarBorder, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                singleLine = true,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)  // Internal padding for text
                    .fillMaxSize(),
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Black,  // Adjust as necessary
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                ),
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Box(modifier = Modifier.weight(1f)) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    "Cari Keyword",
                                    color = SearchBarPlaceholder,
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp)
                                )
                            }
                            innerTextField()
                        }
                    }
                }
            )
        }

        FilledIconButton(
            onClick = { /* TODO: Show filter sheet */ },
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(8.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = FilterButtonBackground
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "Filter",
                tint = Color.Unspecified,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}