package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.api.*
import ru.quipy.api.ProjectAggregate
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
    aggregateClass = ProjectAggregate::class, subscriberName = "demo-subs-stream"
)
class AnnotationBasedProjectEventsSubscriber(
    private val projectProjectionEventHandler : ProjectProjectionEventHandler,
    private val statusProjectionEventHandler: StatusProjectionEventHandler) {

    val logger: Logger = LoggerFactory.getLogger(AnnotationBasedProjectEventsSubscriber::class.java)

    @SubscribeEvent
    fun projectCreatedSubscriber(event: ProjectCreatedEvent) {
        logger.info("Project created: {}", event.projectId)
        projectProjectionEventHandler.on(event)
    }

    @SubscribeEvent
    fun addingUserToProjectSubscriber(event: AddingUserToProjectEvent) {
        logger.info("User {} adding to Project {}: ", event.newAssigneeId, event.projectId)
        projectProjectionEventHandler.on(event)
    }

    @SubscribeEvent
    fun statusCreatedSubscriber(event: StatusCreatedEvent) {
        logger.info("Status created: {}", event.statusId)
        projectProjectionEventHandler.on(event)
        statusProjectionEventHandler.on(event)
    }

    @SubscribeEvent
    fun statusDeletedSubscriber(event: StatusDeletedEvent) {
        logger.info("Status deleted: {}", event.statusId)
        projectProjectionEventHandler.on(event)
        statusProjectionEventHandler.on(event)
    }
}
