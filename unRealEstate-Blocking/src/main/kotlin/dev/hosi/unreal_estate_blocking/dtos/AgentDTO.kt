package dev.hosi.unreal_estate_blocking.dtos

import dev.hosi.unreal_estate_blocking.entities.Agent
import java.time.Instant

data class AgentDTO(
    val id: Long,
    val firstname: String,
    val lastname: String,
    val nickname: String,
    val email: String,
    val phone: String,
    val birthDate: Instant,
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
