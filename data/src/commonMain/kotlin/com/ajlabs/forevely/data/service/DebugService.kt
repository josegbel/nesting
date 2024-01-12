package com.ajlabs.forevely.data.service

import com.ajlabs.forevely.data.DeleteAllUsersQuery
import com.ajlabs.forevely.data.DeleteGeneratedUsersQuery
import com.ajlabs.forevely.data.GenerateUsersQuery
import com.ajlabs.forevely.data.SetIsPremiumMutation
import com.ajlabs.forevely.data.utils.handle
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.apolloStore
import com.apollographql.apollo3.cache.normalized.optimisticUpdates

interface DebugService {
    suspend fun generateUsers(target: Int = 100): Result<GenerateUsersQuery.Data>
    suspend fun deleteAllUsers(): Result<DeleteAllUsersQuery.Data>
    suspend fun deleteGeneratedUsers(): Result<DeleteGeneratedUsersQuery.Data>
    suspend fun clearCache(): Result<Boolean>
    suspend fun setIsPremium(isPremium: Boolean): Result<SetIsPremiumMutation.Data>
}

internal class DebugServiceImpl(private val apolloClient: ApolloClient) : DebugService {
    override suspend fun generateUsers(target: Int): Result<GenerateUsersQuery.Data> {
        return apolloClient.query(GenerateUsersQuery(target)).handle()
    }

    override suspend fun deleteAllUsers(): Result<DeleteAllUsersQuery.Data> {
        return apolloClient.query(DeleteAllUsersQuery()).handle()
    }

    override suspend fun deleteGeneratedUsers(): Result<DeleteGeneratedUsersQuery.Data> {
        return apolloClient.query(DeleteGeneratedUsersQuery()).handle()
    }

    override suspend fun clearCache(): Result<Boolean> = runCatching {
        apolloClient.apolloStore.clearAll()
    }

    override suspend fun setIsPremium(isPremium: Boolean): Result<SetIsPremiumMutation.Data> {
        return apolloClient.mutation(SetIsPremiumMutation(isPremium))
            .optimisticUpdates(
                SetIsPremiumMutation.Data(
                    debugSetIsPremium = SetIsPremiumMutation.DebugSetIsPremium(
                        isPremium = isPremium
                    ),
                )
            )
            .handle()
    }
}
