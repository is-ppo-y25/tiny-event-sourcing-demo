package ru.quipy.logic

import ru.quipy.api.*
import java.awt.Color
import java.util.*


// Commands : takes something -> returns event
// Here the commands are represented by extension functions, but also can be the class member functions

fun ProjectAggregateState.projectCreate(title : String, assigneeId: UUID) : ProjectCreatedEvent {
    return ProjectCreatedEvent(projectId = UUID.randomUUID(), assigneeId = assigneeId, title = title)
}

fun ProjectAggregateState.addUserToProject(newAssigneeId : UUID, assigneeId : UUID) : AddingUserToProjectEvent {
    if (assignees.contains(newAssigneeId)) {
        throw IllegalArgumentException("Assignee already exists: $newAssigneeId")
    }

    if (!assignees.contains(assigneeId)) {
        throw IllegalArgumentException("Assignee doesn't exists: $assigneeId")
    }

    return AddingUserToProjectEvent(projectId = this.getId(),
        newAssigneeId = newAssigneeId,
        assigneeId = assigneeId)
}

fun ProjectAggregateState.statusCreate(statusId: UUID, name: String, color: Color) : StatusCreatedEvent {
    if (statuses.values.any { it.name == name }) {
        throw IllegalArgumentException("Status already exists: $name")
    }

    return StatusCreatedEvent(projectId = this.getId(),
        statusId = statusId,
        title = name,
        color = color)
}

fun ProjectAggregateState.statusDelete(statusId: UUID) : StatusDeletedEvent {
    if (!statuses.containsKey(statusId)) {
        throw IllegalArgumentException("Status doesn't exists: $statusId")
    }

    return StatusDeletedEvent(projectId = this.getId(), statusId = statusId)
}