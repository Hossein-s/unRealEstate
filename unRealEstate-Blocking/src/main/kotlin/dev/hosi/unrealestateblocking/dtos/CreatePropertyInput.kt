package dev.hosi.unrealestateblocking.dtos

data class CreatePropertyInput(
    val agentId: Long,
    val ownerName: String,
    val state: String,
    val city: String,
    val address: String,
    val price: Long,
    val description: String,
)
