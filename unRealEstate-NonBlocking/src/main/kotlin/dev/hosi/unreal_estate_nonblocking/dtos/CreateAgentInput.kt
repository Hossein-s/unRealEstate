package dev.hosi.unreal_estate_nonblocking.dtos

import java.time.LocalDateTime

data class CreateAgentInput(
    val firstname: String,
    val lastname: String,
    val nickname: String,
    val email: String,
    val phone: String,
    val password: String,
    val birthDate: LocalDateTime,
)
