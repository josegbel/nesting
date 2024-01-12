package com.ajlabs.forevely.data.service

import com.ajlabs.forevely.data.CreateConversationMutation
import com.ajlabs.forevely.data.GetConversationsPageQuery
import com.ajlabs.forevely.data.GetMatchedUsersWithoutConversationQuery
import com.ajlabs.forevely.data.WatchConversationsSubscription
import com.ajlabs.forevely.data.type.PageInput
import com.ajlabs.forevely.data.utils.handle
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

interface ConversationService {
    suspend fun getConversationsPage(pageInput: PageInput): Result<GetConversationsPageQuery.Data>
    suspend fun createConversation(matcherId: String): Result<CreateConversationMutation.Data>
    suspend fun getMarchedUsersWithoutConversations(pageInput: PageInput): Result<GetMatchedUsersWithoutConversationQuery.Data>
    suspend fun watchConversations(userId: String): Flow<ApolloResponse<WatchConversationsSubscription.Data>>
}

internal class ConversationServiceImpl(private val apolloClient: ApolloClient) : ConversationService {
    override suspend fun getConversationsPage(pageInput: PageInput): Result<GetConversationsPageQuery.Data> {
        return apolloClient.query(GetConversationsPageQuery(pageInput))
            .fetchPolicy(FetchPolicy.NetworkOnly)
            .handle()
    }

    override suspend fun createConversation(matcherId: String): Result<CreateConversationMutation.Data> {
        return apolloClient.mutation(CreateConversationMutation(matcherId))
            .fetchPolicy(FetchPolicy.NetworkOnly)
            .handle()
    }

    override suspend fun getMarchedUsersWithoutConversations(pageInput: PageInput): Result<GetMatchedUsersWithoutConversationQuery.Data> {
        return apolloClient.query(GetMatchedUsersWithoutConversationQuery(pageInput))
            .fetchPolicy(FetchPolicy.NetworkOnly)
            .handle()
    }

    override suspend fun watchConversations(userId: String): Flow<ApolloResponse<WatchConversationsSubscription.Data>> {
        return apolloClient.subscription(WatchConversationsSubscription(userId))
            .fetchPolicy(FetchPolicy.NetworkOnly)
            .toFlow()
            .onStart { println("watchConversations start") }
            .onCompletion { println("watchConversations end") }
            .onEach { println("watchConversations Conversation updated: ${it.data}") }
    }
}
