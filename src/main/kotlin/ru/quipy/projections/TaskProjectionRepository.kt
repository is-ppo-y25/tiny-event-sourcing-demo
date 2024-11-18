package ru.quipy.projections

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TaskProjectionRepository : JpaRepository<TaskProjectionData, UUID> {
    fun findByProject(projectId: UUID): List<TaskProjectionData>
}