package com.ajlabs.forevely.data.service

import com.ajlabs.forevely.data.GetIsPremiumQuery
import com.ajlabs.forevely.data.GetMyPlanQuery
import com.ajlabs.forevely.data.GetUserQuery
import com.ajlabs.forevely.data.UpdateUserMutation
import com.ajlabs.forevely.data.type.AboutMeInput
import com.ajlabs.forevely.data.type.Gender
import com.ajlabs.forevely.data.type.InterestsInput
import com.ajlabs.forevely.data.type.OpenQuestion
import com.ajlabs.forevely.data.type.PersonalDetailsUpdateInput
import com.ajlabs.forevely.data.type.ProfileUpdateInput
import com.ajlabs.forevely.data.type.UserUpdateInput
import com.ajlabs.forevely.data.utils.handle
import com.ajlabs.forevely.data.utils.skipIfNull
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.watch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface UserService {
    suspend fun getUser(): Flow<Result<GetUserQuery.Data>>
    suspend fun getMyPlan(): Result<GetMyPlanQuery.Data>

    suspend fun updateUser(
        email: String? = null,
        name: String? = null,
        username: String? = null,
        password: String? = null,
        profilePicture: String? = null,
        bio: String? = null,
        aboutMe: AboutMeInput? = null,
        interests: InterestsInput? = null,
        gender: Gender? = null,
        openQuestion: OpenQuestion? = null,
        pictures: List<String>? = null,
    ): Result<UpdateUserMutation.Data>

    suspend fun getIsPremium(): Result<GetIsPremiumQuery.Data>
}

internal class UserServiceImpl(private val apolloClient: ApolloClient) : UserService {
    override suspend fun getUser(): Flow<Result<GetUserQuery.Data>> {
        return apolloClient.query(GetUserQuery())
            .fetchPolicy(FetchPolicy.CacheAndNetwork)
            .watch()
            .map { it.handle() }
    }

    override suspend fun getMyPlan(): Result<GetMyPlanQuery.Data> {
        return apolloClient.query(GetMyPlanQuery())
            .fetchPolicy(FetchPolicy.CacheFirst)
            .handle()
    }

    override suspend fun updateUser(
        email: String?,
        name: String?,
        username: String?,
        password: String?,
        profilePicture: String?,
        bio: String?,
        aboutMe: AboutMeInput?,
        interests: InterestsInput?,
        gender: Gender?,
        openQuestion: OpenQuestion?,
        pictures: List<String>?,
    ): Result<UpdateUserMutation.Data> {
        val userUpdateInput = UserUpdateInput(
            email = email.skipIfNull(),
            password = password.skipIfNull()
        )

        val profileUpdateInput = ProfileUpdateInput(
            aboutMe = aboutMe.skipIfNull(),
            bio = bio.skipIfNull(),
            interests = interests.skipIfNull(),
            openQuestion = openQuestion.skipIfNull(),
            pictures = pictures.skipIfNull()

        )
        val personalDetailsUpdateInput = PersonalDetailsUpdateInput(
            name = name.skipIfNull(),
            gender = gender.skipIfNull()
        )
        return apolloClient.mutation(
            UpdateUserMutation(
                Optional.present(userUpdateInput),
                Optional.present(profileUpdateInput),
                Optional.present(personalDetailsUpdateInput)
            )
        )
            .fetchPolicy(FetchPolicy.CacheOnly)
            .handle()
    }

    override suspend fun getIsPremium(): Result<GetIsPremiumQuery.Data> {
        return apolloClient.query(GetIsPremiumQuery())
            .fetchPolicy(FetchPolicy.NetworkFirst)
            .handle()
    }
}
