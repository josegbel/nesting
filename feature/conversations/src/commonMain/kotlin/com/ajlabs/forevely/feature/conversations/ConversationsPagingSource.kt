package com.ajlabs.forevely.feature.conversations

import app.cash.paging.PagingSource
import app.cash.paging.PagingState
import com.ajlabs.forevely.data.GetConversationsPageQuery
import com.ajlabs.forevely.data.service.ConversationService
import com.ajlabs.forevely.data.type.PageInput
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val PAGE_SIZE_DEFAULT = 10

internal class ConversationsPagingSource : KoinComponent,
    PagingSource<Int, GetConversationsPageQuery.Conversation>() {
    private val conversationService: ConversationService by inject()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetConversationsPageQuery.Conversation> {
        val pageInput = PageInput(params.key ?: 0, PAGE_SIZE_DEFAULT)
        return conversationService.getConversationsPage(pageInput).fold(
            onSuccess = { data ->
                val page = data.getConversationsPage
                LoadResult.Page(
                    data = page.conversations,
                    prevKey = page.info.prev,
                    nextKey = page.info.next
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, GetConversationsPageQuery.Conversation>): Int? {
        return state.anchorPosition
    }
}
