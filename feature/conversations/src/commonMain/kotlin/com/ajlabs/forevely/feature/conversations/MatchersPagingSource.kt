package com.ajlabs.forevely.feature.conversations

import app.cash.paging.PagingSource
import app.cash.paging.PagingState
import com.ajlabs.forevely.data.GetMatchedUsersWithoutConversationQuery
import com.ajlabs.forevely.data.service.ConversationService
import com.ajlabs.forevely.data.type.PageInput
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val PAGE_SIZE_DEFAULT = 10

internal class MatchersPagingSource : KoinComponent,
    PagingSource<Int, GetMatchedUsersWithoutConversationQuery.Matcher>() {
    private val conversationService: ConversationService by inject()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetMatchedUsersWithoutConversationQuery.Matcher> {
        val pageInput = PageInput(params.key ?: 0, PAGE_SIZE_DEFAULT)
        return conversationService.getMarchedUsersWithoutConversations(pageInput).fold(
            onSuccess = { data ->
                val page = data.getMatchedUsersWithoutConversation
                LoadResult.Page(
                    data = page.matchers,
                    prevKey = page.info.prev,
                    nextKey = page.info.next
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, GetMatchedUsersWithoutConversationQuery.Matcher>): Int? {
        return state.anchorPosition
    }
}
