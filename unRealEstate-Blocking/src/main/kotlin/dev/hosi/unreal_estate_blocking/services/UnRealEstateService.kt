package dev.hosi.unreal_estate_blocking.services

import dev.hosi.unreal_estate_blocking.dtos.AgentDTO
import dev.hosi.unreal_estate_blocking.dtos.PageDTO
import dev.hosi.unreal_estate_blocking.dtos.PropertyDTO
import dev.hosi.unreal_estate_blocking.entities.Agent
import dev.hosi.unreal_estate_blocking.entities.Property
import dev.hosi.unreal_estate_blocking.repositories.AgentRepository
import dev.hosi.unreal_estate_blocking.repositories.PropertyRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.Instant

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
        birthDate: Instant
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
            agent = agent,
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
