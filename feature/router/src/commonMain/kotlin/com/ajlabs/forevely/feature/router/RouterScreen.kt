package com.ajlabs.forevely.feature.router

import com.copperleaf.ballast.navigation.routing.Route
import com.copperleaf.ballast.navigation.routing.RouteAnnotation
import com.copperleaf.ballast.navigation.routing.RouteMatcher

private const val APP = "/app"
private const val LOGIN = "/login"
private const val MY_PLAN = "/my_plan"
private const val BEST_BEE = "/best_bee"
private const val MATCHER = "/matcher"
private const val LIKES = "/likes"
private const val CONVERSATIONS = "/conversations"
private const val CHAT = "/chat/{conversationInfo}"
private const val PROFILE = "/profile"
private const val UPDATE_PROFILE = "/update_profile"

enum class RouterScreen(
    routeFormat: String,
    override val annotations: Set<RouteAnnotation> = emptySet(),
) : Route {
    Login(APP + LOGIN),
    MyPlan(APP + MY_PLAN),
    BestBee(APP + BEST_BEE),
    Matcher(APP + MATCHER),
    Likes(APP + LIKES),
    Conversations(APP + CONVERSATIONS),
    Chat(APP + CONVERSATIONS + CHAT),
    Profile(APP + PROFILE),
    UpdateProfile(APP + UPDATE_PROFILE),
    ;

    override val matcher: RouteMatcher = RouteMatcher.create(routeFormat)
}
