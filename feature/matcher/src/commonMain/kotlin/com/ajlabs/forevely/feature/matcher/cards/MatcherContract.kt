package com.ajlabs.forevely.feature.matcher.cards

import com.ajlabs.forevely.data.GetMatchersPageQuery
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
import com.ajlabs.forevely.data.type.SwipeType
import com.ajlabs.forevely.data.type.Zodiac
import com.ajlabs.forevely.feature.matcher.cards.model.MatcherFilters
import org.koin.core.component.KoinComponent

object MatcherContract : KoinComponent {
    data class State(
        val isLoading: Boolean = false,
        val isUndoEnabled: Boolean = false,
        // TODO: Take availableUndo from current user premium status
        val availableUndo: Int = 10,
        val progress: Float = 0f,
        val visibleItemIndex: Int = 0,
        val isLocationEnabled: Boolean = false,

        val matchers: List<GetMatchersPageQuery.Matcher> = emptyList(),
        val pageInfo: GetMatchersPageQuery.Info = GetMatchersPageQuery.Info(0, 0, null, null),

        // Config
        val distanceRangeMax: Float = 300f,
        val heightRange: ClosedFloatingPointRange<Float> = 100f..230f,
        val ageRange: ClosedFloatingPointRange<Float> = 18f..100f,

        // Filters
        val defaultFilters: MatcherFilters = MatcherFilters(
            maxDistanceAway = distanceRangeMax,
            ageChosenRange = ageRange,
            heightChosenRange = heightRange,
        ),
        val filters: MatcherFilters = MatcherFilters(),

        val filterTab: FilterTab = FilterTab.BASIC,

        val isPremium: Boolean = false,
        val advancedFilters: List<AdvancedFilter> = emptyList(),
    )

    sealed interface Inputs {
        // Loading matches
        data class SetIsLoading(val isLoading: Boolean) : Inputs

        data object ObserveLocationStatus : Inputs
        data class SetIsLocationEnabled(val isLocationEnabled: Boolean) : Inputs

        data object Init : Inputs
        data class FetchMatches(val page: Int) : Inputs
        data object LoadData : Inputs
        data class SetIsPremium(val isPremium: Boolean) : Inputs
        data class OnGenderClicked(val gender: Gender) : Inputs
        data class SetDefaultFilters(val defaultFilters: MatcherFilters) : Inputs

        data class SetMatches(val profiles: List<GetMatchersPageQuery.Matcher>) : Inputs
        data class SetPageInfo(val pageInfo: GetMatchersPageQuery.Info) : Inputs
        data object FetchNextPage : Inputs

        // Handling matches
        data class OnVisibleItemIndexChanged(val index: Int) : Inputs
        data class SetVisibleItemIndex(val index: Int) : Inputs
        data class OnSwipe(val swipeType: SwipeType, val index: Int) : Inputs
        data class SetProgress(val progress: Float) : Inputs

        // Handle Undo
        data class SetIsUndoEnabled(val isEnabled: Boolean) : Inputs
        data class SetAvailableUndo(val availableUndo: Int) : Inputs
        data object OnUndoClicked : Inputs

        // Filters
        data object ApplyFilters : Inputs

        data object OnGetPremiumClicked : Inputs

        data class SetFilterType(val filterTab: FilterTab) : Inputs

        // Basic filters
        data class OnRelationshipClicked(val relationship: Relationship) : Inputs

        data class SetAgeChosenRange(val ageChosenRange: ClosedFloatingPointRange<Float>) : Inputs
        data class SetHasAgeSafeMargin(val hasAgeSafeMargin: Boolean) : Inputs

        data class SetHasDistanceLimit(val hasDistanceLimit: Boolean) : Inputs
        data class SetMaxDistanceAway(val maxDistanceAway: Float) : Inputs
        data class SetHasDistanceSafeMargin(val hasDistanceSafeMargin: Boolean) : Inputs

        data class OnLanguageClicked(val language: Language) : Inputs

        // Advanced filters
        data class OnAdvancedFilterClicked(val advancedFilter: AdvancedFilter) : Inputs
        data object OnVerifiedOnlyClicked : Inputs
        data class SetHeightChosenRange(val heightChosenRange: ClosedFloatingPointRange<Float>) : Inputs
        data class SetHeightSafeRange(val heightShowOthersIfRunOut: Boolean) : Inputs
        data class OnFitnessClicked(val fitness: Fitness) : Inputs
        data class OnEducationClicked(val education: Education) : Inputs
        data class OnDrinkingClicked(val drinking: Drinking) : Inputs
        data class OnSmokingClicked(val smoking: Smoking) : Inputs
        data class OnChildClicked(val child: Children) : Inputs
        data class OnZodiacClicked(val zodiac: Zodiac) : Inputs
        data class OnPoliticClicked(val politic: Politics) : Inputs
        data class OnReligionClicked(val religion: Religion) : Inputs
        data class OnDietClicked(val diet: Diet) : Inputs
        data class OnLoveLanguageClicked(val loveLanguage: LoveLanguage) : Inputs
        data class OnPersonalityClicked(val personality: Personality) : Inputs
        data class OnPetClicked(val pet: Pet) : Inputs

        // Config
        data class SetAgeRange(val ageRange: ClosedFloatingPointRange<Float>) : Inputs
        data class SetDistanceRangeMax(val distanceRangeMax: Float) : Inputs
        data class SetHeightRange(val heightRange: ClosedFloatingPointRange<Float>) : Inputs
    }

    sealed interface Events {
        data class OnError(val message: String) : Events
        data object ShowGetPremium : Events
    }
}

enum class AboutMeAttr {
    HEIGHT,
    DIET,
    DRINKING,
    EDUCATION,
    FITNESS,
    LOVE_LANGUAGE,
    PERSONALITY,
    PETS,
    POLITICS,
    RELATIONSHIP,
    SMOKING,
    CHILDREN,
    ZODIAC,
    RELIGION,
}

enum class FilterTab { BASIC, ADVANCED }

enum class AdvancedFilter {
    HEIGHT,
    FITNESS,
    EDUCATION,
    DRINKING,
    SMOKING,
    CHILDREN,
    ZODIAC,
    RELIGION,
    POLITICS,
    DIET,
    LOVE_LANGUAGE,
    PERSONALITY,
    PETS,
    VERIFIED_ONLY,
}
