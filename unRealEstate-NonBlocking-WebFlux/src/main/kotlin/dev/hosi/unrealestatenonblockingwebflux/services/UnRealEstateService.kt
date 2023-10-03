package dev.hosi.unrealestatenonblockingwebflux.services

import dev.hosi.unrealestatenonblockingwebflux.dtos.AgentDTO
import dev.hosi.unrealestatenonblockingwebflux.dtos.PageDTO
import dev.hosi.unrealestatenonblockingwebflux.dtos.PropertyDTO
import dev.hosi.unrealestatenonblockingwebflux.entities.Agent
import dev.hosi.unrealestatenonblockingwebflux.entities.Property
import dev.hosi.unrealestatenonblockingwebflux.repositories.AgentRepository
import dev.hosi.unrealestatenonblockingwebflux.repositories.PropertyRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
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
        birthDate: LocalDate,
    ): Mono<AgentDTO> {
        val agent = Agent(
            firstname = firstname,
            lastname = lastname,
            nickname = nickname,
            email = email,
            phone = phone,
            password = password,
            birthDate = birthDate,
        )

        return agentRepository.save(agent)
            .map { AgentDTO.fromAgent(it) }
    }

    fun addProperty(
        agentId: Long,
        ownerName: String,
        state: String,
        city: String,
        address: String,
        price: Long,
        description: String,
    ): Mono<PropertyDTO> {
        return agentRepository.findById(agentId)
            .flatMap {
                propertyRepository.save(
                    Property(
                        ownerName = ownerName,
                        state = state,
                        city = city,
                        address = address,
                        price = price,
                        description = description,
                        agent = it,
                    )
                )
            }
            .map {
                PropertyDTO.fromProperty(it)
            }
    }

    fun getProperties(): Mono<PageDTO<PropertyDTO>> {
        return propertyRepository.getPage(Pageable.ofSize(50))
            .map { PageDTO.fromPage(it, PropertyDTO::fromProperty) }
    }

    fun getAgents(): Mono<PageDTO<AgentDTO>> {
        return agentRepository.getPage(Pageable.ofSize(10))
            .map { PageDTO.fromPage(it, AgentDTO::fromAgent) }
    }
}
