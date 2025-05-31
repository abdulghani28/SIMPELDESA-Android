package com.cvindosistem.simpeldesa.main.domain.usecases

import com.cvindosistem.simpeldesa.main.data.repository.ReferensiRepository
import com.cvindosistem.simpeldesa.main.domain.model.AgamaResult
import com.cvindosistem.simpeldesa.main.domain.model.BidangUsahaResult
import com.cvindosistem.simpeldesa.main.domain.model.DisahkanOlehResult
import com.cvindosistem.simpeldesa.main.domain.model.JenisUsahaResult
import com.cvindosistem.simpeldesa.main.domain.model.PerbedaanIdentitasResult
import com.cvindosistem.simpeldesa.main.domain.model.StatusKawinResult
import com.cvindosistem.simpeldesa.main.domain.model.TercantumIdentitasResult

class GetTercantumIdentitasUseCase(private val referensiRepository: ReferensiRepository) {
    suspend operator fun invoke(): TercantumIdentitasResult {
        return referensiRepository.getTercantumIdentitas()
    }
}

class GetPerbedaanIdentitasUseCase(private val referensiRepository: ReferensiRepository) {
    suspend operator fun invoke(): PerbedaanIdentitasResult {
        return referensiRepository.getPerbedaanIdentitas()
    }
}

class GetDisahkanOlehUseCase(private val referensiRepository: ReferensiRepository) {
    suspend operator fun invoke(): DisahkanOlehResult {
        return referensiRepository.getDisahkanOleh()
    }
}

class GetAgamaUseCase(private val referensiRepository: ReferensiRepository) {
    suspend operator fun invoke(): AgamaResult {
        return referensiRepository.getAgama()
    }
}

class BidangUsahaUseCase(private val referensiRepository: ReferensiRepository) {
    suspend operator fun invoke(): BidangUsahaResult {
        return referensiRepository.getBidangUsaha()
    }
}

class JenisUsahaUseCase(private val referensiRepository: ReferensiRepository) {
    suspend operator fun invoke(): JenisUsahaResult {
        return referensiRepository.getJenisUsaha()
    }
}

class GetStatusKawinUseCase(private val referensiRepository: ReferensiRepository) {
    suspend operator fun invoke(): StatusKawinResult {
        return referensiRepository.getStatusKawin()
    }
}