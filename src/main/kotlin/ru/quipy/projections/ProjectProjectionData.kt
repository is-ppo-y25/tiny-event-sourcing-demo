package ru.quipy.projections

import java.util.*
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "project_projections")
data class ProjectProjectionData (
    @Id
    val id: UUID,
    val name: String,
    @ElementCollection
    val participants: MutableSet<UUID> = mutableSetOf(),
    @ElementCollection
    val statuses: MutableSet<UUID> = mutableSetOf(),
    @ElementCollection
    val tasks: MutableSet<UUID> = mutableSetOf()
) {
    constructor() : this(
        id = UUID.randomUUID(),
        name = "",
        participants = mutableSetOf(),
        statuses = mutableSetOf(),
        tasks = mutableSetOf()
    )
}
