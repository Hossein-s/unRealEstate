package dev.hosi.unreal_estate_blocking.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

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

    @UpdateTimestamp
    val updatedAt: Instant = Instant.now()

    @CreationTimestamp
    val createdAt: Instant = Instant.now()
}
