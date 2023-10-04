package dev.hosi.unrealestateblocking.services

import dev.hosi.unrealestateblocking.dtos.AgentDTO
import dev.hosi.unrealestateblocking.dtos.PageDTO
import dev.hosi.unrealestateblocking.dtos.PropertyDTO
import dev.hosi.unrealestateblocking.entities.Agent
import dev.hosi.unrealestateblocking.entities.Property
import dev.hosi.unrealestateblocking.repositories.AgentRepository
import dev.hosi.unrealestateblocking.repositories.PropertyRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class UnRealEstateService(
    private val agentRepository: AgentRepository,
    private val propertyRepository: PropertyRepository,
) {
    fun addAgent(
        firstname: String,
        lastname: String,
        nickname: String,
        email: String,
        phone: String,
        password: String,
        birthDate: LocalDate
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

    fun addProperty(
        agentId: Long,
        ownerName: String,
        state: String,
        city: String,
        address: String,
        price: Long,
        description: String,
    ): PropertyDTO {
        val agent = agentRepository.findById(agentId)
            .orElseThrow { throw IllegalArgumentException("Agent not found") }

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

    fun getProperties(): PageDTO<PropertyDTO> {
        val propertyPage = propertyRepository.findAll(Pageable.ofSize(50))
        return PageDTO.fromPage(propertyPage, PropertyDTO::fromProperty)
    }

    fun getAgents(): PageDTO<AgentDTO> {
        val agentPage = agentRepository.findAll(Pageable.ofSize(10))
        return PageDTO.fromPage(agentPage, AgentDTO::fromAgent)
    }
}
