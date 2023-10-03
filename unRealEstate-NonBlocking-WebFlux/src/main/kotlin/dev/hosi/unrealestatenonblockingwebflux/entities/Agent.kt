package dev.hosi.unrealestatenonblockingwebflux.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table(name = "agents")
class Agent(
    val firstname: String,
    val lastname: String,
    val nickname: String,
    val email: String,
    val phone: String,
    val password: String,
    val birthDate: LocalDate
) {
    @Id
    var id: Long? = null
}
