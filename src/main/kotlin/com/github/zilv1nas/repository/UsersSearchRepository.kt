package com.github.zilv1nas.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.zilv1nas.repository.model.User
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.support.IndicesOptions
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.unit.Fuzziness
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
class UsersSearchRepository(
    private val elasticSearch: RestHighLevelClient,
    private val objectMapper: ObjectMapper,
) {
    fun index(user: User) {
        val request = IndexRequest("users").apply {
            id(user.id.toString())
            source(objectMapper.writeValueAsString(user), XContentType.JSON)
        }
        elasticSearch.index(request, RequestOptions.DEFAULT)
    }

    fun search(query: String): List<User> {
        val searchSourceBuilder = SearchSourceBuilder()
            .query(QueryBuilders.multiMatchQuery(query, "name", "email").fuzziness(Fuzziness.AUTO))
        val searchRequest = SearchRequest("users")
            .source(searchSourceBuilder)
            .indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN)

        return elasticSearch.search(searchRequest, RequestOptions.DEFAULT)
            .hits
            .map { objectMapper.readValue(it.sourceAsString) }
    }
}