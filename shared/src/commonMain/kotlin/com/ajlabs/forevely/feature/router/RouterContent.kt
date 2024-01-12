package com.ajlabs.forevely.feature.router

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ajlabs.forevely.components.BottomBar
import com.ajlabs.forevely.components.BottomSheet
import com.ajlabs.forevely.components.ModalSheet
import com.ajlabs.forevely.feature.bestBee.BestBeeContent
import com.ajlabs.forevely.feature.chat.ChatContent
import com.ajlabs.forevely.feature.conversations.ConversationInfo
import com.ajlabs.forevely.feature.conversations.ConversationsContent
import com.ajlabs.forevely.feature.likes.LikesContent
import com.ajlabs.forevely.feature.login.LoginContent
import com.ajlabs.forevely.feature.matcher.MatcherContent
import com.ajlabs.forevely.feature.matcher.ModalShow
import com.ajlabs.forevely.feature.matcher.cards.AboutMeAttr
import com.ajlabs.forevely.feature.myPlan.MyPlanContent
import com.ajlabs.forevely.feature.profile.ProfileContent
import com.ajlabs.forevely.feature.router.RouterScreen.BestBee
import com.ajlabs.forevely.feature.router.RouterScreen.Likes
import com.ajlabs.forevely.feature.router.RouterScreen.Login
import com.ajlabs.forevely.feature.router.RouterScreen.Matcher
import com.ajlabs.forevely.feature.router.RouterScreen.MyPlan
import com.ajlabs.forevely.feature.router.RouterScreen.Profile
import com.ajlabs.forevely.feature.router.RouterScreen.UpdateProfile
import com.ajlabs.forevely.feature.router.sheets.MenuDrawer
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContent
import com.ajlabs.forevely.theme.safePaddingValues
import com.copperleaf.ballast.navigation.routing.Backstack
import com.copperleaf.ballast.navigation.routing.RouterContract.Inputs.GoBack
import com.copperleaf.ballast.navigation.routing.RouterContract.Inputs.GoToDestination
import com.copperleaf.ballast.navigation.routing.RouterContract.Inputs.ReplaceTopDestination
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.currentRouteOrNull
import com.copperleaf.ballast.navigation.routing.directions
import com.copperleaf.ballast.navigation.routing.pathParameter
import com.copperleaf.ballast.navigation.routing.renderCurrentDestination
import com.copperleaf.ballast.navigation.routing.stringPath
import com.copperleaf.ballast.navigation.vm.Router
import kotlinx.serialization.json.Json

