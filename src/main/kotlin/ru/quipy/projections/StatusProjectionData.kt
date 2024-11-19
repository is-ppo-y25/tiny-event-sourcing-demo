package ru.quipy.projections

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "status_projections")
data class StatusProjectionData(
    @Id
    val id: UUID,
    val name: String,
    val color: String
) {
    constructor() : this(UUID.randomUUID(), "", "")
}