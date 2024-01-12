package com.ajlabs.forevely.data.service

import com.ajlabs.forevely.data.GetMessagesPageQuery
import com.ajlabs.forevely.data.SendMessageMutation
import com.ajlabs.forevely.data.type.PageInput
import com.ajlabs.forevely.data.type.SendMessageInput
import com.ajlabs.forevely.data.utils.handle
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy

interface MessageService {
    suspend fun sendMessage(input: SendMessageInput): Result<SendMessageMutation.Data>
    suspend fun getMessagesPage(conversationId: String, pageInput: PageInput): Result<GetMessagesPageQuery.Data>
}

internal class MessageServiceImpl(private val apolloClient: ApolloClient) : MessageService {
    override suspend fun sendMessage(input: SendMessageInput): Result<SendMessageMutation.Data> {
        return apolloClient.mutation(SendMessageMutation(input))
//            .fetchPolicy(FetchPolicy.NetworkOnly)
            .handle()
    }

    override suspend fun getMessagesPage(
        conversationId: String,
        pageInput: PageInput,
    ): Result<GetMessagesPageQuery.Data> {
        return apolloClient.query(GetMessagesPageQuery(conversationId, pageInput))
            .fetchPolicy(FetchPolicy.NetworkOnly)
            .handle()
    }
}
