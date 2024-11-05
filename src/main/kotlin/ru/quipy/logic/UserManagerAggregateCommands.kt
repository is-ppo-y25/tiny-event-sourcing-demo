package ru.quipy.logic

import ru.quipy.api.UserCreatedEvent
import ru.quipy.api.UserManagerCreatedEvent
import ru.quipy.api.UserUpdatedEvent
import java.util.*

fun UserManagerAggregateState.create(id: UUID): UserManagerCreatedEvent {
    return UserManagerCreatedEvent(
            userManagerId = id
    )
}

fun UserManagerAggregateState.createUser(userManagerId: UUID, userId: UUID, nickname: String, userName: String, password: String): UserCreatedEvent {
    if (registeredNicknames.contains(nickname)) {
        throw IllegalStateException("User with nickname $nickname already exists.")
    }
    return UserCreatedEvent(
            userManagerId = userManagerId,
            userId = userId,
            nickname = nickname,
            userName = userName,
            password = password
    )
}

fun UserManagerAggregateState.updateUser(userManagerId: UUID, userId: UUID, nickname: String, userName: String, password: String): UserUpdatedEvent {
    if (registeredNicknames.contains(nickname)) {
        throw IllegalStateException("User with nickname $nickname already exists.")
    }
    return UserUpdatedEvent(
            userManagerId = userManagerId,
            userId = userId,
            nickname = nickname,
            userName = userName,
            password = password
    )
}