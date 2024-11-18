package ru.quipy.projections

import org.springframework.stereotype.Service
import ru.quipy.api.*
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
@AggregateSubscriber(
    aggregateClass = ProjectAggregate::class, subscriberName = "task-projection"
)
class TaskEventSubscriber(
    private val taskProjectionRepository: TaskProjectionRepository
) {
    @SubscribeEvent
    fun newTask(event: TaskCreatedEvent) {
        taskProjectionRepository.save(TaskProjectionData(event.taskId, event.taskName, UUID(0, 0), mutableListOf()))
    }

    @SubscribeEvent
    fun changeName(event: NameChangedEvent) {
        val data = taskProjectionRepository.findById(event.taskId).getOrNull()
            ?: throw IllegalArgumentException("the task doesn't exist")
        data.name = event.newName
        taskProjectionRepository.save(data)
    }

    @SubscribeEvent
    fun changeStatus(event: StatusChangedEvent) {
        val data = taskProjectionRepository.findById(event.taskId).getOrNull()
            ?: throw IllegalArgumentException("the task doesn't exist")
        data.status = event.statusId
        taskProjectionRepository.save(data)
    }

    @SubscribeEvent
    fun assignUser(event: UserAssignedEvent) {
        val data = taskProjectionRepository.findById(event.taskId).getOrNull()
            ?: throw IllegalArgumentException("the task doesn't exist")
        data.assignees.add(event.assigneeId)
        taskProjectionRepository.save(data)
    }
}