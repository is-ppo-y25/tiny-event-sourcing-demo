package ru.quipy.projections

import java.util.*
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "project_projections")
data class ProjectProjectionData (
    @Id
    val id: UUID,
    val name: String,
    @ElementCollection(fetch = FetchType.EAGER)
    val participants: MutableSet<UUID> = mutableSetOf(),
    @ElementCollection(fetch = FetchType.EAGER)
    val statuses: MutableSet<UUID> = mutableSetOf(),
    @ElementCollection(fetch = FetchType.EAGER)
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
