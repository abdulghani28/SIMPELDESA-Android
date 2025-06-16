package com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.notification

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.core.components.AnimatedTabContent
import com.cvindosistem.simpeldesa.core.components.AppTab
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.BodyMediumText
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.main.navigation.Screen
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.viewmodel.HomeViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.viewmodel.NotificationCategory
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.viewmodel.NotificationItem

@Composable
fun NotificationScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
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
                0 -> FiturLayananContent(
                    modifier = modifier,
                    notifications = homeViewModel.getNotificationsByCategory(NotificationCategory.FITUR_LAYANAN),
                    onNotificationClick = { notificationId ->
                        navController.navigate("${Screen.DetailSurat.route}/$notificationId")
                    }
                )
                1 -> PengumumanContent(
                    modifier = modifier,
                    notifications = homeViewModel.getNotificationsByCategory(NotificationCategory.PENGUMUMAN),
                    onNotificationClick = { notificationId ->
                        navController.navigate("${Screen.DetailSurat.route}/$notificationId")
                    }
                )
            }
        }
    }
}

@Composable
private fun FiturLayananContent(
    modifier: Modifier = Modifier,
    notifications: List<NotificationItem>,
    onNotificationClick: (String) -> Unit = {}
) {
    if (notifications.isEmpty()) {
        EmptyNotificationState(
            modifier = modifier,
            message = "Belum ada notifikasi fitur layanan"
        )
    } else {
        LazyColumn(
            modifier = modifier
        ) {
            itemsIndexed(notifications) { index, notification ->
                NotificationItemView(
                    notification = notification,
                    onItemClick = { onNotificationClick(notification.suratId) }
                )
            }
        }
    }
}

@Composable
private fun PengumumanContent(
    modifier: Modifier = Modifier,
    notifications: List<NotificationItem>,
    onNotificationClick: (String) -> Unit = {}
) {
    if (notifications.isEmpty()) {
        EmptyNotificationState(
            modifier = modifier,
            message = "Belum ada pengumuman"
        )
    } else {
        LazyColumn(
            modifier = modifier
        ) {
            itemsIndexed(notifications) { index, notification ->
                NotificationItemView(
                    notification = notification,
                    onItemClick = { onNotificationClick(notification.suratId) }
                )
            }
        }
    }
}

@Composable
private fun NotificationItemView(
    notification: NotificationItem,
    onItemClick: () -> Unit = {},
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
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
                    category = notification.category,
                )

                Spacer(modifier = Modifier.height(8.dp))

                NotificationContent(
                    title = notification.title,
                    message = notification.message,
                    timestamp = notification.timestamp,
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
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodySmallText(
            text = category,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun NotificationContent(
    title: String,
    message: String,
    timestamp: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        BodyMediumText(
            text = title,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface
        )

        if (message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = message,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        BodySmallText(
            text = timestamp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun EmptyNotificationState(
    modifier: Modifier = Modifier,
    message: String
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.height(16.dp))

            BodyMediumText(
                text = message,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}