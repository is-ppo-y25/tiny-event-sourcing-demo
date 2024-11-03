package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

// Service's business logic
class ProjectAggregateState : AggregateState<UUID, ProjectAggregate> {
    private lateinit var projectId: UUID
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()

    lateinit var projectTitle: String
    lateinit var creatorId: String
    var tasks = mutableMapOf<UUID, TaskEntity>()

    override fun getId() = projectId

    // State transition functions which is represented by the class member function
    @StateTransitionFunc
    fun projectCreatedApply(event: ProjectCreatedEvent) {
        projectId = event.projectId
        projectTitle = event.title
        creatorId = event.creatorId
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun taskCreatedApply(event: TaskCreatedEvent) {
        tasks[event.taskId] = TaskEntity(event.taskId, event.taskName, UUID.randomUUID(), mutableSetOf())
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun assignUserApply(event: UserAssignedEvent) {
        tasks[event.taskId]?.assignees?.add(event.assigneeId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun changeStatusApply(event: StatusChangedEvent) {
        tasks[event.taskId]?.status = event.statusId
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun changeNameApply(event: NameChangedEvent) {
        tasks[event.taskId]?.name = event.newName
        updatedAt = event.createdAt
    }
}

data class TaskEntity(
    val id: UUID = UUID.randomUUID(),
    var name: String,
    var status: UUID,
    val assignees: MutableSet<UUID>
)
