package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.api.ProjectAggregate
import ru.quipy.api.TaskCreatedEvent
import ru.quipy.api.UserAssignedEvent
import ru.quipy.api.StatusChangedEvent
import ru.quipy.api.NameChangedEvent
import ru.quipy.streams.AggregateSubscriptionsManager
import javax.annotation.PostConstruct

@Service
class ProjectEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(ProjectEventsSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(ProjectAggregate::class, "some-meaningful-name") {

            `when`(TaskCreatedEvent::class) { event ->
                logger.info("Task created: {}", event.taskName)
            }

            `when`(UserAssignedEvent::class) { event ->
                logger.info("User {} is assigned to task {}", event.assigneeId, event.taskId)
            }

            `when`(StatusChangedEvent::class) { event ->
                logger.info("Status of {} is changed to {}", event.taskId, event.statusId)
            }

            `when`(NameChangedEvent::class) { event ->
                logger.info("Changed name of task {} to {}", event.taskId, event.newName)
            }
        }
    }
}
