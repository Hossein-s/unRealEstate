package dev.hosi.unrealestateblocking.entities

import jakarta.persistence.*

@Entity(name = "properties")
class Property(
    val ownerName: String,
    val state: String,
    val city: String,
    val address: String,
    val price: Long,
    val description: String,

    @ManyToOne
    val agent: Agent,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
