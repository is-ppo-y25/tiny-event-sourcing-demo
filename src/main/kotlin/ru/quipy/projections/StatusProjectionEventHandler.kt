package ru.quipy.projections

import org.springframework.stereotype.Service
import ru.quipy.api.StatusCreatedEvent
import ru.quipy.api.StatusDeletedEvent

@Service
class StatusProjectionEventHandler(
    private val statusProjectionRepository: StatusProjectionRepository
) {
    fun on(event: StatusCreatedEvent) {
        val statusProjection = StatusProjectionData(
            id = event.statusId,
            name = event.title,
            color = event.color.toString()
        )
        statusProjectionRepository.save(statusProjection)
    }

    fun on(event: StatusDeletedEvent) {
        statusProjectionRepository.findById(event.statusId).ifPresent { statusProjection ->
            statusProjectionRepository.delete(statusProjection)
        }
    }
}
