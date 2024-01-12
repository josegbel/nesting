package com.ajlabs.forevely.feature.debug

import com.ajlabs.forevely.core.Platform
import com.ajlabs.forevely.core.currentPlatform
import com.ajlabs.forevely.core.models.PermissionStatus
import com.ajlabs.forevely.core.util.infiniteFlow
import com.ajlabs.forevely.data.service.ConversationService
import com.ajlabs.forevely.data.service.DebugService
import com.ajlabs.forevely.data.service.UserService
import com.ajlabs.forevely.feature.debug.model.toLatLng
import com.ajlabs.forevely.feature.debug.model.toLocation
import com.ajlabs.forevely.location.LocationService
import com.ajlabs.forevely.notification.NotificationService
import com.ajlabs.forevely.notification.NotificationType
import com.ajlabs.forevely.notification.SoundType
import com.ajlabs.forevely.pictures.PicturesService
import com.ajlabs.forevely.pictures.model.MediaSource
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.core.PrintlnLogger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private typealias DebugInputScope =
    InputHandlerScope<DebugContract.Inputs, DebugContract.Events, DebugContract.State>

internal class DebugInputHandler : KoinComponent,
    InputHandler<DebugContract.Inputs, DebugContract.Events, DebugContract.State> {
    private val debugService: DebugService by inject()
    private val userService: UserService by inject()
    private val locationService: LocationService by inject()
    private val notificationService: NotificationService by inject()
    private val picturesService: PicturesService by inject()
    private val conversationService: ConversationService by inject()

    override suspend fun InputHandlerScope<DebugContract.Inputs, DebugContract.Events, DebugContract.State>.handleInput(
        input: DebugContract.Inputs,
    ) = when (input) {
        is DebugContract.Inputs.SetIsLoading -> updateState { it.copy(isLoading = input.isLoading) }
        DebugContract.Inputs.Init -> handleInit()

        is DebugContract.Inputs.SetGenerateUsersTarget ->
            updateState { it.copy(generateUsersTarget = input.target.toInt()) }

        DebugContract.Inputs.GenerateUsers -> handleGenerateUsers()
        DebugContract.Inputs.DeleteAllUsers -> deleteAllUsers()
        DebugContract.Inputs.DeleteGeneratedUsers -> deleteGenerateUsers()
        DebugContract.Inputs.OnClearCacheClick -> handleCacheClear()
        DebugContract.Inputs.GetIsPremium -> getIsPremium()
        is DebugContract.Inputs.SetIsPremium -> updateState { it.copy(isPremium = input.isPremium) }
        DebugContract.Inputs.ToggleIsPremium -> toggleIsPremium()
        is DebugContract.Inputs.SetUndoCount -> updateState { it.copy(undoCount = input.count.toInt()) }

        DebugContract.Inputs.OpenAppSettings -> locationService.openSettingPage()

        DebugContract.Inputs.ObserveLocationPermission -> observeLocationPermission()
        DebugContract.Inputs.RequestLocationPermission -> locationService.requestPermission()
        DebugContract.Inputs.GetLatLng -> getLatLng()
        DebugContract.Inputs.GetLocation -> getLocation()
        is DebugContract.Inputs.SetLatLng -> updateState { it.copy(latLng = input.latLng, showMap = input.showMap) }
        is DebugContract.Inputs.SetLocation -> updateState { it.copy(location = input.location) }
        is DebugContract.Inputs.SetLocationPermissionStatus ->
            updateState { it.copy(locationPermissionStatus = input.status) }

        DebugContract.Inputs.ObserveNotificationPermission -> observeNotificationPermission()
        DebugContract.Inputs.RequestNotificationPermission -> notificationService.requestPermission()
        is DebugContract.Inputs.SetNotificationText -> updateState { it.copy(notificationText = input.text) }
        DebugContract.Inputs.SendNotificationNow -> sendNotificationNow()
        is DebugContract.Inputs.SetNotificationPermission ->
            updateState { it.copy(notificationPermissionStatus = input.status) }

        DebugContract.Inputs.ObserveCameraPermission -> observeCameraPermission()
        is DebugContract.Inputs.PickImageFromCamera -> pickImageFromCamera()
        DebugContract.Inputs.RequestCameraPermission -> requestCameraPermission()
        is DebugContract.Inputs.SetCameraPermissionStatus ->
            updateState { it.copy(cameraPermissionStatus = input.status) }

        DebugContract.Inputs.ObserveGalleryPermission -> observeGalleryPermission()
        is DebugContract.Inputs.PickImageFromGallery -> pickImageFromGallery()
        DebugContract.Inputs.RequestGalleryPermission -> requestGalleryPermission()
        is DebugContract.Inputs.SetPickedBitmaps -> updateState { it.copy(pickedBitmaps = input.bitmaps) }
        DebugContract.Inputs.ClearPickedBitmaps -> updateState { it.copy(pickedBitmaps = emptyList()) }
        is DebugContract.Inputs.SetGalleryPermission ->
            updateState { it.copy(galleryPermissionStatus = input.status) }

        DebugContract.Inputs.WatchConversations -> {
            sideJob("WatchConversations") {
                val userId = userService.getUser().first().getOrNull()?.getUser?.id?.toString() ?: return@sideJob
                conversationService.watchConversations(userId).collect {
                    logger.debug("WatchConversations: $it")
                }
            }
        }
    }

    private suspend fun DebugInputScope.requestGalleryPermission() {
        sideJob("RequestGalleryPermission") {
            picturesService.requestGalleryPermission()
        }
    }

    private suspend fun DebugInputScope.pickImageFromGallery() {
        val pickedBitmaps = getCurrentState().pickedBitmaps
        sideJob("PickImageFromGallery") {
            picturesService.pickImage(MediaSource.GALLERY)?.let {
                postInput(DebugContract.Inputs.SetPickedBitmaps(pickedBitmaps + listOf(it)))
                PrintlnLogger().debug("Image picked from gallery")
            } ?: run {
                PrintlnLogger().debug("Image not picked from gallery")
                postEvent(DebugContract.Events.OnError("Error picking image"))
            }
        }
    }

    private suspend fun DebugInputScope.requestCameraPermission() {
        sideJob("RequestCameraPermission") {
            picturesService.requestCameraPermission()
        }
    }

    private suspend fun DebugInputScope.pickImageFromCamera() {
        when (currentPlatform) {
            Platform.ANDROID -> {
                val pickedBitmaps = getCurrentState().pickedBitmaps
                sideJob("PickImageFromCamera") {
                    picturesService.pickImage(MediaSource.CAMERA)?.let {
                        PrintlnLogger().debug("Image picked from camera")
                        postInput(DebugContract.Inputs.SetPickedBitmaps(pickedBitmaps + listOf(it)))
                    } ?: run {
                        PrintlnLogger().debug("Image not picked from camera")
                        postEvent(DebugContract.Events.OnError("Error picking image"))
                    }
                }
            }

            Platform.IOS -> {
                postEvent(DebugContract.Events.OnError("Picker is disabled on iOS"))
            }
        }
    }

    private suspend fun DebugInputScope.getLocation() {
        val latLng = getCurrentState().latLng
        if (latLng != null) {
            sideJob("GetLocation") {
                locationService.getPlaceDetails(latLng.latitude, latLng.longitude)?.let {
                    postInput(DebugContract.Inputs.SetLocation(it.toLocation()))
                }
            }
        } else {
            noOp()
        }
    }

    private suspend fun DebugInputScope.sendNotificationNow() {
        val state = getCurrentState()
        sideJob("SendNotificationNow") {
            val notificationType = NotificationType.Immediate(
                title = state.notificationText,
                body = state.notificationText,
                soundType = SoundType.CRITICAL
            )
            notificationService.schedule(notificationType)
        }
    }

    private suspend fun DebugInputScope.getLatLng() {
        sideJob("GetCurrentLocation") {
            locationService.getCurrentLocation()?.let {
                postInput(DebugContract.Inputs.SetLatLng(it.toLatLng(), true))
            }
        }
    }

    private suspend fun DebugInputScope.handleInit() {
        sideJob("handleInitDebug") {
            postInput(DebugContract.Inputs.GetIsPremium)
            postInput(DebugContract.Inputs.ObserveLocationPermission)
            postInput(DebugContract.Inputs.ObserveNotificationPermission)
            postInput(DebugContract.Inputs.ObserveCameraPermission)
            postInput(DebugContract.Inputs.ObserveGalleryPermission)
        }
    }

    private suspend fun DebugInputScope.getIsPremium() {
        sideJob("GetIsPremium") {
            userService.getIsPremium().fold(
                onSuccess = { postInput(DebugContract.Inputs.SetIsPremium(it.getUser.isPremium)) },
                onFailure = { postEvent(DebugContract.Events.OnError(it.message ?: "Error checking is Premium")) }
            )
        }
    }

    private suspend fun DebugInputScope.observeCameraPermission() {
        sideJob("observeCameraPermission") {
            infiniteFlow { picturesService.checkCameraPermission() }
                .catch { PrintlnLogger().error(it) }
                .collect { postInput(DebugContract.Inputs.SetCameraPermissionStatus(it)) }
        }
    }

    private suspend fun DebugInputScope.observeGalleryPermission() {
        sideJob("observeGalleryPermission") {
            infiniteFlow { picturesService.checkGalleryPermission() }
                .catch { PrintlnLogger().error(it) }
                .collect { postInput(DebugContract.Inputs.SetGalleryPermission(it)) }
        }
    }

    private suspend fun DebugInputScope.observeNotificationPermission() {
        sideJob("observeNotificationPermission") {
            infiniteFlow { locationService.checkPermission() }
                .catch { PrintlnLogger().error(it) }
                .collect { postInput(DebugContract.Inputs.SetNotificationPermission(it)) }
        }
    }

    private suspend fun DebugInputScope.observeLocationPermission() {
        sideJob("observeLocationPermission") {
            infiniteFlow { locationService.checkPermission() }
                .catch { PrintlnLogger().error(it) }
                .collect { location ->
                    postInput(DebugContract.Inputs.SetLocationPermissionStatus(location))
                    if (location == PermissionStatus.GRANTED) {
                        postInput(DebugContract.Inputs.GetLocation)
                    }
                }
        }
    }

    private suspend fun DebugInputScope.toggleIsPremium() {
        val state = getCurrentState()
        sideJob("toggleIsPremium") {
            debugService.setIsPremium(!state.isPremium).fold(
                onSuccess = { postInput(DebugContract.Inputs.SetIsPremium(it.debugSetIsPremium.isPremium)) },
                onFailure = { postEvent(DebugContract.Events.OnError(it.message ?: "Error setting is premium")) }
            )
        }
    }

    private suspend fun DebugInputScope.handleCacheClear() {
        sideJob("ClearCache") {
            debugService.clearCache().fold(
                onSuccess = { postEvent(DebugContract.Events.OnError("Cache cleared")) },
                onFailure = { postEvent(DebugContract.Events.OnError(it.message ?: "Error clearing cache")) }
            )
        }
    }

    private suspend fun DebugInputScope.handleGenerateUsers() {
        val state = getCurrentState()
        sideJob("GenerateUsers") {
            postInput(DebugContract.Inputs.SetIsLoading(true))
            debugService.generateUsers(state.generateUsersTarget).fold(
                onSuccess = {
                    val inserted = it.debugGenerateUsers.inserted
                    postEvent(DebugContract.Events.OnGenerateUsersSuccess("Generated $inserted users"))
                },
                onFailure = { postEvent(DebugContract.Events.OnError(it.message ?: "Error generating users")) }
            )
            postInput(DebugContract.Inputs.SetIsLoading(false))
        }
    }

    private suspend fun DebugInputScope.deleteAllUsers() {
        sideJob("DeleteAllUsers") {
            debugService.deleteAllUsers().fold(
                onSuccess = { postEvent(DebugContract.Events.OnDeleteUsersSuccess("Deleted all users")) },
                onFailure = { postEvent(DebugContract.Events.OnError(it.message ?: "Error deleting users")) }
            )
        }
    }

    private suspend fun DebugInputScope.deleteGenerateUsers() {
        sideJob("DeleteGeneratedUsers") {
            debugService.deleteGeneratedUsers().fold(
                onSuccess = { postEvent(DebugContract.Events.OnError("Deleted generated users")) },
                onFailure = { postEvent(DebugContract.Events.OnError(it.message ?: "Error deleting users")) }
            )
        }
    }
}
