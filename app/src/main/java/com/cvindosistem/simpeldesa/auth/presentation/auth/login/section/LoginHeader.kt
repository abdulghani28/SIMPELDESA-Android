package com.cvindosistem.simpeldesa.auth.presentation.auth.login.section

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
import com.cvindosistem.simpeldesa.core.components.LargeText
import com.cvindosistem.simpeldesa.core.components.BodyMediumText

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

        BodyMediumText(
            text = "Silakan masukkan Email dan Password yang sudah\nterdaftar untuk mengakses aplikasi Digital Desa"
        )
    }
}

@Composable
fun VillageIllustration() {
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_image),
            contentDescription = "Illustration of a village",
            modifier = Modifier.fillMaxSize()
        )
    }
}
