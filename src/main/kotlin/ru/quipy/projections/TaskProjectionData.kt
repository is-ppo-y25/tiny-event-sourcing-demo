package ru.quipy.projections

import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.util.UUID
import javax.persistence.*

@Entity
data class TaskProjectionData (
    @Id
    val taskId: UUID,
    val projectId: UUID,
    var name: String,
    var status: UUID,
    @ElementCollection(fetch = FetchType.EAGER)
    val assignees: MutableSet<UUID>
) {
    constructor() : this(UUID(0, 0), UUID(0, 0), "", UUID(0, 0), mutableSetOf())
}
