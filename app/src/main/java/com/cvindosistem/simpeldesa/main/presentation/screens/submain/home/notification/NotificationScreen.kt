package com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.notification

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.AnimatedTabContent
import com.cvindosistem.simpeldesa.core.components.AppTab
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.BodyMediumText
import com.cvindosistem.simpeldesa.core.components.BodySmallText

@Composable
fun NotificationScreen(
    navController: NavController
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Fitur Layanan", "Pengumuman")

    Scaffold(
        topBar = {
            Column {
                AppTopBar(
                    title = "Notifikasi",
                    showBackButton = true,
                    onBackClick = { navController.popBackStack() }
                )
                AppTab(
                    selectedTab = selectedTab,
                    tabs = tabs,
                    onTabSelected = { selectedTab = it }
                )
            }
        }
    ) { paddingValues ->
        AnimatedTabContent(
            selectedTab = selectedTab,
            paddingValues = paddingValues
        ) { tabIndex, modifier ->
            when (tabIndex) {
                0 -> FiturLayananContent(modifier)
                1 -> PengumumanContent(modifier)
            }
        }
    }
}

@Composable
private fun FiturLayananContent(
    modifier: Modifier = Modifier
) {
    val notifications = getFiturLayananNotifications()

    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(notifications) { index, notification ->
            NotificationItem(
                notification = notification,
            )
        }
    }
}

@Composable
private fun PengumumanContent(
    modifier: Modifier = Modifier
) {
    val notifications = getPengumumanNotifications()

    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(notifications) { index, notification ->
            NotificationItem(
                notification = notification,
            )
        }
    }
}

@Composable
private fun NotificationItem(
    notification: NotificationItem,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                NotificationHeader(
                    category = notification.category
                )

                Spacer(modifier = Modifier.height(8.dp))

                NotificationContent(
                    title = notification.title,
                    timestamp = notification.timestamp
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )
    }
}

@Composable
private fun NotificationHeader(
    category: String,
) {
    BodySmallText(category)
}

@Composable
private fun NotificationContent(
    title: String,
    timestamp: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        BodyMediumText(title)

        Spacer(modifier = Modifier.height(8.dp))

        BodySmallText(timestamp)
    }
}

// Data class untuk item notifikasi
data class NotificationItem(
    val id: String,
    val category: String,
    val title: String,
    val timestamp: String,
    val isRead: Boolean = false
)

// Function untuk mendapatkan data notifikasi fitur layanan
private fun getFiturLayananNotifications(): List<NotificationItem> {
    return listOf(
        NotificationItem(
            id = "1",
            category = "Layanan Persuratan",
            title = "Anda telah mengajukan Surat Keterangan Bepergian",
            timestamp = "04/12/2024 | 23:00 WIB"
        )
    )
}

// Function untuk mendapatkan data notifikasi pengumuman
private fun getPengumumanNotifications(): List<NotificationItem> {
    return listOf(
        NotificationItem(
            id = "2",
            category = "Lapor Pemdes",
            title = "Laporan Anda diterima pihak desa dan saat ini sedang diproses",
            timestamp = "04/12/2024 | 23:00 WIB"
        ),
        NotificationItem(
            id = "3",
            category = "Lapor Pemdes",
            title = "Laporan Anda diterima pihak desa dan saat ini sedang diproses",
            timestamp = "04/12/2024 | 23:00 WIB"
        ),
        NotificationItem(
            id = "4",
            category = "Lapor Pemdes",
            title = "Laporan Anda diterima pihak desa dan saat ini sedang diproses",
            timestamp = "04/12/2024 | 23:00 WIB"
        ),
        NotificationItem(
            id = "5",
            category = "Lapor Pemdes",
            title = "Laporan Anda diterima pihak desa dan saat ini sedang diproses",
            timestamp = "04/12/2024 | 23:00 WIB"
        )
    )
}