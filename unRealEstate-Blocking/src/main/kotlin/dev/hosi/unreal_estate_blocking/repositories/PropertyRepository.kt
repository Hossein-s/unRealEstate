package dev.hosi.unreal_estate_blocking.repositories

import dev.hosi.unreal_estate_blocking.entities.Property
import org.springframework.data.jpa.repository.JpaRepository

interface PropertyRepository : JpaRepository<Property, Long>
