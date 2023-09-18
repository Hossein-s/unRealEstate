package dev.hosi.unreal_estate_blocking.dtos

import java.time.Instant

data class CreateAgentInput(
    val firstname: String,
    val lastname: String,
    val nickname: String,
    val email: String,
    val phone: String,
    val password: String,
    val birthDate: Instant,
)
