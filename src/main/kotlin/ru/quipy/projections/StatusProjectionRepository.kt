package ru.quipy.projections

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StatusProjectionRepository : JpaRepository<StatusProjectionData, UUID> { }