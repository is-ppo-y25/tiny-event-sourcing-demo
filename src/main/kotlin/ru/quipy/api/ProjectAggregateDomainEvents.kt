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

const val USER_CREATED_EVENT = "USER_CREATED_EVENT"
const val USER_UPDATED_EVENT = "USER_UPDATED_EVENT"
const val USER_MANAGER_CREATED_EVENT = "USER_MANAGER_CREATED_EVENT"

const val USER_ASSIGNED_EVENT = "USER_ASSIGNED_EVENT"
const val STATUS_CHANGED_EVENT = "STATUS_CHANGED_EVENT"
const val NAME_CHANGED_EVENT = "NAME_CHANGED_EVENT"

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
        val projectId: UUID,
        val taskId: UUID,
        val taskName: String,
        createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
        name = TASK_CREATED_EVENT,
        createdAt = createdAt
)

@DomainEvent(name = USER_CREATED_EVENT)
class UserCreatedEvent(
        val userManagerId:UUID,
        val userId: UUID,
        val nickname: String,
        val userName: String,
        val password: String,
        createdAt: Long = System.currentTimeMillis()
) : Event<UserManagerAggregate>(
        name = USER_CREATED_EVENT,
        createdAt = createdAt
)

@DomainEvent(name = USER_UPDATED_EVENT)
class UserUpdatedEvent(
        val userManagerId:UUID,
        val userId: UUID,
        val nickname: String,
        val userName: String,
        val password: String,
        createdAt: Long = System.currentTimeMillis()
) : Event<UserManagerAggregate>(
        name = USER_UPDATED_EVENT,
        createdAt = createdAt
)

@DomainEvent(name = USER_MANAGER_CREATED_EVENT)
class UserManagerCreatedEvent(
        val userManagerId: UUID,
        createdAt: Long = System.currentTimeMillis(),
) : Event<UserManagerAggregate>(
        name = USER_MANAGER_CREATED_EVENT,
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
