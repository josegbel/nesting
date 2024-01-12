package com.ajlabs.forevely.feature.matcher.cards.model

import com.ajlabs.forevely.data.type.Children
import com.ajlabs.forevely.data.type.Diet
import com.ajlabs.forevely.data.type.Drinking
import com.ajlabs.forevely.data.type.Education
import com.ajlabs.forevely.data.type.Fitness
import com.ajlabs.forevely.data.type.Gender
import com.ajlabs.forevely.data.type.Language
import com.ajlabs.forevely.data.type.LoveLanguage
import com.ajlabs.forevely.data.type.Personality
import com.ajlabs.forevely.data.type.Pet
import com.ajlabs.forevely.data.type.Politics
import com.ajlabs.forevely.data.type.Relationship
import com.ajlabs.forevely.data.type.Religion
import com.ajlabs.forevely.data.type.Smoking
import com.ajlabs.forevely.data.type.Zodiac

data class MatcherFilters(
    val relationships: List<Relationship> = emptyList(),
    val ageChosenRange: ClosedFloatingPointRange<Float> = 18f..100f,
    val hasAgeSafeMargin: Boolean = true,
    val hasDistanceLimit: Boolean = true, // Only in the client
    val maxDistanceAway: Float = 300f,
    val hasDistanceSafeMargin: Boolean = true,
    val languages: List<Language> = emptyList(),
    val verifiedOnly: Boolean = true,
    val genders: List<Gender> = emptyList(),
    val heightChosenRange: ClosedFloatingPointRange<Float> = 100f..230f,
    val heightSafeMargin: Boolean = true,
    val fitnesses: List<Fitness> = emptyList(),
    val educations: List<Education> = emptyList(),
    val drinkings: List<Drinking> = emptyList(),
    val smokings: List<Smoking> = emptyList(),
    val children: List<Children> = emptyList(),
    val zodiacs: List<Zodiac> = emptyList(),
    val politics: List<Politics> = emptyList(),
    val religions: List<Religion> = emptyList(),
    val diets: List<Diet> = emptyList(),
    val loveLanguages: List<LoveLanguage> = emptyList(),
    val personalities: List<Personality> = emptyList(),
    val pets: List<Pet> = emptyList(),
)
