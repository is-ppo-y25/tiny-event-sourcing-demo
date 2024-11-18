package ru.quipy.projections

import org.springframework.stereotype.Service

@Service
class UserProjectionService(
        private val userProjectionRepository: UserProjectionRepository
) {
    fun save(user: UserProjectionData) {
        userProjectionRepository.save(user)
    }

    fun findByNickname(nickname: String): UserProjectionData? {
        return userProjectionRepository.findByNickname(nickname)
    }
}