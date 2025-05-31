package com.cvindosistem.simpeldesa.main.presentation.screens.main.profile

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.BodyLargeText
import com.cvindosistem.simpeldesa.core.components.BodyMediumText
import com.cvindosistem.simpeldesa.core.components.ClickableText
import com.cvindosistem.simpeldesa.core.components.LargeText
import com.cvindosistem.simpeldesa.core.components.NavigationSectionTitle
import com.cvindosistem.simpeldesa.core.components.TitleLargeText
import com.cvindosistem.simpeldesa.core.components.TitleMediumText
import com.cvindosistem.simpeldesa.core.helpers.ImageLoader
import com.cvindosistem.simpeldesa.core.helpers.RemoteImage
import com.cvindosistem.simpeldesa.main.navigation.Screen
import com.cvindosistem.simpeldesa.main.presentation.screens.main.profile.viewmodel.ProfileUiState
import com.cvindosistem.simpeldesa.main.presentation.screens.main.profile.viewmodel.ProfileViewModel
import org.koin.compose.koinInject

@Composable
fun ProfilScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
    imageLoader: ImageLoader = koinInject()
) {
    var isExpanded by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Handle events
    LaunchedEffect(viewModel) {
        viewModel.profileEvent.collect { event ->
            when (event) {
                is ProfileViewModel.ProfileEvent.LogoutSuccess -> {
                    // Navigate to login screen or handle logout
                    navController.navigate(Screen.Login.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
                is ProfileViewModel.ProfileEvent.Error -> {
                    // Show error message
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                ProfileViewModel.ProfileEvent.DataLoaded -> {
                    // Data loaded successfully
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceBright),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Profile Header with Expandable Information
        item {
            ProfileHeaderWithExpandableInfo(
                userName = uiState.userName.ifEmpty { "Pengguna" },
                nik = uiState.nik.ifEmpty { "123xxxxxxxxxxxxx" },
                photo = uiState.foto?.ifEmpty { "" } ?: "",
                imageLoader = imageLoader,
                isExpanded = isExpanded,
                onExpandToggle = { isExpanded = !isExpanded },
                uiState = uiState
            )
        }

        // Village Information Card
        item {
            VillageInfoCard(
                village = uiState.dusun
            )
        }

        // Account Settings
        item {
            AccountSettingsSection(
                onLogoutClick = { viewModel.logout() }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Loading indicator
    if (viewModel.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun ProfileHeaderWithExpandableInfo(
    userName: String,
    photo: String,
    nik: String,
    imageLoader: ImageLoader,
    isExpanded: Boolean,
    onExpandToggle: () -> Unit,
    uiState: ProfileUiState
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Profile Image with Edit Icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                RemoteImage(
                    fileId = photo,
                    imageLoader = imageLoader,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Edit Icon
            IconButton(
                onClick = { /* Handle edit action */ },
                modifier = Modifier
                    .size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Name
        TitleLargeText(userName)

        Spacer(modifier = Modifier.height(8.dp))

        // NIK Label
        BodyMediumText("Nomor Induk Kependudukan (NIK)")

        Spacer(modifier = Modifier.height(4.dp))

        // NIK Value
        BodyLargeText(nik, FontWeight.Medium)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .clickable { onExpandToggle() }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isExpanded) "SEMBUNYIKAN INFORMASI" else "LIHAT INFORMASI LENGKAP",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }

        // Expandable Content
        AnimatedVisibility(
            visible = isExpanded,
            enter = slideInVertically() + expandVertically() + fadeIn(),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProfileInfoItem(
                    label = "Dusun",
                    value = uiState.dusun
                )

                ProfileInfoItem(
                    label = "RT",
                    value = uiState.rt
                )

                ProfileInfoItem(
                    label = "RW",
                    value = uiState.rw
                )

                ProfileInfoItem(
                    label = "Tempat & Tanggal Lahir",
                    value = uiState.tempatTanggalLahir
                )

                ProfileInfoItem(
                    label = "Jenis Kelamin",
                    value =  uiState.jenisKelamin
                )

                ProfileInfoItem(
                    label = "Agama",
                    value = uiState.agama.ifEmpty { "Islam" }
                )

                ProfileInfoItem(
                    label = "Pekerjaan",
                    value = uiState.pekerjaan.ifEmpty { "Karyawan Swasta" }
                )

                ProfileInfoItem(
                    label = "Alamat Lengkap",
                    value = uiState.alamatLengkap.ifEmpty { "Jl. Kangkung Lemas, Kec. Terong Belanda, Kab. Kebun Subur" }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun ProfileInfoItem(
    label: String,
    value: String
) {
    Column {
        BodyMediumText(label)
        Spacer(modifier = Modifier.height(4.dp))
        BodyLargeText(value, FontWeight.Medium)
    }
}

@Composable
private fun VillageInfoCard(
    village: String
) {
    AppCard(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .clickable {

            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                TitleMediumText(
                    village,
                    FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                ClickableText(
                    {},
                    "PINDAH DESA"
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Arrow Right",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AccountSettingsSection(
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 16.dp, horizontal = 32.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Pengaturan Akun",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )

//        SettingsItem(
//            icon = Icons.Default.Lock,
//            title = "Ubah Password",
//            iconTint = MaterialTheme.colorScheme.primary
//        )
//
//        SettingsItem(
//            icon = Icons.Default.Phone,
//            title = "Ubah Nomor Telepon",
//            iconTint = MaterialTheme.colorScheme.primary
//        )
//
//        SettingsItem(
//            icon = Icons.Default.Email,
//            title = "Ubah Alamat Email",
//            iconTint = MaterialTheme.colorScheme.primary
//        )

        Spacer(modifier = Modifier.height(8.dp))

        SettingsItem(
            icon = Icons.AutoMirrored.Filled.ExitToApp,
            title = "Keluar Akun",
            iconTint = MaterialTheme.colorScheme.error,
            textColor = MaterialTheme.colorScheme.onSurface,
            onClick = onLogoutClick
        )
    }
}

@Composable
private fun SettingsItem(
    icon: ImageVector,
    title: String,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            modifier = Modifier.weight(1f)
        )
    }
}