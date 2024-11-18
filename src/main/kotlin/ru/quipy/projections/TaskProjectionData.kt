package ru.quipy.projections

import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@TypeDef(name = "uuid-list", typeClass = MutableList::class)
data class TaskProjectionData (
    @Id
    val taskId: UUID,
    var name: String,
    var status: UUID,
    @Type(type = "uuid-list")
    @Column(columnDefinition = "uuid[]")
    val assignees: MutableList<UUID>
) {}
