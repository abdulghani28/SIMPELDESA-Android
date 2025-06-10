package com.cvindosistem.simpeldesa.core.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    showBackButton: Boolean = false,
    showAdd: Boolean = false,
    showEdit: Boolean = false,
    showInfo: Boolean = false,
    showList: Boolean = false,
    showDelete: Boolean = false,
    showMenu: Boolean = false,
    showCreate: Boolean = false,
    showSearch: Boolean = false,
    showNavigation: Boolean = false,
    showSort: Boolean = false,
    showShare: Boolean = false,
    showDownload: Boolean = false,
    showBookmark: Boolean = false,
    showRefresh: Boolean = false,
    showSettings: Boolean = false,
    onBackClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onListClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onInfoClick: () -> Unit = {},
    onCreateClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onNavigateClick: () -> Unit = {},
    onSortClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onDownloadClick: () -> Unit = {},
    onBookmarkClick: () -> Unit = {},
    onRefreshClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                if (showBackButton) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = Color.Unspecified
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        navigationIcon = {
            // Kosongkan navigationIcon karena kita sudah handle di dalam title
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground,
            subtitleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        actions = {
            // Action buttons dasar
            if (showAdd) {
                IconButton(onClick = onAddClick) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            }

            if (showCreate) {
                Button(
                    onClick = onCreateClick,
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Buat",
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }

            if (showSearch) {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            }

            if (showNavigation) {
                IconButton(onClick = onNavigateClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_navigation),
                        contentDescription = "Navigation",
                        tint = Color.Unspecified
                    )
                }
            }

            if (showSort) {
                IconButton(onClick = onSortClick) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Sort"
                    )
                }
            }

            if (showBookmark) {
                IconButton(onClick = onBookmarkClick) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Bookmark"
                    )
                }
            }

            if (showShare) {
                IconButton(onClick = onShareClick) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share"
                    )
                }
            }

            if (showDownload) {
                IconButton(onClick = onDownloadClick) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Download"
                    )
                }
            }

            if (showRefresh) {
                IconButton(onClick = onRefreshClick) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }
            }

            if (showSettings) {
                IconButton(onClick = onSettingsClick) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings"
                    )
                }
            }

            // Action buttons asli
            if (showList) {
                IconButton(onClick = onListClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = "List"
                    )
                }
            }

            if (showEdit) {
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }
            }

            if (showDelete) {
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
            }

            if (showInfo) {
                IconButton(onClick = onInfoClick) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info"
                    )
                }
            }

            if (showMenu) {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu"
                    )
                }
            }
        }
    )
}
