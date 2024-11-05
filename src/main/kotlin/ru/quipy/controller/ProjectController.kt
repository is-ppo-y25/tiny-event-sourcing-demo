package ru.quipy.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.quipy.api.*
import ru.quipy.core.EventSourcingService
import ru.quipy.domain.Event
import ru.quipy.logic.*
import java.awt.Color
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
    private val DEFAULT_CREATED_STATUS_COLOR = Color(0, 255, 0)

    @GetMapping("/{projectId}")
    fun getProject(@PathVariable projectId: UUID) : ResponseEntity<ProjectAggregateState> {
        return projectEsService.getState(projectId)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createProject(@RequestParam projectTitle : String, @RequestParam assigneeId: UUID)
        : ResponseEntity<List<Event<ProjectAggregate>>> {
        val projectCreated = projectEsService.create {
            it.projectCreate(title = projectTitle, assigneeId = assigneeId)
        }

        val statusCreated = projectEsService.update(projectCreated.projectId) {
            it.statusCreate(statusId = UUID.randomUUID(), "CREATED", DEFAULT_CREATED_STATUS_COLOR)
        }

        return ResponseEntity.ok(listOf(projectCreated, statusCreated))
    }

    @PostMapping("/{projectId}/tasks")
    fun createTask(@PathVariable projectId: UUID,
                   @RequestParam taskName: String) : ResponseEntity<TaskCreatedEvent> {
        return ResponseEntity.ok(projectEsService.update(projectId) {
            it.createTask(taskName = taskName)
        })
    }

    @PostMapping("/{projectId}/assignees")
    fun assignUserToProject(@PathVariable projectId: UUID,
                            @RequestParam newAssigneeId : UUID,
                            @RequestParam assigneeId : UUID) : ResponseEntity<AddingUserToProjectEvent> {
        return ResponseEntity.ok(projectEsService.update(projectId) {
            it.addUserToProject(newAssigneeId = newAssigneeId, assigneeId = assigneeId)
        })
    }

    @PostMapping("/{projectId}/statuses")
    fun statusCreate(@PathVariable projectId: UUID,
                     @RequestBody request: StatusCreateRequest) : ResponseEntity<StatusCreatedEvent> {
        val color = Color(request.color.red, request.color.green, request.color.red)

        return ResponseEntity.ok(projectEsService.update(projectId) {
            it.statusCreate(statusId = request.statusId, name = request.name, color = color)
        })
    }

    @DeleteMapping("/{projectId}/statuses")
    fun statusDelete(@PathVariable projectId: UUID, @RequestParam statusId: UUID)
        : ResponseEntity<StatusDeletedEvent> {
        return ResponseEntity.ok(projectEsService.update(projectId) {
            it.statusDelete(statusId = statusId)
        })
    }

    @PostMapping("/{projectId}/tasks/{taskId}/assignees")
    fun assignUserToTask(@PathVariable projectId: UUID,
                         @PathVariable taskId: UUID,
                         @RequestBody request: UserRequest) : ResponseEntity<UserAssignedEvent> {
        return ResponseEntity.ok(projectEsService.update(projectId) {
            it.assignUser(taskId, request.userId)
        })
    }

    @PostMapping("/{projectId}/tasks/{taskId}/statuses")
    fun changeTaskStatus(@PathVariable projectId: UUID,
                         @PathVariable taskId: UUID,
                         @RequestBody request: StatusRequest) : ResponseEntity<StatusChangedEvent> {
        return ResponseEntity.ok(projectEsService.update(projectId) {
            it.changeStatus(taskId, request.statusId)
        })
    }

    @PutMapping("/{projectId}/tasks/{taskId}/name")
    fun changeTaskName(@PathVariable projectId: UUID,
                       @PathVariable taskId: UUID,
                       @RequestBody request: NameRequest) : ResponseEntity<NameChangedEvent> {
        return ResponseEntity.ok(projectEsService.update(projectId) {
            it.changeName(taskId, request.name)
        })
    }
}

data class UserRequest(val userId: UUID)
data class StatusRequest(val statusId: UUID)
data class NameRequest(val name: String)

data class StatusCreateRequest(
    val statusId: UUID,
    val name: String,
    val color: ColorDto
)

data class ColorDto(
    val red: Int,
    val green: Int,
    val blue: Int
)