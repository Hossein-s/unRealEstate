package dev.hosi.unrealestatenonblockingwebflux.dtos

import dev.hosi.unrealestatenonblockingwebflux.entities.Agent
import java.time.LocalDateTime

data class AgentDTO(
    val id: Long,
    val firstname: String,
    val lastname: String,
    val nickname: String,
    val email: String,
    val phone: String,
    val birthDate: LocalDateTime,
) {
    companion object {
        fun fromAgent(agent: Agent): AgentDTO {
            return AgentDTO(
                id = agent.id!!,
                firstname = agent.firstname,
                lastname = agent.lastname,
                nickname = agent.nickname,
                email = agent.email,
                phone = agent.phone,
                birthDate = agent.birthDate,
            )
        }
    }
}
