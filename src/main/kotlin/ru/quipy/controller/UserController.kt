package ru.quipy.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.quipy.api.UserManagerAggregate
import ru.quipy.api.UserManagerCreatedEvent
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.UserManagerAggregateState
import ru.quipy.logic.create
import ru.quipy.logic.createUser
import ru.quipy.logic.updateUser
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(
        val userEsService: EventSourcingService<UUID, UserManagerAggregate, UserManagerAggregateState>

) {
    @PostMapping("/user-managers")
    fun registerUserManager(): ResponseEntity<UserManagerCreatedEvent> {
        return ResponseEntity.ok(userEsService.create { it.create(UUID.randomUUID()) })
    }

    @GetMapping("{userManagerId}/nickname-check")
    fun checkNickname(
            @PathVariable userManagerId: UUID,
            @RequestParam nickname: String
    ): ResponseEntity<String> {
        userEsService.getState(userManagerId)?.let { userManagerState ->
            return if (!userManagerState.checkNickname(nickname)) {
                ResponseEntity.ok("Nickname is available")
            } else {
                ResponseEntity.ok("Nickname already exists")
            }
        }

        return ResponseEntity.notFound().build()
    }

    @PostMapping("{userManagerId}")
    fun registerUser(
            @PathVariable userManagerId: UUID,
            @RequestParam nickname: String,
            @RequestParam name: String,
            @RequestParam password: String
    ): ResponseEntity<String> {
        val userManagerState = userEsService.getState(userManagerId)
                ?: return ResponseEntity.notFound().build()

        if (userManagerState.checkNickname(nickname)) {
            return ResponseEntity.ok("Nickname already exists")
        }

        val userId = UUID.randomUUID()
        val event = userEsService.update(userManagerId) { it.createUser(userManagerId, userId, nickname, name, password) }

        return ResponseEntity.ok("User registered with ID: ${event.userId}")
    }

    @GetMapping("{userManagerId}/user/{userId}")
    fun getUser(
            @PathVariable userManagerId: UUID,
            @PathVariable userId: UUID
    ): ResponseEntity<UserManagerAggregateState> {
        val userState = userEsService.getState(userManagerId)
                ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(userState)
    }

    @PutMapping("{userManagerId}/user/{userId}")
    fun updateUser(
            @PathVariable userManagerId: UUID,
            @PathVariable userId: UUID,
            @RequestParam nickname: String,
            @RequestParam name: String,
            @RequestParam password: String
    ): ResponseEntity<String> {
        userEsService.getState(userManagerId)
                ?: return ResponseEntity.notFound().build()

        val event = userEsService.update(userManagerId) {
            it.updateUser(userManagerId, userId, nickname, name, password)
        }

        return ResponseEntity.ok("User updated with ID: ${event.userId}")
    }
}
