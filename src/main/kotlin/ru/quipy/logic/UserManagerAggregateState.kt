package ru.quipy.logic

import ru.quipy.api.UserCreatedEvent
import ru.quipy.api.UserManagerAggregate
import ru.quipy.api.UserManagerCreatedEvent
import ru.quipy.api.UserUpdatedEvent
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

class UserManagerAggregateState : AggregateState<UUID, UserManagerAggregate> {
    private lateinit var userManagerId: UUID
    val registeredNicknames: MutableSet<String> = mutableSetOf()
    var users = mutableMapOf<UUID, UserEntity>()
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()

    override fun getId() = userManagerId

    fun checkNickname(nickname: String): Boolean {
        return registeredNicknames.contains(nickname)
    }

    data class UserEntity(
            val userId: UUID,
            val nickname: String,
            val userName: String,
            val password: String
    )

    @StateTransitionFunc
    fun userManagerCreatedAply(event: UserManagerCreatedEvent) {
        userManagerId = event.userManagerId
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun userCreatedAply(event: UserCreatedEvent) {
        userManagerId = event.userManagerId
        users[event.userId] = UserEntity(event.userId, event.nickname, event.userName, event.password)
        registeredNicknames.add(event.nickname)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun userUpdatedAply(event: UserUpdatedEvent) {
        userManagerId = event.userManagerId
        users[event.userId] = UserEntity(event.userId, event.nickname, event.userName, event.password)
        registeredNicknames.add(event.nickname)
        users[event.userId]?.let { registeredNicknames.remove(it.nickname) }
        updatedAt = event.createdAt
    }
}