package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val PROJECT_CREATED_EVENT = "PROJECT_CREATED_EVENT"
const val TAG_CREATED_EVENT = "TAG_CREATED_EVENT"
const val TAG_ASSIGNED_TO_TASK_EVENT = "TAG_ASSIGNED_TO_TASK_EVENT"
const val TASK_CREATED_EVENT = "TASK_CREATED_EVENT"
const val USER_CREATED_EVENT = "USER_CREATED_EVENT"
const val USER_UPDATED_EVENT = "USER_UPDATED_EVENT"
const val USER_MANAGER_CREATED_EVENT = "USER_MANAGER_CREATED_EVENT"

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

@DomainEvent(name = TAG_CREATED_EVENT)
class TagCreatedEvent(
        val projectId: UUID,
        val tagId: UUID,
        val tagName: String,
        createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
        name = TAG_CREATED_EVENT,
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

@DomainEvent(name = TAG_ASSIGNED_TO_TASK_EVENT)
class TagAssignedToTaskEvent(
        val projectId: UUID,
        val taskId: UUID,
        val tagId: UUID,
        createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
        name = TAG_ASSIGNED_TO_TASK_EVENT,
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