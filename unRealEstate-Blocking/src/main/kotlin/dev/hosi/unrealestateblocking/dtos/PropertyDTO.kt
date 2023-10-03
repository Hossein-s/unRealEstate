package dev.hosi.unrealestateblocking.dtos

import dev.hosi.unrealestateblocking.entities.Property

data class PropertyDTO(
    val id: Long,
    val ownerName: String,
    val state: String,
    val city: String,
    val address: String,
    val price: Long,
    val description: String,
    val agentId: Long,
) {
    companion object {
        fun fromProperty(property: Property): PropertyDTO {
            return PropertyDTO(
                id = property.id!!,
                ownerName = property.ownerName,
                state = property.state,
                city = property.city,
                address = property.address,
                price = property.price,
                description = property.description,
                agentId = property.agentId,
            )
        }
    }
}
