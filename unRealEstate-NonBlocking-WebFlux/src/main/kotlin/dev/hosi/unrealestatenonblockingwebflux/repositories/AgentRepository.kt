package dev.hosi.unrealestatenonblockingwebflux.repositories

import dev.hosi.unrealestatenonblockingwebflux.entities.Agent
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class AgentRepository(private val entityTemplate: R2dbcEntityTemplate) {
    fun save(agent: Agent): Mono<Agent> {
        return entityTemplate.insert(agent)
    }

    fun getPage(pageable: Pageable): Mono<PageImpl<Agent>> {
        val itemsMono = entityTemplate.select(Agent::class.java)
            .matching(Query.empty().limit(pageable.pageSize).offset(pageable.offset))
            .all()
            .collectList()
        val totalMono = entityTemplate.select(Agent::class.java).count()
        return Mono.zip(itemsMono, totalMono).map { PageImpl(it.t1, pageable, it.t2) }
    }

    fun findById(id: Long): Mono<Agent> {
        return entityTemplate.selectOne(
            Query.query(where("id").`is`(id)),
            Agent::class.java
        )
    }
}
