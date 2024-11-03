package ru.quipy.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import ru.quipy.api.ProjectAggregate
import ru.quipy.api.ProjectCreatedEvent
import ru.quipy.api.TaskCreatedEvent
import ru.quipy.api.UserAssignedEvent
import ru.quipy.api.StatusChangedEvent
import ru.quipy.api.NameChangedEvent
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.ProjectAggregateState
import ru.quipy.logic.addTask
import ru.quipy.logic.create
import ru.quipy.logic.assignUser
import ru.quipy.logic.changeStatus
import ru.quipy.logic.changeName
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>
) {

    @PostMapping("/{projectTitle}")
    fun createProject(@PathVariable projectTitle: String, @RequestParam creatorId: String) : ProjectCreatedEvent {
        return projectEsService.create { it.create(UUID.randomUUID(), projectTitle, creatorId) }
    }

    @GetMapping("/{projectId}")
    fun getAccount(@PathVariable projectId: UUID) : ProjectAggregateState? {
        return projectEsService.getState(projectId)
    }

    @PostMapping("/{projectId}/tasks/{taskName}")
    fun createTask(@PathVariable projectId: UUID, @PathVariable taskName: String) : TaskCreatedEvent {
        return projectEsService.update(projectId) {
            it.addTask(taskName)
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
