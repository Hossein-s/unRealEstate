package dev.hosi.unrealestateblocking.entities

import jakarta.persistence.*
import java.time.LocalDate

@Entity(name = "agents")
class Agent(
    val firstname: String,
    val lastname: String,
    val nickname: String,
    val email: String,
    val phone: String,
    val password: String,

    @Temporal(TemporalType.TIMESTAMP)
    val birthDate: LocalDate
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
