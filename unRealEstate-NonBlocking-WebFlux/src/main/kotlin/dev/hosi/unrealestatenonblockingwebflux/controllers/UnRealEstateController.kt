package dev.hosi.unrealestatenonblockingwebflux.controllers

import dev.hosi.unrealestatenonblockingwebflux.dtos.*
import dev.hosi.unrealestatenonblockingwebflux.services.UnRealEstateService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class UnRealEstateController(private val unRealEstateService: UnRealEstateService) {
    @PostMapping("/agents")
    fun createAgent(@RequestBody input: CreateAgentInput): Mono<AgentDTO> {
        return unRealEstateService.addAgent(
            firstname = input.firstname,
            lastname = input.lastname,
            nickname = input.nickname,
            email = input.email,
            phone = input.phone,
            password = input.password,
            birthDate = input.birthDate,
        )
    }

    @PostMapping("/properties")
    fun createProperty(@RequestBody input: CreatePropertyInput): Mono<PropertyDTO> {
        return unRealEstateService.addProperty(
            agentId = input.agentId,
            ownerName = input.ownerName,
            state = input.state,
            city = input.city,
            address = input.address,
            price = input.price,
            description = input.description,
        )
    }

    @GetMapping("/agents")
    fun getAgents(): Mono<PageDTO<AgentDTO>> {
        return unRealEstateService.getAgents()
    }

    @GetMapping("/properties")
    fun getProperties(): Mono<PageDTO<PropertyDTO>> {
        return unRealEstateService.getProperties()
    }
}
