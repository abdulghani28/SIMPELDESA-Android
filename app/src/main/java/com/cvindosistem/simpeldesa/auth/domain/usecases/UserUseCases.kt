package com.cvindosistem.simpeldesa.auth.domain.usecases

import com.cvindosistem.simpeldesa.auth.data.repository.UserRepository
import com.cvindosistem.simpeldesa.auth.domain.model.UserInfoResult

class GetUserInfoUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): UserInfoResult = userRepository.getUserInfo()
}

//class GetOrganizationInfoUseCase(private val userRepository: UserRepository) {
//    suspend operator fun invoke(): OrganizationInfoResult = userRepository.getOrganizationInfo()
//}

//class EditProfileUseCase(private val userRepository: UserRepository) {
//    suspend operator fun invoke(request: EditProfileRequest): EditProfileResult =
//        userRepository.editProfile(request)
//}