package com.ajlabs.forevely.feature.conversations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.ajlabs.forevely.components.Thumbnail
import com.ajlabs.forevely.data.GetConversationsPageQuery
import com.ajlabs.forevely.data.GetMatchedUsersWithoutConversationQuery
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.getString
import com.ajlabs.forevely.theme.Dimensions
import com.ajlabs.forevely.theme.safePaddingValues
import com.ajlabs.forevely.util.toTime
import kotlinx.coroutines.launch

@Composable
internal fun ConversationsContent(
    onError: suspend (String) -> Unit,
    onMenuClicked: () -> Unit,
    onConversationClicked: (ConversationInfo) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val vm = remember(scope) {
        ConversationsViewModel(
            scope = scope,
            onError = onError,
            onConversationClicked = onConversationClicked,
        )
    }
    val state by vm.observeStates().collectAsState()
    val matchers = state.matchersPagingDataFlow.collectAsLazyPagingItems()
    val conversations = state.conversationsPagingDataFlow.collectAsLazyPagingItems()

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            modifier = Modifier.fillMaxWidth(),
            onMenuClicked = onMenuClicked,
            onSearchClicked = {
                scope.launch {
                    onError("Search is not implemented yet")
                }
            },
        )
        Matchers(
            matcherLazyPagingItems = matchers,
            onClick = { vm.trySend(ConversationsContract.Inputs.OnMatcherClicked(it)) }
        )
        ConversationsList(
            conversationLazyPagingItems = conversations,
            hasMatchers = matchers.itemCount > 0,
            onClick = { vm.trySend(ConversationsContract.Inputs.OnConversationClicked(it)) }
        )
    }
}

@Composable
private fun Matchers(
    matcherLazyPagingItems: LazyPagingItems<GetMatchedUsersWithoutConversationQuery.Matcher>,
    onClick: (GetMatchedUsersWithoutConversationQuery.Matcher) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = getString(Strings.Conversations.MatchQueue),
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(matcherLazyPagingItems.itemCount) { index ->
                matcherLazyPagingItems.itemSnapshotList[index]?.let { matcher ->
                    MatcherItem(
                        url = matcher.picture,
                        name = matcher.name,
                        onClick = { onClick(matcher) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    modifier: Modifier,
    onMenuClicked: () -> Unit,
    onSearchClicked: () -> Unit,
) {
    com.ajlabs.forevely.components.TopBar(
        onMenuClicked = onMenuClicked,
        extraActionsStart = {},
        actionCenter = {},
        extraActionsEnd = {},
        actionEnd = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onSearchClicked() }
            )
        },
        isMenuButtonVisible = true,
        modifier = modifier
            .padding(top = safePaddingValues.calculateTopPadding())

    )
}

@Composable
private fun MatcherItem(
    modifier: Modifier = Modifier,
    url: String?,
    name: String,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.clickable { onClick() },
    ) {
        url?.let {
            Thumbnail(
                url = url,
                modifier = Modifier.size(64.dp)
            )
        } ?: run {
            DefaultUserThumbnail(
                name = name,
                modifier = Modifier.size(64.dp)
            )
        }
        Text(
            text = name,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.caption,
        )
    }
}

@Composable
private fun DefaultUserThumbnail(
    modifier: Modifier = Modifier,
    name: String,
) {
    Surface(
        shape = CircleShape,
        elevation = 8.dp,
        color = MaterialTheme.colors.primary,
        modifier = modifier.size(64.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = name.first().toString(),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h6,
            )
        }
    }
}

@Composable
fun ConversationsList(
    conversationLazyPagingItems: LazyPagingItems<GetConversationsPageQuery.Conversation>,
    hasMatchers: Boolean,
    onClick: (GetConversationsPageQuery.Conversation) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = getString(Strings.Conversations.Chats),
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(start = 16.dp, end = 4.dp)
            )
            // TODO: This will come from current filter selected
            Text(
                text = "(${getString(Strings.Conversations.Recent)})",
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                style = MaterialTheme.typography.h6,
            )
        }
        if (conversationLazyPagingItems.itemSnapshotList.isEmpty()) {
            val description = if (hasMatchers) {
                Strings.Conversations.NoConversationsDescriptionWithMatches
            } else {
                Strings.Conversations.NoConversationsDescriptionWithOutMatches
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = getString(Strings.Conversations.NoConversations),
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                    )
                    Text(
                        text = getString(description),
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                    )
                }
            }
        }
        if (conversationLazyPagingItems.itemSnapshotList.isNotEmpty()) {
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp + Dimensions.BOTTOM_BAR_HEIGHT.dp
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(conversationLazyPagingItems.itemCount) { index ->
                    conversationLazyPagingItems.itemSnapshotList[index]?.let { conversation ->
                        ConversationItem(
                            conversation = conversation,
                            onClick = { onClick(conversation) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ConversationItem(
    conversation: GetConversationsPageQuery.Conversation,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.clickable { onClick() },
    ) {
        conversation.picture?.let {
            Thumbnail(
                url = it,
                modifier = Modifier.size(64.dp)
            )
        } ?: run {
            DefaultUserThumbnail(
                name = conversation.matcherName,
                modifier = Modifier.size(64.dp)
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = conversation.matcherName,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h6,
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = conversation.lastMessage?.content ?: "",
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.caption,
                )
                Text(
                    text = conversation.lastMessage?.timestamp?.toLong()?.toTime() ?: "",
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}
