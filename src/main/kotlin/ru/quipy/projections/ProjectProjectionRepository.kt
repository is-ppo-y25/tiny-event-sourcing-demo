package ru.quipy.projections
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProjectProjectionRepository : JpaRepository<ProjectProjectionData, String> {
    fun findById(id: UUID): ProjectProjectionData?
}