@Composable
internal fun RouterContent(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    isAuthenticated: Boolean,
    onLogOut: () -> Unit,
    homeScreen: RouterScreen = RouterScreen.Conversations,
    loginScreen: RouterScreen = Login,
    onSpeedDatingClicked: () -> Unit,
    onComplementClick: (imageId: String) -> Unit,
    onAboutMeAttrClick: (AboutMeAttr) -> Unit,
    onLocationClick: () -> Unit,
    onSuperSwipeClick: () -> Unit,
    onHideAndReportClick: (userId: String) -> Unit,
    onRecommendToFriendClick: (userId: String) -> Unit,
    onSettingsClicked: () -> Unit,
    onShowQrCodeSheet: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val initialRoute = when (isAuthenticated) {
        true -> homeScreen
        false -> loginScreen
    }
    val router: Router<RouterScreen> = remember(scope) {
        RouterViewModel(
            viewModelScope = scope,
            initialRoute = initialRoute,
        )
    }
    val routerState: Backstack<RouterScreen> by router.observeStates().collectAsState()
    val onError: suspend (String) -> Unit = { snackbarHostState.showSnackbar(it) }

    LaunchedEffect(isAuthenticated) {
        if (!isAuthenticated) {
            if (routerState.currentRouteOrNull != loginScreen) {
                println("User is not authenticated, redirecting to login screen")
                router.trySend(ReplaceTopDestination(Login.matcher.routeFormat))
            }
        } else {
            if (routerState.currentRouteOrNull == loginScreen) {
                println("User is authenticated, redirecting to matcher screen")
                router.trySend(ReplaceTopDestination(RouterScreen.Matcher.matcher.routeFormat))
            }
        }
    }

    var isModalShowing by remember { mutableStateOf(false) }

    var menuDrawer by remember {
        mutableStateOf(
            ModalShow(
                id = 0,
                modalSheet = ModalSheet.MenuDrawer,
                show = false,
            )
        )
    }
    val onMenuClick: () -> Unit = {
        menuDrawer = menuDrawer.copy(show = true)
        isModalShowing = true
    }

    Scaffold(
        modifier = modifier,
        topBar = {},
        bottomBar = {
            BottomBar(
                routes = bottomBarRoutes,
                currentRoute = routerState.currentRouteOrNull ?: loginScreen,
                onDestinationClick = { route ->
                    router.trySend(ReplaceTopDestination(route.matcher.routeFormat))
                },
                show = !isModalShowing && (routerState.currentRouteOrNull ?: loginScreen) in bottomBarRoutes,
                modifier = Modifier
                    .padding(bottom = safePaddingValues.calculateBottomPadding())
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            routerState.renderCurrentDestination(
                route = { routerScreen: RouterScreen ->
                    when (routerScreen) {
                        Login -> LoginContent(
                            onError = onError,
                            onAuthenticated = { router.trySend(ReplaceTopDestination(homeScreen.matcher.routeFormat)) },
                        )

                        MyPlan -> MyPlanContent(
                            snackbarHostState = snackbarHostState,
                            onMenuClicked = onMenuClick,
                            onExtraEndIconClicked = onShowQrCodeSheet,
                            onSettingsClicked = onSettingsClicked,
                            onProfileClicked = { router.trySend(GoToDestination(UpdateProfile.matcher.routeFormat)) },
                            onLogOut = onLogOut,
                        )

                        BestBee -> BestBeeContent()

                        Matcher -> MatcherContent(
                            snackbarHostState = snackbarHostState,
                            onMenuClicked = onMenuClick,
                            onSpeedDatingClicked = onSpeedDatingClicked,
                            onComplementClick = onComplementClick,
                            onAboutMeAttrClick = onAboutMeAttrClick,
                            onLocationClick = onLocationClick,
                            onSuperSwipeClick = onSuperSwipeClick,
                            onHideAndReportClick = onHideAndReportClick,
                            onRecommendToFriendClick = onRecommendToFriendClick,
                            isModalShowing = { isModalShowing = it },
                        )

                        Likes -> LikesContent(
                            onError = onError,
                        )

                        RouterScreen.Conversations -> ConversationsContent(
                            onError = onError,
                            onMenuClicked = onMenuClick,
                            onConversationClicked = { conversationInfo ->
                                val conversationInfoString =
                                    Json.encodeToString(ConversationInfo.serializer(), conversationInfo)

                                println("sending conversationInfo: $conversationInfoString")

                                router.trySend(
                                    GoToDestination(
                                        RouterScreen.Chat
                                            .directions()
                                            .pathParameter("conversationInfo", conversationInfoString)
                                            .build(),
                                    ),
                                )
                            },
                        )

                        RouterScreen.Chat -> {
                            val conversationInfoString by stringPath("conversationInfo")
                            val conversationInfo =
                                Json.decodeFromString<ConversationInfo>(conversationInfoString)

                            ChatContent(
                                conversationInfo = conversationInfo,
                                onError = onError,
                                onGoBack = { router.trySend(GoBack()) },
                            )
                        }

                        Profile -> ProfileContent(
                            goBack = { router.trySend(GoBack()) },
                        )

                        UpdateProfile -> UpdateProfileContent(
                            onError = onError,
                            onBackClicked = { router.trySend(GoBack()) },
                            isModalShowing = { isModalShowing = it }

                        )
                    }
                },
                notFound = { router.trySend(GoToDestination(Login.matcher.routeFormat)) },
            )
            BottomSheet(
                showSheet = menuDrawer.show,
                modalSheet = menuDrawer.modalSheet,
                modifier = Modifier
                    .consumeWindowInsets(WindowInsets.systemBars)
            ) {
                MenuDrawer(
                    snackbarHostState = snackbarHostState,
                    onCloseClicked = {
                        menuDrawer = menuDrawer.copy(show = false)
                        isModalShowing = false
                    },
                    onLogOut = {
                        onLogOut()
                        menuDrawer = menuDrawer.copy(show = false)
                    },
                )
            }
        }
    }
}
