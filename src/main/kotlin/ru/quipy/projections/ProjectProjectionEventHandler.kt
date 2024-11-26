package ru.quipy.projections

import org.springframework.stereotype.Service
import ru.quipy.api.*

@Service
class ProjectProjectionEventHandler(
    private val projectProjectionRepository: ProjectProjectionRepository
) {
    fun on(event: ProjectCreatedEvent) {
        val newProjectProjection = ProjectProjectionData(
            id = event.projectId,
            name = event.title,
            participants = mutableSetOf(event.assigneeId),
            statuses = mutableSetOf(),
            tasks = mutableSetOf()
        )

        projectProjectionRepository.save(newProjectProjection)
    }

    fun on(event: AddingUserToProjectEvent) {
        projectProjectionRepository.findById(event.projectId).ifPresent { projectProjection ->
            projectProjection.participants.add(event.newAssigneeId)
            projectProjectionRepository.save(projectProjection)
        }
    }

    fun on(event: StatusCreatedEvent) {
        projectProjectionRepository.findById(event.projectId).ifPresent { projectProjection ->
            projectProjection.statuses.add(event.statusId)
            projectProjectionRepository.save(projectProjection)
        }
    }

    fun on(event: StatusDeletedEvent) {
        projectProjectionRepository.findById(event.projectId).ifPresent { projectProjection ->
            projectProjection.statuses.remove(event.statusId)
            projectProjectionRepository.save(projectProjection)
        }
    }
}
