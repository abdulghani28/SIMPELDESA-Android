package com.cvindosistem.simpeldesa.main.domain.model

import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.DisahkanOlehResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.PerbedaanIdentitasResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.TercantumIdentitasResponse

sealed class TercantumIdentitasResult {
    data class Success(val data: TercantumIdentitasResponse) : TercantumIdentitasResult()
    data class Error(val message: String) : TercantumIdentitasResult()
}

sealed class PerbedaanIdentitasResult {
    data class Success(val data: PerbedaanIdentitasResponse) : PerbedaanIdentitasResult()
    data class Error(val message: String) : PerbedaanIdentitasResult()
}

sealed class DisahkanOlehResult {
    data class Success(val data: DisahkanOlehResponse) : DisahkanOlehResult()
    data class Error(val message: String) : DisahkanOlehResult()
}

sealed class AgamaResult {
    data class Success(val data: AgamaResponse) : AgamaResult()
    data class Error(val message: String) : AgamaResult()
}