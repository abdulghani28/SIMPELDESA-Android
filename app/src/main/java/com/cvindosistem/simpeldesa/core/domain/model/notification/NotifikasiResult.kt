package com.cvindosistem.simpeldesa.core.domain.model.notification


sealed class NotifikasiResult {
    data class Success(val data: NotificationResponse) : NotifikasiResult()
    data class Error(val message: String) : NotifikasiResult()
}