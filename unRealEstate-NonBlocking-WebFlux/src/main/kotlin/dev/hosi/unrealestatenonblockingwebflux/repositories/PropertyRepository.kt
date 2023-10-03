package dev.hosi.unrealestatenonblockingwebflux.repositories

import dev.hosi.unrealestatenonblockingwebflux.entities.Property
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class PropertyRepository(private val entityTemplate: R2dbcEntityTemplate) {
    fun save(property: Property): Mono<Property> {
        return entityTemplate.insert(property)
    }

    fun getPage(pageable: Pageable): Mono<PageImpl<Property>> {
        val itemsMono = entityTemplate.select(Property::class.java)
            .matching(Query.empty().limit(pageable.pageSize).offset(pageable.offset))
            .all()
            .collectList()
        val totalMono = entityTemplate.select(Property::class.java).count()
        return Mono.zip(itemsMono, totalMono).map { PageImpl(it.t1, pageable, it.t2) }
    }
}
