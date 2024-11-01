package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.awt.Color
import java.util.*

// Service's business logic
class ProjectAggregateState : AggregateState<UUID, ProjectAggregate> {
    private lateinit var projectId: UUID
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()

    lateinit var projectTitle: String
    lateinit var creatorId: String
    var statuses = mutableMapOf<UUID, StatusEntity>()
    var assignees = mutableSetOf<UUID>()

    override fun getId() = projectId

    // State transition functions which is represented by the class member function


    @StateTransitionFunc
    fun projectCreatedApply(event: ProjectCreatedEvent) {
        projectId = event.projectId
        projectTitle = event.title
        creatorId = event.assigneeId.toString()
        assignees.add(event.assigneeId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskCreatedApply(event: TaskCreatedEvent) {
        // TODO
    }

    @StateTransitionFunc
    fun addingUserToProjectApply(event: AddingUserToProjectEvent) {
        assignees.add(event.newAssigneeId)
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun statusCreatedApply(event: StatusCreatedEvent) {
        statuses[event.statusId] = StatusEntity(event.statusId, event.title, event.color)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun statusDeletedApply(event: StatusDeletedEvent) {
        statuses.remove(event.statusId)
        updatedAt = event.createdAt
    }
}

data class ProjectEntity(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val participants: MutableSet<UUID>,
    val statuses : MutableSet<UUID>,
    val tasks : MutableSet<UUID>
)

data class StatusEntity(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val color: Color
)
