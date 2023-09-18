package dev.hosi.unreal_estate_nonblocking.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "agents")
class Agent(
    val firstname: String,

    val lastname: String,

    val nickname: String,

    val email: String,

    val phone: String,

    val password: String,

    val birthDate: LocalDateTime
) {
    @Id
    var id: Long? = null
}
