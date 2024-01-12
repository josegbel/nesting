package com.ajlabs.forevely.feature.matcher

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.ajlabs.forevely.components.BottomSheet
import com.ajlabs.forevely.components.ModalSheet
import com.ajlabs.forevely.components.SuperSwipeButton
import com.ajlabs.forevely.components.TopBar
import com.ajlabs.forevely.data.GetMatchersPageQuery
import com.ajlabs.forevely.data.type.SwipeType
import com.ajlabs.forevely.feature.debug.model.LatLng
import com.ajlabs.forevely.feature.matcher.cards.AboutMeAttr
import com.ajlabs.forevely.feature.matcher.cards.MatcherContract
import com.ajlabs.forevely.feature.matcher.cards.MatcherViewModel
import com.ajlabs.forevely.feature.matcher.filters.MatcherFiltersSheet
import com.ajlabs.forevely.feature.matcher.sections.AboutMeSection
import com.ajlabs.forevely.feature.matcher.sections.FinalMatchSection
import com.ajlabs.forevely.feature.matcher.sections.LocationInfoSection
import com.ajlabs.forevely.feature.matcher.sections.MainPictureSection
import com.ajlabs.forevely.feature.matcher.sections.PicturesSection
import com.ajlabs.forevely.feature.matcher.sections.RecommendToFriendButton
import com.ajlabs.forevely.feature.matcher.topbarActions.MatcherTopBarCenterAction
import com.ajlabs.forevely.feature.matcher.topbarActions.MatcherTopBarEndAction
import com.ajlabs.forevely.feature.matcher.topbarActions.MatcherTopBarExtraEndAction
import com.ajlabs.forevely.feature.matcher.topbarActions.MatcherTopBarExtraStartAction
import com.ajlabs.forevely.feature.sheets.GetPremiumModal
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.getString
import com.ajlabs.forevely.localization.toName
import com.ajlabs.forevely.matcherCards.LazyCardStack
import com.ajlabs.forevely.matcherCards.rememberLazyCardStackState
import com.ajlabs.forevely.matcherCards.swiper.SwipeDirection
import com.ajlabs.forevely.theme.safePaddingValues
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ModalShow(
    val id: Int,
    val modalSheet: ModalSheet,
    val show: Boolean,
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MatcherContent(
    snackbarHostState: SnackbarHostState,
    onMenuClicked: () -> Unit,
    onSpeedDatingClicked: () -> Unit,
    onComplementClick: (imageId: String) -> Unit,
    onAboutMeAttrClick: (AboutMeAttr) -> Unit,
    onLocationClick: () -> Unit,
    onSuperSwipeClick: () -> Unit,
    onHideAndReportClick: (userId: String) -> Unit,
    onRecommendToFriendClick: (userId: String) -> Unit,
    isModalShowing: (isShowingModal: Boolean) -> Unit,
) {
    var filterModal by remember {
        mutableStateOf(
            ModalShow(
                id = 0,
                modalSheet = ModalSheet.MatcherFilter,
                show = false,
            )
        )
    }
    var getPremiumModal by remember {
        mutableStateOf(
            ModalShow(
                id = 1,
                modalSheet = ModalSheet.GetPremium,
                show = false,
            )
        )
    }

    val scope = rememberCoroutineScope()
    val vm = remember(scope) {
        MatcherViewModel(
            scope = scope,
            onError = { snackbarHostState.showSnackbar(it) },
            showGetPremium = { getPremiumModal = getPremiumModal.copy(show = true) },
        )
    }
    val state by vm.observeStates().collectAsState()

    val cardStackState = rememberLazyCardStackState(
        firstVisibleItemIndex = state.visibleItemIndex,
    )

    LaunchedEffect(cardStackState.visibleItemIndex) {
        vm.trySend(MatcherContract.Inputs.OnVisibleItemIndexChanged(cardStackState.visibleItemIndex))
    }

    LaunchedEffect(filterModal, getPremiumModal) {
        val modals = listOf(filterModal, getPremiumModal)
        isModalShowing(modals.any { it.show })
        println("Debug currentModals: $modals")
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (state.matchers.isNotEmpty()) {
            LazyCardStack(
                state = cardStackState,
                directions = setOf(
                    SwipeDirection.Left,
                    SwipeDirection.Right,
                ),
                onSwipedItem = { index, direction ->
                    val swipeType = when (direction) {
                        SwipeDirection.Left -> SwipeType.DISLIKE
                        SwipeDirection.Right -> SwipeType.LIKE
                    }
                    vm.trySend(MatcherContract.Inputs.OnSwipe(swipeType, index))
                },
                modifier = Modifier
                    .padding(
                        top = 48.dp + safePaddingValues.calculateTopPadding(),
                        bottom = 48.dp + safePaddingValues.calculateBottomPadding(),
                        start = 8.dp,
                        end = 8.dp,
                    )
                    .fillMaxWidth(),
            ) {
                items(
                    count = state.matchers.size,
                    key = { index -> index },
                ) { index ->
                    val userProfile = state.matchers[index]
                    val bottomBarPadding = 16.dp

                    BoxWithConstraints {
                        MatcherCard(
                            matchProfile = userProfile,
                            mainPictureHeight = maxHeight - bottomBarPadding,
                            onComplementCLick = onComplementClick,
                            onAboutMeAttrClick = onAboutMeAttrClick,
                            onSuperSwipeClick = onSuperSwipeClick,
                            onLocationClick = onLocationClick,
                            onHideAndReportClick = { onHideAndReportClick(userProfile.id) },
                            onRecommendToFriendClick = { onRecommendToFriendClick(userProfile.id) },
                            onSwipeLeftClick = {
                                vm.trySend(
                                    MatcherContract.Inputs.OnSwipe(SwipeType.DISLIKE, cardStackState.visibleItemIndex)
                                )
                                scope.launch {
                                    cardStackState.animateToNext(SwipeDirection.Left)
                                }
                            },
                            onSwipeRightClick = {
                                vm.trySend(
                                    MatcherContract.Inputs.OnSwipe(SwipeType.LIKE, cardStackState.visibleItemIndex)
                                )
                                scope.launch {
                                    cardStackState.animateToNext(SwipeDirection.Right)
                                }
                            },
                            isLocationEnabled = state.isLocationEnabled,
                            modifier = Modifier
                                .padding(
                                    top = safePaddingValues.calculateTopPadding(),
                                    bottom = bottomBarPadding
                                ),
                        )
                    }
                }
            }
        }
        if (state.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                CircularProgressIndicator()
            }
        }
        TopBar(
            onMenuClicked = onMenuClicked,
            extraActionsStart = {
                MatcherTopBarExtraStartAction(
                    isVisible = state.isUndoEnabled,
                    onClick = {
                        vm.trySend(MatcherContract.Inputs.OnUndoClicked)
                        scope.launch {
                            cardStackState.animateToBack(SwipeDirection.Left)
                        }
                    },
                )
            },
            actionCenter = {
                MatcherTopBarCenterAction(
                    matcherProgress = state.progress,
                    headerText = getString(Strings.AppName),
                    modifier = it,
                )
            },
            extraActionsEnd = {
                MatcherTopBarExtraEndAction(
                    onClick = { onSpeedDatingClicked() },
                )
            },
            actionEnd = {
                MatcherTopBarEndAction(
                    onClick = {
                        filterModal = filterModal.copy(show = true)
                    },
                )
            },
            isMenuButtonVisible = true,
            modifier = Modifier
                .padding(top = safePaddingValues.calculateTopPadding())
                .align(alignment = Alignment.TopCenter)
        )
        Box {
            BottomSheet(
                showSheet = filterModal.show,
                modalSheet = filterModal.modalSheet,
                modifier = Modifier
                    .zIndex((filterModal.id).toFloat())
                    .consumeWindowInsets(WindowInsets.systemBars)
            ) {
                MatcherFiltersSheet(
                    vm = vm,
                    state = state,
                    onClose = {
                        scope.launch {
                            delay(300L)
                            filterModal = filterModal.copy(show = false)
                        }
                    },
                )
            }
            if (filterModal.show) {
                BottomSheet(
                    showSheet = getPremiumModal.show,
                    modalSheet = getPremiumModal.modalSheet,
                    modifier = Modifier
                        .zIndex((getPremiumModal.id).toFloat())
                        .consumeWindowInsets(WindowInsets.systemBars)
                ) {
                    GetPremiumModal(
                        onCloseClicked = {
                            scope.launch {
                                delay(300L)
                                getPremiumModal = getPremiumModal.copy(show = false)
                            }
                        },
                    )
                }
            }
        }
    }
}


