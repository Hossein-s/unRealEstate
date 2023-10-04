package dev.hosi.unrealestateblocking.dtos

import java.time.LocalDate

data class CreateAgentInput(
    val firstname: String,
    val lastname: String,
    val nickname: String,
    val email: String,
    val phone: String,
    val password: String,
    val birthDate: LocalDate,
)
