package com.cvindosistem.simpeldesa.core.domain.usecases

import com.cvindosistem.simpeldesa.core.domain.model.notification.NotifikasiResult
import com.cvindosistem.simpeldesa.core.domain.repository.NotifikasiRepository


class GetNotifikasiUseCase(private val notifikasiRepository: NotifikasiRepository) {
    suspend operator fun invoke(): NotifikasiResult {
        return notifikasiRepository.getNotifikasi()
    }
}