@Composable
private fun MatcherCard(
    modifier: Modifier = Modifier,
    matchProfile: GetMatchersPageQuery.Matcher,
    mainPictureHeight: Dp,
    onComplementCLick: (imageId: String) -> Unit,
    onAboutMeAttrClick: (AboutMeAttr) -> Unit,
    onLocationClick: () -> Unit,
    onSuperSwipeClick: () -> Unit,
    onSwipeRightClick: () -> Unit,
    onSwipeLeftClick: () -> Unit,
    onHideAndReportClick: () -> Unit,
    onRecommendToFriendClick: () -> Unit,
    isLocationEnabled: Boolean,
) {
    val lazyListState = rememberLazyListState()

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colors.background,
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxWidth(),
            ) {
                item {
                    if (
                        matchProfile.pictures.isNotEmpty() &&
                        matchProfile.pictures.first().isNotEmpty() &&
                        matchProfile.name != null &&
                        matchProfile.age != null
                    ) {
                        MainPictureSection(
                            name = matchProfile.name!!,
                            age = matchProfile.age!!,
                            imageUrl = matchProfile.pictures.first(),
                            verified = matchProfile.verified,
                            onComplementCLicked = { onComplementCLick(matchProfile.pictures.first()) },
                            modifier = Modifier
                                .height(mainPictureHeight),
                        )
                    }
                }
                item {
                    AboutMeSection(
                        items = matchProfile.toAboutMeAttrs(),
                        onCLick = { onAboutMeAttrClick(it) },
                    )
                }
                item {
                    PicturesSection(
                        pictures = matchProfile.pictures,
                        onComplementCLicked = { onComplementCLick(it) },
                    )
                }
                item {
                    if (
                        matchProfile.currentLoc?.latitude != null &&
                        matchProfile.currentLoc?.longitude != null
                    ) {
                        val latLng = LatLng(matchProfile.currentLoc?.latitude!!, matchProfile.currentLoc?.longitude!!)

                        LocationInfoSection(
                            userName = matchProfile.name,
                            city = matchProfile.currentLoc?.city,
                            state = matchProfile.currentLoc?.state,
                            country = matchProfile.currentLoc?.country,
                            distance = matchProfile.currentLoc?.distance,
                            isLocationEnabled = isLocationEnabled,
                            latLng = latLng,
                            onCLick = onLocationClick,
                        )
                    }
                }
                item {
                    FinalMatchSection(
                        onSuperSwipeCLick = onSuperSwipeClick,
                        onSwipeRightClick = onSwipeRightClick,
                        onSwipeLeftClick = onSwipeLeftClick,
                        onHideAndReportClick = onHideAndReportClick,
                    )
                }
                item {
                    RecommendToFriendButton(
                        onCLick = onRecommendToFriendClick,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                    )
                }
            }

            AnimatedVisibility(
                visible = lazyListState.canScrollForward,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .padding(16.dp)
                    .align(alignment = Alignment.BottomEnd),
            ) {
                SuperSwipeButton(
                    onClick = onSuperSwipeClick,
                )
            }
        }
    }
}

