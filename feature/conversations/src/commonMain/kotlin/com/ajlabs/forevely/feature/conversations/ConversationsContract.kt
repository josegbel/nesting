package com.ajlabs.forevely.feature.conversations

import androidx.paging.PagingData
import com.ajlabs.forevely.data.GetConversationsPageQuery
import com.ajlabs.forevely.data.GetMatchedUsersWithoutConversationQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.koin.core.component.KoinComponent

object ConversationsContract : KoinComponent {
    data class State(
        val isLoading: Boolean = false,
        val userPicture: String? = null,
        val matchersPagingDataFlow: Flow<PagingData<GetMatchedUsersWithoutConversationQuery.Matcher>> = emptyFlow(),
        val conversationsPagingDataFlow: Flow<PagingData<GetConversationsPageQuery.Conversation>> = emptyFlow(),
        val pageInfo: GetConversationsPageQuery.Info? = null,
    )

    sealed interface Inputs {
        data object Init : Inputs
        data object FetchMatchers : Inputs
        data class OnMatcherClicked(val matcher: GetMatchedUsersWithoutConversationQuery.Matcher) : Inputs

        data object FetchConversations : Inputs
        data class CreateConversation(val matcherId: String, val matcherPicture: String?) : Inputs
        data class OnConversationClicked(val conversation: GetConversationsPageQuery.Conversation) : Inputs

        data object GetUserInfo : Inputs
        data class SetUserPicture(val userPicture: String) : Inputs
    }

    sealed interface Events {
        data class OnError(val message: String) : Events
        data class OnConversationClicked(val conversationInfo: ConversationInfo) : Events
    }
}
