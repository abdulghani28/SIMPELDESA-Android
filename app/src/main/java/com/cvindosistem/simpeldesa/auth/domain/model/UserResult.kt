package com.cvindosistem.simpeldesa.auth.domain.model

import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse

sealed class UserInfoResult {
    data class Success(val data: UserInfoResponse) : UserInfoResult()
    data class Error(val message: String) : UserInfoResult()
}
//
//sealed class OrganizationInfoResult {
//    data class Success(val data: OrganizationInfoResponse) : OrganizationInfoResult()
//    data class Error(val message: String) : OrganizationInfoResult()
//}
//
//sealed class EditProfileResult {
//    data class Success(val data: EditProfileResponse) : EditProfileResult()
//    data class Error(val message: String) : EditProfileResult()
//}