internal fun GetMatchersPageQuery.Matcher.toAboutMeAttrs(): Map<AboutMeAttr, String> =
    mutableMapOf<AboutMeAttr, String>().apply {
        height?.let { put(AboutMeAttr.HEIGHT, it.toString()) }
        diet?.let { put(AboutMeAttr.DIET, getString(it.toName())) }
        drinking?.let { put(AboutMeAttr.DRINKING, getString(it.toName())) }
        education?.let { put(AboutMeAttr.EDUCATION, getString(it.toName())) }
        fitness?.let { put(AboutMeAttr.FITNESS, getString(it.toName())) }
        loveLanguage?.let { put(AboutMeAttr.LOVE_LANGUAGE, getString(it.toName())) }
        personality?.let { put(AboutMeAttr.PERSONALITY, getString(it.toName())) }
        pets?.let { put(AboutMeAttr.PETS, getString(it.toName())) }
        politics?.let { put(AboutMeAttr.POLITICS, getString(it.toName())) }
        relationship?.let { put(AboutMeAttr.RELATIONSHIP, getString(it.toName())) }
        smoking?.let { put(AboutMeAttr.SMOKING, getString(it.toName())) }
        children?.let { put(AboutMeAttr.CHILDREN, getString(it.toName())) }
        zodiac?.let { put(AboutMeAttr.ZODIAC, getString(it.toName())) }
        religion?.let { put(AboutMeAttr.RELIGION, getString(it.toName())) }
    }
