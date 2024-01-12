package com.ajlabs.forevely.feature.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import com.ajlabs.forevely.feature.conversations.ConversationInfo
import com.ajlabs.forevely.theme.safePaddingValues
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun ChatContent(
    conversationInfo: ConversationInfo,
    onError: suspend (String) -> Unit,
    onGoBack: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val vm = remember(scope) {
        ChatViewModel(conversationInfo.id, scope, onError)
    }
    val state by vm.observeStates().collectAsState()
    val hazeState = remember { HazeState() }

    val messages = state.messagesPagingDataFlow.collectAsLazyPagingItems()

    val lazyListState = rememberLazyListState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(state) {
//        if (state.messages.isNotEmpty() && state.isAnswering) {
//            lazyListState.animateScrollToItem(state.messages.size - 1)
//        }
    }

    LaunchedEffect(lazyListState) {
        println("lazyListState.firstVisibleItemIndex: ${lazyListState.firstVisibleItemIndex}")
        println("lazyListState.firstVisibleItemScrollOffset: ${lazyListState.firstVisibleItemScrollOffset}")
    }

    Surface(
        color = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            ChatList(
                lazyListState = lazyListState,
                messagesLocal = state.messagesLocal,
                messagesPaged = messages,
                isLoadingPaging = messages.loadState.refresh == LoadState.Loading,
                thumbUrl = conversationInfo.matcherPicture,
                modifier = Modifier
                    .fillMaxSize()
                    .haze(
                        state = hazeState,
                        backgroundColor = MaterialTheme.colors.background,
                    )
            )
            ChatTopBar(
                title = conversationInfo.matcherName,
                thumb = conversationInfo.matcherPicture,
                onGoBackClick = onGoBack,
                onMenuClick = { },
                modifier = Modifier
                    .padding(top = safePaddingValues.calculateTopPadding())
                    .align(Alignment.TopCenter)
                    .hazeChild(state = hazeState)
            )
            ChatInputBar(
                value = state.textInput,
                onValueChange = { vm.trySend(ChatContract.Inputs.SetChatInput(it)) },
                onSend = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    vm.trySend(ChatContract.Inputs.SendMessage)
                },
                onAddAttachmentClick = { },
                modifier = Modifier
                    .padding(bottom = safePaddingValues.calculateBottomPadding())
                    .align(Alignment.BottomCenter)
                    .hazeChild(state = hazeState)
            )
        }
    }
}
