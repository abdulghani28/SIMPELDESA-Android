package com.cvindosistem.simpeldesa.presentation.screens.auth.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.presentation.components.LargeText
import com.cvindosistem.simpeldesa.presentation.components.SmallText

@Composable
internal fun LoginHeader() {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        VillageIllustration()

        Spacer(modifier = Modifier.height(24.dp))

        LargeText(
            text = "Selamat Datang!"
        )

        Spacer(modifier = Modifier.height(8.dp))

        SmallText(
            text = "Silakan masukkan Email dan Password yang sudah\nterdaftar untuk mengakses aplikasi Digital Desa"
        )
    }
}

@Composable
private fun VillageIllustration() {
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_login),
            contentDescription = "Illustration of a village",
            modifier = Modifier.fillMaxSize()
        )
    }
}
