package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.awt.Color
import java.util.*

const val PROJECT_CREATED_EVENT = "PROJECT_CREATED_EVENT"
const val TASK_CREATED_EVENT = "TASK_CREATED_EVENT"
const val ADDING_USER_TO_PROJECT_EVENT = "ADDING_USER_TO_PROJECT_EVENT"
const val STATUS_CREATED_EVENT = "STATUS_CREATED_EVENT"
const val STATUS_DELETED_EVENT = "STATUS_DELETED_EVENT"

@DomainEvent(name = PROJECT_CREATED_EVENT)
class ProjectCreatedEvent(
    val projectId: UUID,
    val assigneeId: UUID,
    val title: String,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectAggregate>(
    name = PROJECT_CREATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = ADDING_USER_TO_PROJECT_EVENT)
class AddingUserToProjectEvent(
    val projectId: UUID,
    val newAssigneeId : UUID,
    val assigneeId : UUID,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectAggregate>(
    name = ADDING_USER_TO_PROJECT_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = TASK_CREATED_EVENT)
class TaskCreatedEvent(
    val taskId: UUID,
    val creatorId : UUID,
    val projectId: UUID,
    val title: String,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectAggregate>(
    name = TASK_CREATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = STATUS_CREATED_EVENT)
class StatusCreatedEvent(
    val statusId: UUID,
    val projectId: UUID,
    val title: String,
    val color: Color,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectAggregate>(
    name = STATUS_CREATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = STATUS_DELETED_EVENT)
class StatusDeletedEvent(
    val statusId: UUID,
    val projectId: UUID,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectAggregate>(
    name = STATUS_DELETED_EVENT,
    createdAt = createdAt
)