package dev.hosi.unrealestateblocking.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "properties")
class Property(
    val ownerName: String,
    val state: String,
    val city: String,
    val address: String,
    val price: Long,
    val description: String,
    val agentId: Long,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
