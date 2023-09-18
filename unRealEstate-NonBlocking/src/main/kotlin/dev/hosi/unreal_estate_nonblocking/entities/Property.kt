package dev.hosi.unreal_estate_nonblocking.entities

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "properties")
class Property(
    val ownerName: String,

    val state: String,

    val city: String,

    val address: String,

    val price: Long,

    val description: String,

    val agent: Agent,
) {
    @Id
    var id: Long? = null
}
