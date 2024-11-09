package com.cvindosistem.simpeldesa.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.ui.theme.TabBackgroundColor
import com.cvindosistem.simpeldesa.ui.theme.TabIndicatorColor
import com.cvindosistem.simpeldesa.ui.theme.TabSelectedTextColor
import com.cvindosistem.simpeldesa.ui.theme.TabUnselectedTextColor
import com.cvindosistem.simpeldesa.ui.theme.WorkSans

@Composable
fun ActivityTabLayout(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = TabSelectedTextColor,
        indicator = { tabPositions ->
            SecondaryIndicator(
                Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .height(2.dp),
                color = TabIndicatorColor
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = title,
                        fontFamily = WorkSans,
                        fontWeight = if (selectedTabIndex == index)
                            FontWeight.SemiBold else FontWeight.Normal,
                        color = if (selectedTabIndex == index)
                            TabSelectedTextColor else TabUnselectedTextColor
                    )
                }
            )
        }
    }
}
