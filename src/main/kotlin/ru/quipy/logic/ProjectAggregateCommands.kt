package ru.quipy.logic

import ru.quipy.api.ProjectCreatedEvent
import ru.quipy.api.TaskCreatedEvent
import ru.quipy.api.UserAssignedEvent
import ru.quipy.api.StatusChangedEvent
import ru.quipy.api.NameChangedEvent
import java.util.*


// Commands : takes something -> returns event
// Here the commands are represented by extension functions, but also can be the class member functions

fun ProjectAggregateState.create(id: UUID, title: String, creatorId: String): ProjectCreatedEvent {
    return ProjectCreatedEvent(
        projectId = id,
        title = title,
        creatorId = creatorId,
    )
}

fun ProjectAggregateState.addTask(name: String): TaskCreatedEvent {
    return TaskCreatedEvent(projectId = this.getId(), taskId = UUID.randomUUID(), taskName = name)
}

fun ProjectAggregateState.assignUser(taskId: UUID, assigneeId: UUID): UserAssignedEvent {
    if (!tasks.containsKey(taskId)) {
        throw IllegalArgumentException("Task doesn't exists: $taskId")
    }

    // TODO: проверка того что assignee это участник проекта

    return UserAssignedEvent(getId(), taskId, assigneeId)
}

fun ProjectAggregateState.changeStatus(taskId: UUID, statusId: UUID): StatusChangedEvent {
    if (!tasks.containsKey(taskId)) {
        throw IllegalArgumentException("Task doesn't exists: $taskId")
    }

    // TODO: проверка на наличие статуса
    
    return StatusChangedEvent(getId(), taskId, statusId)
}

fun ProjectAggregateState.changeName(taskId: UUID, name: String): NameChangedEvent {
    if (!tasks.containsKey(taskId)) {
        throw IllegalArgumentException("Task doesn't exists: $taskId")
    }

    return NameChangedEvent(getId(), taskId, name)
}
