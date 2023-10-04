package dev.hosi.unrealestateblocking.repositories

import dev.hosi.unrealestateblocking.entities.Property
import org.springframework.data.jpa.repository.JpaRepository

interface PropertyRepository : JpaRepository<Property, Long>
