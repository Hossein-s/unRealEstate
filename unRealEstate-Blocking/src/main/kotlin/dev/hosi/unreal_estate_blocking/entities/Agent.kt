package dev.hosi.unreal_estate_blocking.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity(name = "agents")
class Agent(
    val firstname: String,

    val lastname: String,

    val nickname: String,

    val email: String,

    val phone: String,

    val password: String,

    @Temporal(TemporalType.TIMESTAMP)
    val birthDate: Instant
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
