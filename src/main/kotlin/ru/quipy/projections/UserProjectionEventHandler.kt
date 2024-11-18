package ru.quipy.projections

import org.springframework.stereotype.Service
import ru.quipy.api.UserCreatedEvent
import ru.quipy.api.UserUpdatedEvent

@Service
class UserProjectionEventHandler(
        private val userProjectionService: UserProjectionService
) {
    fun handle(event: UserCreatedEvent) {
        val projectionData = UserProjectionData(
                userId = event.userId.toString(),
                nickname = event.nickname,
                userName = event.userName
        )
        userProjectionService.save(projectionData)
    }

    fun handle(event: UserUpdatedEvent) {
        val userProjection = userProjectionService.findByNickname(event.nickname)
        if (userProjection != null) {
            val updatedUser = userProjection.copy(userName = event.userName, nickname = event.nickname)
            userProjectionService.save(updatedUser)
        }
    }
}