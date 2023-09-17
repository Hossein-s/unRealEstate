package dev.hosi.unreal_estate_blocking.repositories

import dev.hosi.unreal_estate_blocking.entities.Agent
import org.springframework.data.jpa.repository.JpaRepository

interface AgentRepository : JpaRepository<Agent, Long>
