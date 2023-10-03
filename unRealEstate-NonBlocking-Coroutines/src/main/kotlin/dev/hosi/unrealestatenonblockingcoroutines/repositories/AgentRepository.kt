package dev.hosi.unrealestatenonblockingcoroutines.repositories

import dev.hosi.unrealestatenonblockingcoroutines.entities.Agent
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2

@Repository
class AgentRepository(private val entityTemplate: R2dbcEntityTemplate) {
    suspend fun save(agent: Agent): Agent {
        return entityTemplate.insert(agent).awaitSingle()
    }

    suspend fun getPage(pageable: Pageable): Page<Agent> {
        val itemsMono = entityTemplate.select(Agent::class.java)
            .matching(Query.empty().limit(pageable.pageSize).offset(pageable.offset))
            .all()
            .collectList()
        val totalMono = entityTemplate.select(Agent::class.java).count()
        val (items, total) = Mono.zip(itemsMono, totalMono).awaitSingle()

        return PageImpl(items, pageable, total)
    }

    suspend fun findById(id: Long): Agent? {
        return entityTemplate.selectOne(
            Query.query(where("id").`is`(id)),
            Agent::class.java
        ).awaitSingle()
    }
}
