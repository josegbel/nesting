package com.ajlabs.forevely.pictures

import kotlin.coroutines.Continuation
import platform.UIKit.UIAdaptivePresentationControllerDelegateProtocol
import platform.UIKit.UIPresentationController
import platform.darwin.NSObject

internal class AdaptivePresentationDelegateToContinuation(
    private val continuation: Continuation<*>,
) : NSObject(), UIAdaptivePresentationControllerDelegateProtocol {
    override fun presentationControllerDidDismiss(presentationController: UIPresentationController) {
        continuation.resumeWith(Result.failure(Exception("Image picker cancelled")))
    }
}
