package com.ajlabs.forevely.data.service

import com.ajlabs.forevely.data.GetMatchersPageQuery
import com.ajlabs.forevely.data.SaveSwipeMutation
import com.ajlabs.forevely.data.type.Children
import com.ajlabs.forevely.data.type.Diet
import com.ajlabs.forevely.data.type.Drinking
import com.ajlabs.forevely.data.type.Education
import com.ajlabs.forevely.data.type.Fitness
import com.ajlabs.forevely.data.type.Gender
import com.ajlabs.forevely.data.type.Language
import com.ajlabs.forevely.data.type.LoveLanguage
import com.ajlabs.forevely.data.type.MatcherFilterInput
import com.ajlabs.forevely.data.type.PageInput
import com.ajlabs.forevely.data.type.Personality
import com.ajlabs.forevely.data.type.Pet
import com.ajlabs.forevely.data.type.Politics
import com.ajlabs.forevely.data.type.Relationship
import com.ajlabs.forevely.data.type.Religion
import com.ajlabs.forevely.data.type.Smoking
import com.ajlabs.forevely.data.type.SwipeType
import com.ajlabs.forevely.data.type.Zodiac
import com.ajlabs.forevely.data.utils.handle
import com.ajlabs.forevely.data.utils.skipIfNull
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy

interface MatcherService {
    suspend fun getMatcherUsers(
        pageInput: PageInput,
        hasAgeMinLimit: Boolean?,
        hasAgeMaxLimit: Boolean?,
        ageMax: Int?,
        ageMin: Int?,
        children: List<Children>?,
        diets: List<Diet>?,
        drinkings: List<Drinking>?,
        educations: List<Education>?,
        fitnesses: List<Fitness>?,
        hasAgeSafeMargin: Boolean?,
        distanceMax: Int?,
        hasDistanceLimit: Boolean?,
        hasDistanceSafeMargin: Boolean?,
        genders: List<Gender>?,
        hasHeightMinLimit: Boolean?,
        hasHeightMaxLimit: Boolean?,
        heightMax: Int?,
        heightMin: Int?,
        heightSafeMargin: Boolean?,
        languages: List<Language>?,
        loveLanguages: List<LoveLanguage>?,
        personality: List<Personality>?,
        pets: List<Pet>?,
        politics: List<Politics>?,
        relationships: List<Relationship>?,
        religions: List<Religion>?,
        smokings: List<Smoking>?,
        verifiedOnly: Boolean?,
        zodiacs: List<Zodiac>?,
    ): Result<GetMatchersPageQuery.Data>

    suspend fun saveSwipe(swipeType: SwipeType, id: String): Result<SaveSwipeMutation.Data>
}

internal class MatcherServiceImpl(private val apolloClient: ApolloClient) : MatcherService {
    override suspend fun getMatcherUsers(
        pageInput: PageInput,
        hasAgeMinLimit: Boolean?,
        hasAgeMaxLimit: Boolean?,
        ageMax: Int?,
        ageMin: Int?,
        children: List<Children>?,
        diets: List<Diet>?,
        drinkings: List<Drinking>?,
        educations: List<Education>?,
        fitnesses: List<Fitness>?,
        hasAgeSafeMargin: Boolean?,
        distanceMax: Int?,
        hasDistanceLimit: Boolean?,
        hasDistanceSafeMargin: Boolean?,
        genders: List<Gender>?,
        hasHeightMinLimit: Boolean?,
        hasHeightMaxLimit: Boolean?,
        heightMax: Int?,
        heightMin: Int?,
        heightSafeMargin: Boolean?,
        languages: List<Language>?,
        loveLanguages: List<LoveLanguage>?,
        personality: List<Personality>?,
        pets: List<Pet>?,
        politics: List<Politics>?,
        relationships: List<Relationship>?,
        religions: List<Religion>?,
        smokings: List<Smoking>?,
        verifiedOnly: Boolean?,
        zodiacs: List<Zodiac>?,
    ): Result<GetMatchersPageQuery.Data> {
        val matcherFilterInput = MatcherFilterInput(
            hasAgeMinLimit = hasAgeMinLimit.skipIfNull(),
            hasAgeMaxLimit = hasAgeMaxLimit.skipIfNull(),
            ageMin = if (hasAgeMinLimit == true) ageMin.skipIfNull() else Optional.absent(),
            ageMax = if (hasAgeMaxLimit == true) ageMax.skipIfNull() else Optional.absent(),
            children = children.skipIfNull(),
            diets = diets.skipIfNull(),
            drinkings = drinkings.skipIfNull(),
            educations = educations.skipIfNull(),
            fitnesses = fitnesses.skipIfNull(),
            genders = genders.skipIfNull(),
            hasAgeSafeMargin = hasAgeSafeMargin.skipIfNull(),
            hasDistanceLimit = hasDistanceLimit.skipIfNull(),
            distanceMax = if (hasDistanceLimit == true) distanceMax.skipIfNull() else Optional.absent(),
            hasDistanceSafeMargin =
            if (hasDistanceLimit == true) hasDistanceSafeMargin.skipIfNull() else Optional.absent(),
            hasHeightMinLimit = if (hasHeightMinLimit == true) hasHeightMinLimit.skipIfNull() else Optional.absent(),
            hasHeightMaxLimit = if (hasHeightMaxLimit == true) hasHeightMaxLimit.skipIfNull() else Optional.absent(),
            heightMax = heightMax.skipIfNull(),
            heightMin = heightMin.skipIfNull(),
            heightSafeMargin = heightSafeMargin.skipIfNull(),
            languages = languages.skipIfNull(),
            loveLanguages = loveLanguages.skipIfNull(),
            personalities = personality.skipIfNull(),
            petPrefs = pets.skipIfNull(),
            politics = politics.skipIfNull(),
            relationship = relationships.skipIfNull(),
            religions = religions.skipIfNull(),
            smokings = smokings.skipIfNull(),
            verifiedProfilesOnly = verifiedOnly.skipIfNull(),
            zodiacs = zodiacs.skipIfNull(),
        )
        return apolloClient.query(GetMatchersPageQuery(matcherFilterInput, pageInput))
            .fetchPolicy(FetchPolicy.NetworkOnly)
            .handle()
    }

    override suspend fun saveSwipe(swipeType: SwipeType, id: String): Result<SaveSwipeMutation.Data> {
        return apolloClient.mutation(SaveSwipeMutation(swipeType, id)).handle()
    }
}
