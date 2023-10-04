package dev.hosi.unrealestateblocking.repositories

import dev.hosi.unrealestateblocking.entities.Agent
import org.springframework.data.jpa.repository.JpaRepository

interface AgentRepository : JpaRepository<Agent, Long>
