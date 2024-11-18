package ru.quipy.projections
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserProjectionRepository : JpaRepository<UserProjectionData, String> {
    fun findByNickname(nickname: String): UserProjectionData?
}