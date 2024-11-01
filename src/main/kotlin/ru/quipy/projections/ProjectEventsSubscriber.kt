package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.api.*
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.streams.AggregateSubscriptionsManager
import java.awt.Color
import java.util.*
import javax.annotation.PostConstruct

@Service
class ProjectEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(ProjectEventsSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(ProjectAggregate::class, "some-meaningful-name") {
            `when`(ProjectCreatedEvent::class) { event ->
                logger.info("Project created: {}", event.projectId)
            }

            `when`(AddingUserToProjectEvent::class) { event ->
                logger.info("User {} adding to Project {}: ", event.newAssigneeId, event.projectId)
            }

            `when`(TaskCreatedEvent::class) { event ->
                logger.info("Task created: {}", event.taskId)
            }

            `when`(StatusCreatedEvent::class) { event ->
                logger.info("Status created: {}", event.statusId)
            }

            `when`(StatusDeletedEvent::class) { event ->
                logger.info("Status deleted: {}", event.statusId)
            }
        }
    }
}