package com.ajlabs.forevely.data.service

import co.touchlab.kermit.Logger
import com.ajlabs.forevely.data.ForgotPasswordQuery
import com.ajlabs.forevely.data.LoginMutation
import com.ajlabs.forevely.data.RegisterMutation
import com.ajlabs.forevely.data.type.AuthInput
import com.ajlabs.forevely.data.utils.handle
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.apolloStore
import com.russhwolf.settings.Settings
import com.russhwolf.settings.nullableString
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive

interface AuthService {
    var userId: String?
    suspend fun login(email: String, password: String): Result<LoginMutation.Data>
    suspend fun register(email: String, password: String): Result<RegisterMutation.Data>
    suspend fun forgotPassword(email: String): Result<ForgotPasswordQuery.Data>
    suspend fun signOut()
    fun checkAuth(): Boolean
    suspend fun observeToken(): Flow<String?>
}

internal class AuthServiceImpl(
    private val logger: Logger,
    private val apolloClient: ApolloClient,
    settings: Settings,
) : AuthService {
    override var userId by settings.nullableString()
    private var token by settings.nullableString()

    override suspend fun login(email: String, password: String): Result<LoginMutation.Data> {
        val authInput = AuthInput(email, password)
        return apolloClient.mutation(LoginMutation(authInput)).handle {
            saveData(it.login.userMinimal.id.toString(), it.login.token)
        }
    }

    override suspend fun register(email: String, password: String): Result<RegisterMutation.Data> {
        val authInput = AuthInput(email, password)
        return apolloClient.mutation(RegisterMutation(authInput)).handle {
            saveData(it.register.userMinimal.id.toString(), it.register.token)
        }
    }

    override suspend fun forgotPassword(email: String): Result<ForgotPasswordQuery.Data> {
        return apolloClient.query(ForgotPasswordQuery(email)).handle()
    }

    override suspend fun signOut() {
        apolloClient.apolloStore.clearAll()
        userId = null
        token = null
    }

    override fun checkAuth(): Boolean {
        val isAuth = token != null
        logger.d { "User is authenticated: $isAuth" }
        return isAuth
    }

    override suspend fun observeToken(): Flow<String?> = flow {
        while (currentCoroutineContext().isActive) {
            emit(token)
            delay(16)
        }
    }
        .distinctUntilChanged()
        .onEach { logger.d("UserId: $userId\nToken: $it") }

    private fun saveData(userId: String, token: String): String? {
        this.userId = userId
        this.token = token
        return this.token
    }
}
