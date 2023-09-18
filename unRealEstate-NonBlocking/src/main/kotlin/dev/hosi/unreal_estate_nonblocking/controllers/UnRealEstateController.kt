package dev.hosi.unreal_estate_nonblocking.controllers

import dev.hosi.unreal_estate_nonblocking.services.UnRealEstateService
import dev.hosi.unreal_estate_nonblocking.dtos.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UnRealEstateController(private val unRealEstateService: UnRealEstateService) {
    @PostMapping("/agents")
    suspend fun createAgent(@RequestBody input: CreateAgentInput): AgentDTO {
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
    suspend fun createProperty(input: CreatePropertyInput): PropertyDTO {
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
    suspend fun getAgents(): PageDTO<AgentDTO> {
        return unRealEstateService.getAgents()
    }

    @GetMapping("/properties")
    suspend fun getProperties(): PageDTO<PropertyDTO> {
        return unRealEstateService.getProperties()
    }
}
