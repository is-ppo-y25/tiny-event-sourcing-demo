package ru.quipy.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.api.*
import ru.quipy.core.EventSourcingService
import ru.quipy.domain.Event
import ru.quipy.logic.*
import java.awt.Color
import org.springframework.web.bind.annotation.RequestBody
import ru.quipy.api.ProjectAggregate
import ru.quipy.api.UserAssignedEvent
import ru.quipy.api.NameChangedEvent
import ru.quipy.logic.ProjectAggregateState
import ru.quipy.logic.assignUser
import ru.quipy.logic.changeStatus
import ru.quipy.logic.changeName
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>
) {
    @GetMapping("/{projectId}")
    fun getAccount(@PathVariable projectId: UUID) : ProjectAggregateState? {
        return projectEsService.getState(projectId)
    }

    @PostMapping("/{projectTitle}")
    fun createProject(@PathVariable projectTitle : String, @RequestParam assigneeId: UUID) : List<Event<ProjectAggregate>> {
        val projectCreated = projectEsService.create { it.projectCreate(title = projectTitle, assigneeId = assigneeId) }

        val statusCreated = projectEsService.update(projectCreated.projectId) {
            it.statusCreate(statusId = UUID.randomUUID(), "CREATED", Color(0, 255, 0))
        }

        return listOf(projectCreated, statusCreated)
    }

    @PostMapping("/{projectId}/assign")
    fun addUserToProject(@PathVariable projectId: UUID, @RequestParam newAssigneeId : UUID, @RequestParam assigneeId : UUID) : AddingUserToProjectEvent {
        return projectEsService.update(projectId) {
            it.addUserToProject(newAssigneeId = newAssigneeId, assigneeId = assigneeId)
        }
    }

    @PostMapping("/{projectId}/status/{name}")
    fun statusCreate(@PathVariable projectId: UUID,
                     @PathVariable name: String,
                     @RequestParam statusId: UUID,
                     @RequestParam red: Int,
                     @RequestParam green: Int,
                     @RequestParam blue: Int) : StatusCreatedEvent {
        val color = Color(red, green, blue)

        return projectEsService.update(projectId) {
            it.statusCreate(statusId = statusId, name = name, color = color)
        }
    }

    @PostMapping("/{projectId}/status/")
    fun statusDelete(@PathVariable projectId: UUID, @RequestParam statusId: UUID) : StatusDeletedEvent {
        return projectEsService.update(projectId) {
            it.statusDelete(statusId = statusId)
        }
    }

    @PostMapping("/{projectId}/tasks/{taskId}/assign")
    fun assignUser(@PathVariable projectId: UUID, @PathVariable taskId: UUID, @RequestBody request: UserRequest) : UserAssignedEvent {
        return projectEsService.update(projectId) {
            it.assignUser(taskId, request.userId)
        }
    }

    @PostMapping("/{projectId}/tasks/{taskId}/status")
    fun assignUser(@PathVariable projectId: UUID, @PathVariable taskId: UUID, @RequestBody request: StatusRequest) : StatusChangedEvent {
        return projectEsService.update(projectId) {
            it.changeStatus(taskId, request.statusId)
        }
    }

    @PostMapping("/{projectId}/tasks/{taskId}/newName")
    fun assignUser(@PathVariable projectId: UUID, @PathVariable taskId: UUID, @RequestBody request: NameRequest) : NameChangedEvent {
        return projectEsService.update(projectId) {
            it.changeName(taskId, request.name)
        }
    }
}

data class UserRequest(val userId: UUID)
data class StatusRequest(val statusId: UUID)
data class NameRequest(val name: String)
