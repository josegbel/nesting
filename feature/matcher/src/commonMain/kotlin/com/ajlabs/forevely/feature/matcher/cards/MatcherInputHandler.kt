package com.ajlabs.forevely.feature.matcher.cards

import com.ajlabs.forevely.core.util.infiniteFlow
import com.ajlabs.forevely.data.SettingsType
import com.ajlabs.forevely.data.service.MatcherService
import com.ajlabs.forevely.data.service.UserService
import com.ajlabs.forevely.data.type.PageInput
import com.ajlabs.forevely.data.type.SwipeType
import com.ajlabs.forevely.data.utils.update
import com.ajlabs.forevely.location.LocationService
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.russhwolf.settings.Settings
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

private const val PAGE_SIZE = 10
private typealias MatcherInputScope =
    InputHandlerScope<MatcherContract.Inputs, MatcherContract.Events, MatcherContract.State>

internal class MatcherInputHandler :
    KoinComponent,
    InputHandler<MatcherContract.Inputs, MatcherContract.Events, MatcherContract.State> {

    private val settings by inject<Settings>(named(SettingsType.SETTINGS_NON_ENCRYPTED.name))
    private val matcherService by inject<MatcherService>()
    private val userService by inject<UserService>()
    private val locationService by inject<LocationService>()

    override suspend fun MatcherInputScope.handleInput(
        input: MatcherContract.Inputs,
    ) = when (input) {
        // Loading matches
        is MatcherContract.Inputs.SetIsLoading -> updateState { it.copy(isLoading = input.isLoading) }

        MatcherContract.Inputs.Init -> handleInit()
        is MatcherContract.Inputs.FetchMatches -> fetchMatches(input.page)
        is MatcherContract.Inputs.SetPageInfo -> updateState { it.copy(pageInfo = input.pageInfo) }
        MatcherContract.Inputs.FetchNextPage -> handleFetchNextPage()

        // Handling matches
        is MatcherContract.Inputs.OnVisibleItemIndexChanged -> onVisibleItemIndexChanged(input.index)
        is MatcherContract.Inputs.SetVisibleItemIndex -> updateState { it.copy(visibleItemIndex = input.index) }
        is MatcherContract.Inputs.SetProgress -> setProgress(input)
        is MatcherContract.Inputs.OnSwipe -> onSwipe(input.swipeType, input.index)

        // Handle Undo
        is MatcherContract.Inputs.SetIsUndoEnabled -> setIsUndoEnabled(input)
        is MatcherContract.Inputs.SetAvailableUndo -> setAvailableUndo(input)
        MatcherContract.Inputs.OnUndoClicked -> onUndoClicked()
        is MatcherContract.Inputs.SetMatches -> updateState { it.copy(matchers = input.profiles) }
        MatcherContract.Inputs.ApplyFilters -> handleApplyFilters()

        // Filters
        MatcherContract.Inputs.LoadData -> handleLoadData()
        is MatcherContract.Inputs.OnGetPremiumClicked -> handleOnGetPremiumClicked()
        is MatcherContract.Inputs.SetFilterType -> updateState { it.copy(filterTab = input.filterTab) }

        is MatcherContract.Inputs.OnRelationshipClicked ->
            updateState {
                it.copy(filters = it.filters.copy(relationships = it.filters.relationships update input.relationship))
            }

        is MatcherContract.Inputs.SetAgeChosenRange ->
            updateState { it.copy(filters = it.filters.copy(ageChosenRange = input.ageChosenRange)) }

        is MatcherContract.Inputs.SetHasAgeSafeMargin ->
            updateState { it.copy(filters = it.filters.copy(hasAgeSafeMargin = input.hasAgeSafeMargin)) }

        is MatcherContract.Inputs.SetHasDistanceLimit ->
            updateState { it.copy(filters = it.filters.copy(hasDistanceLimit = input.hasDistanceLimit)) }

        is MatcherContract.Inputs.SetMaxDistanceAway ->
            updateState { it.copy(filters = it.filters.copy(maxDistanceAway = input.maxDistanceAway)) }

        is MatcherContract.Inputs.SetHasDistanceSafeMargin ->
            updateState { it.copy(filters = it.filters.copy(hasDistanceSafeMargin = input.hasDistanceSafeMargin)) }

        is MatcherContract.Inputs.OnLanguageClicked ->
            updateState { it.copy(filters = it.filters.copy(languages = it.filters.languages update input.language)) }

        is MatcherContract.Inputs.SetIsPremium -> updateState { it.copy(isPremium = !it.isPremium) }
        MatcherContract.Inputs.OnVerifiedOnlyClicked ->
            updateState { it.copy(filters = it.filters.copy(verifiedOnly = !it.filters.verifiedOnly)) }

        is MatcherContract.Inputs.SetHeightRange -> updateState { it.copy(heightRange = input.heightRange) }
        is MatcherContract.Inputs.SetHeightChosenRange ->
            updateState { it.copy(filters = it.filters.copy(heightChosenRange = input.heightChosenRange)) }

        is MatcherContract.Inputs.SetHeightSafeRange ->
            updateState { it.copy(filters = it.filters.copy(heightSafeMargin = input.heightShowOthersIfRunOut)) }

        is MatcherContract.Inputs.OnAdvancedFilterClicked ->
            updateState { it.copy(advancedFilters = it.advancedFilters update input.advancedFilter) }

        is MatcherContract.Inputs.OnFitnessClicked ->
            updateState { it.copy(filters = it.filters.copy(fitnesses = it.filters.fitnesses update input.fitness)) }

        is MatcherContract.Inputs.OnDrinkingClicked ->
            updateState { it.copy(filters = it.filters.copy(drinkings = it.filters.drinkings update input.drinking)) }

        is MatcherContract.Inputs.OnEducationClicked ->
            updateState {
                it.copy(filters = it.filters.copy(educations = it.filters.educations update input.education))
            }

        is MatcherContract.Inputs.OnSmokingClicked ->
            updateState { it.copy(filters = it.filters.copy(smokings = it.filters.smokings update input.smoking)) }

        is MatcherContract.Inputs.OnChildClicked ->
            updateState { it.copy(filters = it.filters.copy(children = it.filters.children update input.child)) }

        is MatcherContract.Inputs.OnZodiacClicked ->
            updateState { it.copy(filters = it.filters.copy(zodiacs = it.filters.zodiacs update input.zodiac)) }

        is MatcherContract.Inputs.OnPoliticClicked ->
            updateState { it.copy(filters = it.filters.copy(politics = it.filters.politics update input.politic)) }

        is MatcherContract.Inputs.OnReligionClicked ->
            updateState { it.copy(filters = it.filters.copy(religions = it.filters.religions update input.religion)) }

        is MatcherContract.Inputs.OnDietClicked ->
            updateState { it.copy(filters = it.filters.copy(diets = it.filters.diets update input.diet)) }

        is MatcherContract.Inputs.OnPetClicked ->
            updateState { it.copy(filters = it.filters.copy(pets = it.filters.pets update input.pet)) }

        is MatcherContract.Inputs.OnLoveLanguageClicked ->
            updateState {
                it.copy(filters = it.filters.copy(loveLanguages = it.filters.loveLanguages update input.loveLanguage))
            }

        is MatcherContract.Inputs.OnPersonalityClicked ->
            updateState {
                it.copy(filters = it.filters.copy(personalities = it.filters.personalities update input.personality))
            }

        // Config
        is MatcherContract.Inputs.SetAgeRange -> updateState { it.copy(ageRange = input.ageRange) }
        is MatcherContract.Inputs.SetDistanceRangeMax ->
            updateState { it.copy(distanceRangeMax = input.distanceRangeMax) }

        is MatcherContract.Inputs.OnGenderClicked -> updateState {
            it.copy(filters = it.filters.copy(genders = it.filters.genders update input.gender))
        }

        is MatcherContract.Inputs.SetDefaultFilters -> updateState { it.copy(defaultFilters = input.defaultFilters) }

        is MatcherContract.Inputs.ObserveLocationStatus -> observeLocationStatus()
        is MatcherContract.Inputs.SetIsLocationEnabled ->
            updateState { it.copy(isLocationEnabled = input.isLocationEnabled) }
    }

    private suspend fun MatcherInputScope.observeLocationStatus() {
        sideJob("observeLocationStatus") {
            infiniteFlow { locationService.checkPermission() }.collect { locationStatus ->
                postInput(MatcherContract.Inputs.SetIsLocationEnabled(locationStatus.isGranted()))
            }
        }
    }

    private suspend fun MatcherInputScope.handleInit() {
        sideJob("handleInit") {
            postInput(MatcherContract.Inputs.SetIsLoading(true))

            postInput(MatcherContract.Inputs.LoadData)
            postInput(MatcherContract.Inputs.FetchMatches(0))
            postInput(MatcherContract.Inputs.ObserveLocationStatus)

            postInput(MatcherContract.Inputs.SetIsLoading(false))
        }
    }

    private suspend fun MatcherInputScope.handleLoadData() {
        val state = getCurrentState()
        sideJob("handleLoadData") {
            // TODO: Get age min range from the server
            val ageRange = 18f..100f
            postInput(MatcherContract.Inputs.SetAgeRange(ageRange))

            // TODO: Load data from the server
            val isPremium = true
            postInput(MatcherContract.Inputs.SetIsPremium(isPremium))

            userService.getUser().collect { result ->
                result.onSuccess { data ->
                    val user = data.getUser
                    postInput(MatcherContract.Inputs.SetIsPremium(user.isPremium))

                    user.profile.interests?.genders?.let { genders ->
                        val newFilters = state.defaultFilters.copy(genders = genders)
                        postInput(MatcherContract.Inputs.SetDefaultFilters(newFilters))
                    }
                }
            }

            // TODO: Height range will need to come from the server too
            val heightRange = 100f..230f
            postInput(MatcherContract.Inputs.SetHeightRange(heightRange))

            val distanceRangeMax = 100f
            postInput(MatcherContract.Inputs.SetDistanceRangeMax(distanceRangeMax))
        }
    }

    private suspend fun MatcherInputScope.handleApplyFilters() {
        sideJob("handleApplyFilters") {
            postInput(MatcherContract.Inputs.SetIsLoading(true))

            postInput(MatcherContract.Inputs.SetMatches(emptyList()))
            postInput(MatcherContract.Inputs.FetchMatches(0))

            postInput(MatcherContract.Inputs.SetIsLoading(false))
        }
    }

    private suspend fun MatcherInputScope.handleFetchNextPage() {
        val state = getCurrentState()
        sideJob("handleFetchNextPage") {
            if (state.visibleItemIndex + 3 >= state.matchers.size) {
                state.pageInfo.next?.let { next ->
                    postInput(MatcherContract.Inputs.FetchMatches(next))
                } ?: run {
                    postInput(MatcherContract.Inputs.SetIsLoading(false))

                    // FIXME: Here we can send event to show no-matches-page with button to tell backend to expand filters
                    postEvent(MatcherContract.Events.OnError("No more pages to fetch"))
                }
            }
        }
    }

    private suspend fun MatcherInputScope.fetchMatches(page: Int) {
        with(getCurrentState()) {
            sideJob("fetchMatches") {
                val pageInput = PageInput(page, PAGE_SIZE)

                matcherService.getMatcherUsers(
                    pageInput = pageInput,
                    hasAgeMinLimit = ageRange.start != filters.ageChosenRange.start,
                    hasAgeMaxLimit = ageRange.endInclusive != filters.ageChosenRange.endInclusive,
                    ageMin = defaultFilters.ageChosenRange.start.toInt()
                        diff filters.ageChosenRange.start.toInt(),
                    ageMax = defaultFilters.ageChosenRange.endInclusive.toInt()
                        diff filters.ageChosenRange.endInclusive.toInt(),
                    hasAgeSafeMargin = defaultFilters.hasAgeSafeMargin diff filters.hasAgeSafeMargin,
                    distanceMax = defaultFilters.maxDistanceAway.toInt() diff filters.maxDistanceAway.toInt(),
                    hasDistanceLimit = defaultFilters.hasDistanceLimit diff filters.hasDistanceLimit,
                    hasDistanceSafeMargin = defaultFilters.hasDistanceSafeMargin diff filters.hasDistanceSafeMargin,
                    genders = defaultFilters.genders diff filters.genders,
                    relationships = defaultFilters.relationships diff filters.relationships,
                    languages = defaultFilters.languages diff filters.languages,

                    children = premiumFilter(
                        AdvancedFilter.CHILDREN, defaultFilters.children diff filters.children
                    ),
                    diets = premiumFilter(AdvancedFilter.DIET, defaultFilters.diets diff filters.diets),
                    drinkings = premiumFilter(
                        AdvancedFilter.DRINKING, defaultFilters.drinkings diff filters.drinkings
                    ),
                    educations = premiumFilter(
                        AdvancedFilter.EDUCATION, defaultFilters.educations diff filters.educations
                    ),
                    fitnesses = premiumFilter(
                        AdvancedFilter.FITNESS,
                        defaultFilters.fitnesses diff filters.fitnesses
                    ),
                    hasHeightMinLimit = premiumFilter(
                        AdvancedFilter.HEIGHT,
                        heightRange.start != filters.heightChosenRange.start
                    ),
                    hasHeightMaxLimit = premiumFilter(
                        AdvancedFilter.HEIGHT,
                        heightRange.endInclusive != filters.heightChosenRange.endInclusive
                    ),
                    heightMin = premiumFilter(
                        AdvancedFilter.HEIGHT, defaultFilters.heightChosenRange.start.toInt()
                            diff filters.heightChosenRange.start.toInt()
                    ),
                    heightMax = premiumFilter(
                        AdvancedFilter.HEIGHT, defaultFilters.heightChosenRange.endInclusive.toInt()
                            diff filters.heightChosenRange.endInclusive.toInt()
                    ),
                    heightSafeMargin = premiumFilter(
                        AdvancedFilter.HEIGHT, defaultFilters.heightSafeMargin diff filters.heightSafeMargin
                    ),
                    loveLanguages = premiumFilter(
                        AdvancedFilter.LOVE_LANGUAGE,
                        defaultFilters.loveLanguages diff filters.loveLanguages
                    ),
                    personality = premiumFilter(
                        AdvancedFilter.PERSONALITY,
                        defaultFilters.personalities diff filters.personalities
                    ),
                    pets = premiumFilter(AdvancedFilter.PETS, defaultFilters.pets diff filters.pets),
                    politics = premiumFilter(
                        AdvancedFilter.POLITICS,
                        defaultFilters.politics diff filters.politics
                    ),
                    religions = premiumFilter(
                        AdvancedFilter.RELIGION,
                        defaultFilters.religions diff filters.religions
                    ),
                    smokings = premiumFilter(
                        AdvancedFilter.SMOKING,
                        defaultFilters.smokings diff filters.smokings
                    ),
                    verifiedOnly = premiumFilter(
                        AdvancedFilter.VERIFIED_ONLY,
                        defaultFilters.verifiedOnly diff filters.verifiedOnly
                    ),
                    zodiacs = premiumFilter(
                        AdvancedFilter.ZODIAC,
                        defaultFilters.zodiacs diff filters.zodiacs
                    ),
                ).fold(
                    onSuccess = {
                        val matchersPage = it.getMatchersPage

                        val matchersCombined = matchers + matchersPage.matchers
                        postInput(MatcherContract.Inputs.SetMatches(matchersCombined))

                        postInput(MatcherContract.Inputs.SetPageInfo(matchersPage.info))
                    },
                    onFailure = {
                        postEvent(MatcherContract.Events.OnError(it.message ?: "Fetching matches failed"))
                    },
                )
            }
        }
    }

    private infix fun <T> T.diff(value: T): T? {
        val item = this
        return if (item != value) value else null
    }

    private fun <T> MatcherContract.State.premiumFilter(advancedFilter: AdvancedFilter, value: T): T? {
        return if (isPremium && advancedFilter in advancedFilters) value else null
    }

    private suspend fun MatcherInputScope.setAvailableUndo(input: MatcherContract.Inputs.SetAvailableUndo) {
        settings.putInt(MatcherViewModel.availableUndo, input.availableUndo)
        updateState { it.copy(availableUndo = input.availableUndo) }
    }

    private suspend fun MatcherInputScope.setIsUndoEnabled(input: MatcherContract.Inputs.SetIsUndoEnabled) {
        settings.putBoolean(MatcherViewModel.isUndoEnabledKey, input.isEnabled)
        updateState { it.copy(isUndoEnabled = input.isEnabled) }
    }

    private suspend fun MatcherInputScope.setProgress(input: MatcherContract.Inputs.SetProgress) {
        settings.putFloat(MatcherViewModel.progressKey, input.progress)
        updateState { it.copy(progress = input.progress) }
    }

    private suspend fun MatcherInputScope.onVisibleItemIndexChanged(index: Int) {
        val state = getCurrentState()
        sideJob("onVisibleItemIndexChanged") {
            settings.putInt(MatcherViewModel.visibleItemIndexKey, index)
            postInput(MatcherContract.Inputs.SetVisibleItemIndex(index))

            val progress = index.toFloat() / state.matchers.size.toFloat()
            postInput(MatcherContract.Inputs.SetProgress(progress))
        }
    }

    private suspend fun MatcherInputScope.onUndoClicked() {
        val state = getCurrentState()
        sideJob("onUndoClicked") {
            postInput(MatcherContract.Inputs.SetAvailableUndo(state.availableUndo - 1))

            if (state.availableUndo == 0 || state.visibleItemIndex == 1) {
                postInput(MatcherContract.Inputs.SetIsUndoEnabled(false))
            }
        }
    }

    private suspend fun MatcherInputScope.onSwipe(swipeType: SwipeType, index: Int) {
        val state = getCurrentState()
        sideJob("onSwipedRight$swipeType") {
            postInput(MatcherContract.Inputs.FetchNextPage)

            val swipedUserId = state.matchers[index].id

            matcherService.saveSwipe(swipeType, swipedUserId).fold(
                onSuccess = { logger.debug("Saved swipe $swipeType: $it") },
                onFailure = {
                    postEvent(MatcherContract.Events.OnError(it.message ?: "Saving $swipeType swipe failed"))
                },
            )

            postInput(MatcherContract.Inputs.SetIsUndoEnabled(true))
        }
    }

    private suspend fun MatcherInputScope.handleOnGetPremiumClicked() {
        postEvent(MatcherContract.Events.ShowGetPremium)
    }
}
