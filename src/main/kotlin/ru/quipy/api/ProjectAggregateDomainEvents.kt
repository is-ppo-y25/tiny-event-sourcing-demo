package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val PROJECT_CREATED_EVENT = "PROJECT_CREATED_EVENT"
const val TASK_CREATED_EVENT = "TASK_CREATED_EVENT"

const val USER_ASSIGNED_EVENT = "USER_ASSIGNED_EVENT"
const val STATUS_CHANGED_EVENT = "STATUS_CHANGED_EVENT"
const val NAME_CHANGED_EVENT = "NAME_CHANGED_EVENT"

// API
@DomainEvent(name = PROJECT_CREATED_EVENT)
class ProjectCreatedEvent(
    val projectId: UUID,
    val title: String,
    val creatorId: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PROJECT_CREATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = TASK_CREATED_EVENT)
class TaskCreatedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val taskName: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = TASK_CREATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(USER_ASSIGNED_EVENT)
class UserAssignedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val assigneeId: UUID,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectAggregate>(
    name = USER_ASSIGNED_EVENT,
    createdAt = createdAt
)

@DomainEvent(STATUS_CHANGED_EVENT)
class StatusChangedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val statusId: UUID,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectAggregate>(
    name = STATUS_CHANGED_EVENT,
    createdAt = createdAt
)

@DomainEvent(NAME_CHANGED_EVENT)
class NameChangedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val newName: String,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectAggregate>(
    name = NAME_CHANGED_EVENT,
    createdAt = createdAt
)
