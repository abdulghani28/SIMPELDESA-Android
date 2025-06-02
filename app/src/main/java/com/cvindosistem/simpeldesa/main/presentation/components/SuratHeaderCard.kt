package com.cvindosistem.simpeldesa.main.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.BodyMediumText
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.core.components.TitleMediumText
import com.cvindosistem.simpeldesa.core.helpers.formatDateTime
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail

@Composable
fun SuratHeaderCard(suratDetail: SuratDetail) {
    AppCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                StatusChip(status = suratDetail.dataUmum.status)

                if (suratDetail.dataUmum.tanggalPengajuan != null) {
                    BodySmallText(text = formatDateTime(suratDetail.dataUmum.tanggalPengajuan))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TitleMediumText(text = suratDetail.dataUmum.jenisSurat)

            if (suratDetail.dataUmum.nomorSurat != null) {
                Spacer(modifier = Modifier.height(8.dp))
                BodyMediumText(text = "No. Surat: ${suratDetail.dataUmum.nomorSurat}")
            }

            if (suratDetail.dataUmum.nomorPengajuan != null) {
                Spacer(modifier = Modifier.height(4.dp))
                BodyMediumText(text = "No. Pengajuan: ${suratDetail.dataUmum.nomorPengajuan}")
            }

            if (suratDetail.dataUmum.tanggalSelesai != null) {
                Spacer(modifier = Modifier.height(4.dp))
                BodySmallText(text = "Selesai: ${suratDetail.dataUmum.tanggalSelesai}")
            }

            if (suratDetail.dataUmum.diprosesOleh != null) {
                Spacer(modifier = Modifier.height(4.dp))
                BodySmallText(text = "Diproses oleh: ${suratDetail.dataUmum.diprosesOleh}")
            }

            if (suratDetail.dataUmum.disahkanOleh != null) {
                Spacer(modifier = Modifier.height(4.dp))
                BodySmallText(text = "Disahkan oleh: ${suratDetail.dataUmum.disahkanOleh}")
            }
        }
    }
}