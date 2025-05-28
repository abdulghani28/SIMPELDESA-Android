package com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.tab.infoapbdes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.tab.infoapbdes.section.APBDesItemCard
import com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.tab.infoapbdes.section.DistribusiAnggaranSection
import com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.tab.infoapbdes.section.PresentasiRealisasiSection

@Composable
internal fun InfoAPBDesContent(
    modifier: Modifier = Modifier
) {
    val apbDesData = getAPBDesData()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceBright)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Spacer(modifier = Modifier.height(0.1.dp))
        }

        // APBDes Items
        items(apbDesData) { item ->
            APBDesItemCard(apbDesItem = item)
        }

        // Distribusi Anggaran Chart
        item {
            DistribusiAnggaranSection(apbDesData = apbDesData)
        }

        // Presentasi Realisasi
        item {
            PresentasiRealisasiSection(apbDesData = apbDesData)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

private fun getAPBDesData(): List<APBDesItem> {
    return listOf(
        APBDesItem(
            id = "1",
            title = "Pendapatan",
            amount = "Rp 846,310,544",
            numericAmount = 846310544L,
            icon = Icons.Default.ShoppingCart,
            color = Color(0xFF00E396),
            realisasiPercentage = 100
        ),
        APBDesItem(
            id = "2",
            title = "Belanja",
            amount = "Rp 387,185,155",
            numericAmount = 387185155L,
            icon = Icons.Default.ShoppingCart,
            color = Color(0xFFFF4560),
            realisasiPercentage = 83
        ),
        APBDesItem(
            id = "3",
            title = "Pembiayaan",
            amount = "Rp 533,625,813",
            numericAmount = 533625813L,
            icon = Icons.Default.ShoppingCart,
            color = Color(0xFFFEB019),
            realisasiPercentage = 96
        )
    )
}

data class APBDesItem(
    val id: String,
    val title: String,
    val amount: String,
    val numericAmount: Long,
    val icon: ImageVector,
    val color: Color,
    val realisasiPercentage: Int
)