package dev.hosi.unrealestateblocking.dtos

import org.springframework.data.domain.Page

data class PageDTO<T>(
    val items: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
) {
    companion object {
        fun <U, T> fromPage(page: Page<U>, itemMapper: (item: U) -> T): PageDTO<T> {
            return PageDTO(
                items = page.content.map { itemMapper(it) },
                page = page.number,
                size = page.size,
                totalElements = page.totalElements,
                totalPages = page.totalPages,
            )
        }
    }
}
