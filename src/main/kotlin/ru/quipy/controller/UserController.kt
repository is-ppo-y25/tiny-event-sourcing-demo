package ru.quipy.userDemo.controller

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
    @PostMapping("/register-userManager")
    fun registerUserManager(): UserManagerCreatedEvent {
        return userEsService.create { it.create(UUID.randomUUID()) }
    }

    @GetMapping("{userManagerId}/nickname-check")
    fun checkNickname(
            @PathVariable userManagerId: UUID,
            @RequestParam nickname: String
    ): String {
        val userManagerState = userEsService.getState(userManagerId)
                ?: throw IllegalArgumentException("No such ID - $userManagerId")

        return if (!userManagerState.checkNickname(nickname)) {
            "Nickname is available"
        } else {
            "Nickname already exists"
        }
    }

    @PostMapping("{userManagerId}/register")
    fun registerUser(
            @PathVariable userManagerId: UUID,
            @RequestParam nickname: String,
            @RequestParam name: String,
            @RequestParam password: String
    ): String {
        val userManagerState = userEsService.getState(userManagerId)
                ?: throw IllegalArgumentException("No such ID - $userManagerId")

        if (userManagerState.checkNickname(nickname)) {
            return "Nickname already exists"
        }

        val userId = UUID.randomUUID()
        val event = userEsService.update(userManagerId) { it.createUser(userManagerId, userId, nickname, name, password) }
        return "User registered with ID: ${event.userId}"
    }

    @GetMapping("{userManagerId}/user/{userId}")
    fun getUser(
            @PathVariable userManagerId: UUID,
            @PathVariable userId: UUID
    ): UserManagerAggregateState? {
        val userState = userEsService.getState(userManagerId)
                ?: throw IllegalArgumentException("No such userManager with ID - $userManagerId")

        return userState
    }

    @PutMapping("{userManagerId}/user/{userId}/update")
    fun updateUser(
            @PathVariable userManagerId: UUID,
            @PathVariable userId: UUID,
            @RequestParam nickname: String,
            @RequestParam name: String,
            @RequestParam password: String
    ): String {
        userEsService.getState(userManagerId)
                ?: throw IllegalArgumentException("No such userManager with ID - $userManagerId")

        val event = userEsService.update(userManagerId) {
            it.updateUser(userManagerId, userId, nickname, name, password)
        }

        return "User updated with ID: ${event.userId}"
    }
}
