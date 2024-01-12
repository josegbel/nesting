package com.ajlabs.forevely.auth

import com.ajlabs.forevely.data.LoginMutation
import com.ajlabs.forevely.data.type.AuthInput
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest

@OptIn(ApolloExperimental::class)
class LoginTests {
    private val apolloClient: ApolloClient = ApolloClient.Builder()
        .networkTransport(QueueTestNetworkTransport())
        .build()

    @Test
    fun `example apollo test`() = runTest {
        val authInput = AuthInput(
            email = "maibuddy@ajlabs.com",
            password = "P@ss1234",
        )
        val testQuery = LoginMutation(authInput)
        val testData = LoginMutation.Data(
            LoginMutation.Login(
                token = "test_token",
                userMinimal = LoginMutation.UserMinimal(
                    id = "test_id",
                    email = "test_email",
                )
            ),
        )

        apolloClient.enqueueTestResponse(testQuery, testData)

        val actual = apolloClient.mutation(testQuery).execute().data!!
        assertEquals(testData.login.token, actual.login.token)
    }
}
