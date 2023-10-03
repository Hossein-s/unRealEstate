package dev.hosi.unrealestatenonblockingcoroutines.services

import dev.hosi.unrealestatenonblockingcoroutines.dtos.AgentDTO
import dev.hosi.unrealestatenonblockingcoroutines.dtos.PageDTO
import dev.hosi.unrealestatenonblockingcoroutines.dtos.PropertyDTO
import dev.hosi.unrealestatenonblockingcoroutines.entities.Agent
import dev.hosi.unrealestatenonblockingcoroutines.entities.Property
import dev.hosi.unrealestatenonblockingcoroutines.repositories.AgentRepository
import dev.hosi.unrealestatenonblockingcoroutines.repositories.PropertyRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class UnRealEstateService(
    private val agentRepository: AgentRepository,
    private val propertyRepository: PropertyRepository,
) {
    suspend fun addAgent(
        firstname: String,
        lastname: String,
        nickname: String,
        email: String,
        phone: String,
        password: String,
        birthDate: LocalDate,
    ): AgentDTO {
        var agent = Agent(
            firstname = firstname,
            lastname = lastname,
            nickname = nickname,
            email = email,
            phone = phone,
            password = password,
            birthDate = birthDate,
        )

        agent = agentRepository.save(agent)
        return AgentDTO.fromAgent(agent)
    }

    suspend fun addProperty(
        agentId: Long,
        ownerName: String,
        state: String,
        city: String,
        address: String,
        price: Long,
        description: String,
    ): PropertyDTO {
        val agent = agentRepository.findById(agentId) ?: throw IllegalArgumentException("Agent not found")

        var property = Property(
            ownerName = ownerName,
            state = state,
            city = city,
            address = address,
            price = price,
            description = description,
            agentId = agent.id!!,
        )

        property = propertyRepository.save(property)
        return PropertyDTO.fromProperty(property)
    }

    suspend fun getProperties(): PageDTO<PropertyDTO> {
        val propertyPage = propertyRepository.getPage(Pageable.ofSize(50))
        return PageDTO.fromPage(propertyPage, PropertyDTO::fromProperty)
    }

    suspend fun getAgents(): PageDTO<AgentDTO> {
        val agentPage = agentRepository.getPage(Pageable.ofSize(10))
        return PageDTO.fromPage(agentPage, AgentDTO::fromAgent)
    }
}
