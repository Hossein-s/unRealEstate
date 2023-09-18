package dev.hosi.unreal_estate_nonblocking.repositories

import dev.hosi.unreal_estate_nonblocking.entities.Property
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2

@Repository
class PropertyRepository(private val entityTemplate: R2dbcEntityTemplate) {
    suspend fun save(property: Property): Property {
        return entityTemplate.insert(property).awaitSingle()
    }

    suspend fun getPage(pageable: Pageable): Page<Property> {
        val itemsMono = entityTemplate.select(Property::class.java)
            .matching(Query.empty().limit(pageable.pageSize).offset(pageable.offset))
            .all()
            .collectList()
        val totalMono = entityTemplate.select(Property::class.java).count()
        val (items, total) = Mono.zip(itemsMono, totalMono).awaitSingle()

        return PageImpl(items, pageable, total)
    }
}
