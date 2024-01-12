package com.ajlabs.forevely.feature.updateProfile

import com.ajlabs.forevely.core.Platform
import com.ajlabs.forevely.core.currentPlatform
import com.ajlabs.forevely.core.models.PermissionStatus
import com.ajlabs.forevely.core.util.infiniteFlow
import com.ajlabs.forevely.data.GetUserQuery.AboutMe
import com.ajlabs.forevely.data.GetUserQuery.Interests
import com.ajlabs.forevely.data.service.UserService
import com.ajlabs.forevely.data.type.AboutMeInput
import com.ajlabs.forevely.data.type.Creative
import com.ajlabs.forevely.data.type.Culinary
import com.ajlabs.forevely.data.type.Gender
import com.ajlabs.forevely.data.type.InterestsInput
import com.ajlabs.forevely.data.type.Leisure
import com.ajlabs.forevely.data.type.Mind
import com.ajlabs.forevely.data.type.Nature
import com.ajlabs.forevely.data.type.OpenQuestion
import com.ajlabs.forevely.data.type.Social
import com.ajlabs.forevely.data.type.Sports
import com.ajlabs.forevely.data.type.Technology
import com.ajlabs.forevely.feature.updateProfile.InterestCategory.Creativities
import com.ajlabs.forevely.feature.updateProfile.InterestCategory.Culinaries
import com.ajlabs.forevely.feature.updateProfile.InterestCategory.Leisures
import com.ajlabs.forevely.feature.updateProfile.InterestCategory.Minds
import com.ajlabs.forevely.feature.updateProfile.InterestCategory.Natures
import com.ajlabs.forevely.feature.updateProfile.InterestCategory.Socials
import com.ajlabs.forevely.feature.updateProfile.InterestCategory.Technologies
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Events
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Events.OnError
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.AddImageToState
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.CancelBioEdit
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.ClearPickedBitmaps
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.FetchUser
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.FinishEditBio
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.LoadImage
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.ObserveCameraPermission
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.ObserveGalleryPermission
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.RequestCameraPermission
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.RequestGalleryPermission
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SaveAboutMe
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SaveBio
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SaveGender
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SaveInterests
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SaveOpenQuestion
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SetCachedUser
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SetCameraPermissionStatus
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SetGalleryPermission
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SetIsLoading
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SetProfilePhotos
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SetUiUser
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.TappedAddPhoto
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.TappedCancelPhoto
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.TappedOnPhotoGrid
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.TappedRemovePhoto
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.TappedToEditAboutMe
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.TappedToEditBio
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.ToggleInterest
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.ToggleLanguage
import com.ajlabs.forevely.feature.updateProfile.model.ProfilePhoto
import com.ajlabs.forevely.pictures.PicturesService
import com.ajlabs.forevely.pictures.model.MediaSource
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.Optional.Companion
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.postInput
import kotlinx.coroutines.flow.catch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val MAX_INTERESTS = 10
private const val MAX_LANGUAGES = 5

private typealias UpdateProfileInputScope =
    InputHandlerScope<UpdateProfileContract.Inputs, Events, UpdateProfileContract.State>

internal class UpdateProfileInputHandler :
    KoinComponent,
    InputHandler<UpdateProfileContract.Inputs, Events, UpdateProfileContract.State> {
    private val userService by inject<UserService>()
    private val picturesService: PicturesService by inject()

    override suspend fun UpdateProfileInputScope.handleInput(input: UpdateProfileContract.Inputs) = when (input) {
        FetchUser -> handleFetchUser()
        is SetCachedUser -> updateState { it.copy(cachedUser = input.user) }
        is SetUiUser -> updateState { it.copy(uiUser = input.user) }

        is CancelBioEdit -> updateState {
            it.copy(
                isEditingBio = false,
                uiUser = it.uiUser?.profile?.copy(bio = input.bio)?.let { profile -> it.uiUser.copy(profile = profile) }
            )
        }

        is SetIsLoading -> updateState { it.copy(isLoading = input.isLoading) }
        is LoadImage -> onLoadImage(input.url)
        is AddImageToState -> onAddImageToState(input.imageUrl)
        is SaveBio -> saveBio(input.bio ?: "")
        is TappedToEditAboutMe -> TODO()
        is TappedOnPhotoGrid -> tappedOnPhotoGrid(input.gridIndex)
        TappedToEditBio -> updateState { it.copy(isEditingBio = true) }
        FinishEditBio -> updateState { it.copy(isEditingBio = false) }

        is ToggleInterest -> {
            when (input.category) {
                Creativities -> updateCreativities(input)
                InterestCategory.Sports -> updateSports(input)
                Culinaries -> updateCulinaries(input)
                Leisures -> updateLeasures(input)
                Minds -> updateMinds(input)
                Natures -> updateNatures(input)
                Socials -> updateSocials(input)
                Technologies -> updateTechnoligies(input)
            }
        }

//        is SetOpeningQuestionState -> updateState { it.copy(openQuestion = input.openingQuestion) }
        is SaveInterests -> saveInterests(input.interests)
        is SaveAboutMe -> saveAboutMe(input.aboutMe)
        is SaveOpenQuestion -> saveOpenQuestion(input.openQuestion)

        is ToggleLanguage -> handleToggleLanguage(input)

        is SaveGender -> saveGender(gender = input.gender)

        ClearPickedBitmaps -> updateState { it.copy(profilePhotos = emptyList()) }
        ObserveCameraPermission -> observeCameraPermission()
        ObserveGalleryPermission -> observeGalleryPermission()
        RequestCameraPermission -> requestCameraPermission()
        RequestGalleryPermission -> requestGalleryPermission()
        is SetCameraPermissionStatus -> updateState { it.copy(cameraPermissionStatus = input.status) }
        is SetGalleryPermission -> updateState { it.copy(galleryPermissionStatus = input.status) }
        is SetProfilePhotos -> updateState { it.copy(profilePhotos = input.photos) }
        is TappedAddPhoto -> handleAddPhoto(input.gridTap, input.source)
        TappedCancelPhoto -> updateState { it.copy(photoGridTap = null) }
        is TappedRemovePhoto -> updateState {
            it.copy(
                photoGridTap = null,
                profilePhotos = it.profilePhotos.toMutableList().apply {
                    set(input.gridTap.gridIndex, ProfilePhoto())
                }
            )
        }
    }

    private suspend fun UpdateProfileInputScope.handleAddPhoto(gridTap: PhotoGridTap, source: MediaSource) {
        when (source) {
            MediaSource.CAMERA -> pickImageFromCamera()
            MediaSource.GALLERY -> pickImageFromGallery(gridTap)
        }
        postInput(TappedCancelPhoto)
    }

    private suspend fun UpdateProfileInputScope.tappedOnPhotoGrid(gridIndex: Int) {
        logger.debug("Tapped on photo grid $gridIndex. Updating state...")

        updateState {
            if (it.profilePhotos[gridIndex].bitmap != null) {
                logger.debug("Tapped on photo grid $gridIndex. Was slot empty? false")
                it.copy(photoGridTap = PhotoGridTap(gridIndex, false))
            } else {
                logger.debug("Tapped on photo grid $gridIndex. Was slot empty? true")
                it.copy(photoGridTap = PhotoGridTap(gridIndex, true))
            }
        }
    }

    private fun UpdateProfileInputScope.observeCameraPermission() {
        sideJob("observeCameraPermission") {
            infiniteFlow { picturesService.checkCameraPermission() }
                .catch { logger.error(it) }
                .collect { postInput(SetCameraPermissionStatus(it)) }
        }
    }

    private fun UpdateProfileInputScope.observeGalleryPermission() {
        sideJob("observeGalleryPermission") {
            infiniteFlow { picturesService.checkGalleryPermission() }
                .catch { logger.error(it) }
                .collect { postInput(SetGalleryPermission(it)) }
        }
    }

    private suspend fun UpdateProfileInputScope.pickImageFromCamera() {
        val currentProfilePhotos = getCurrentState().profilePhotos
        getCurrentState().cameraPermissionStatus.let { status ->
            when (currentPlatform) {
                Platform.ANDROID -> {
                    if (status == PermissionStatus.GRANTED) {
                        val pickedBitmaps = getCurrentState().profilePhotos
                        sideJob("PickImageFromCamera") {
                            picturesService.pickImage(MediaSource.CAMERA)?.let {
                                logger.debug("Image picked from camera")
                                val photo = ProfilePhoto(it)
                                val updatedPhotos = currentProfilePhotos.toMutableList().apply {
                                    set(indexOfFirst { photo -> photo.bitmap == null }, photo)
                                }
                                postInput(SetProfilePhotos(updatedPhotos))
                            } ?: run {
                                logger.debug("Image not picked from camera")
                                postEvent(OnError("Error picking image"))
                            }
                        }
                    } else {
                        requestCameraPermission()
                    }
                }

                Platform.IOS -> {
                    postEvent(OnError("Picker is disabled on iOS"))
                }
            }
        }
    }

    private suspend fun UpdateProfileInputScope.pickImageFromGallery(gridTap: PhotoGridTap) {
        val currentProfilePhotos = getCurrentState().profilePhotos
        sideJob("PickImageFromGallery") {
            picturesService.pickImage(MediaSource.GALLERY)?.let {
                val photo = ProfilePhoto(it)
                val updatedPhotos = currentProfilePhotos.toMutableList().apply {
                    set(indexOfFirst { photo -> photo.bitmap == null }, photo)
                }

                postInput(SetProfilePhotos(updatedPhotos))
                logger.debug("Image picked from gallery")
            } ?: run {
                logger.debug("Image not picked from gallery")
                postEvent(OnError("Error picking image"))
            }
        }
    }

    private fun UpdateProfileInputScope.requestCameraPermission() {
        sideJob("RequestCameraPermission") {
            picturesService.requestCameraPermission()
        }
    }

    private fun UpdateProfileInputScope.requestGalleryPermission() {
        sideJob("RequestGalleryPermission") {
            picturesService.requestGalleryPermission()
        }
    }

    private suspend fun UpdateProfileInputScope.handleToggleLanguage(
        input: ToggleLanguage,
    ) {
        val currentState = getCurrentState()
        val languages = currentState.uiUser?.profile?.aboutMe?.languages ?: emptyList()
        val newLanguages = if (input.isChecked) {
            if (languages.size < MAX_LANGUAGES)
                languages + input.language
            else languages
        } else {
            languages - input.language
        }

        updateState { state ->
            val aboutMe = state.uiUser?.profile?.aboutMe?.copy(languages = newLanguages)
            val profile = aboutMe?.let { state.uiUser.profile.copy(aboutMe = aboutMe) }

            state.copy(uiUser = profile?.let { state.uiUser.copy(profile = profile) })
        }
    }

    private suspend fun UpdateProfileInputScope.updateTechnoligies(
        input: ToggleInterest,
    ) {
        val state = getCurrentState()
        val technology: List<Technology> = state.uiUser?.profile?.interests?.technologies ?: emptyList()
        val newTechnology = if (input.isChecked) {
            if (state.uiUser?.profile?.interests?.size()!! < MAX_INTERESTS)
                technology + Technology.valueOf(input.name)
            else technology
        } else {
            technology - Technology.valueOf(input.name)
        }

        state.uiUser?.profile?.let { profile ->
            val uiUser =
                state.uiUser.copy(profile = profile.copy(interests = state.uiUser.profile.interests?.copy(technologies = newTechnology)))
            updateState { it.copy(uiUser = uiUser) }
        }
    }

    private suspend fun UpdateProfileInputScope.updateSocials(
        input: ToggleInterest,
    ) {
        val state = getCurrentState()
        val social: List<Social> = state.uiUser?.profile?.interests?.socials ?: emptyList()
        val newSocial = if (input.isChecked) {
            if (state.uiUser?.profile?.interests?.size()!! < MAX_INTERESTS)
                social + Social.valueOf(input.name)
            else social
        } else {
            social - Social.valueOf(input.name)
        }

        state.uiUser?.profile?.let { profile ->
            val uiUser =
                state.uiUser.copy(profile = profile.copy(interests = state.uiUser.profile.interests?.copy(socials = newSocial)))
            updateState { it.copy(uiUser = uiUser) }
        }
    }

    private suspend fun UpdateProfileInputScope.updateNatures(
        input: ToggleInterest,
    ) {
        val state = getCurrentState()
        val nature: List<Nature> = state.uiUser?.profile?.interests?.natures ?: emptyList()
        val newNature = if (input.isChecked) {
            if (state.uiUser?.profile?.interests?.size()!! < MAX_INTERESTS)
                nature + Nature.valueOf(input.name)
            else nature
        } else {
            nature - Nature.valueOf(input.name)
        }

        state.uiUser?.profile?.let { profile ->
            val uiUser =
                state.uiUser.copy(profile = profile.copy(interests = state.uiUser.profile.interests?.copy(natures = newNature)))
            updateState { it.copy(uiUser = uiUser) }
        }
    }

    private suspend fun UpdateProfileInputScope.updateMinds(input: ToggleInterest) {
        val state = getCurrentState()
        val mind: List<Mind> = state.uiUser?.profile?.interests?.minds ?: emptyList()
        val newMind = if (input.isChecked) {
            if (state.uiUser?.profile?.interests?.size()!! < MAX_INTERESTS)
                mind + Mind.valueOf(input.name)
            else mind
        } else {
            mind - Mind.valueOf(input.name)
        }

        state.uiUser?.profile?.let { profile ->
            val uiUser =
                state.uiUser.copy(profile = profile.copy(interests = state.uiUser.profile.interests?.copy(minds = newMind)))
            updateState { it.copy(uiUser = uiUser) }
        }
    }

    private suspend fun UpdateProfileInputScope.updateLeasures(input: ToggleInterest) {
        val state = getCurrentState()
        val leisure: List<Leisure> = state.uiUser?.profile?.interests?.leisures ?: emptyList()
        val newLeisure = if (input.isChecked) {
            if (state.uiUser?.profile?.interests?.size()!! < MAX_INTERESTS)
                leisure + Leisure.valueOf(input.name)
            else leisure
        } else {
            leisure - Leisure.valueOf(input.name)
        }

        state.uiUser?.profile?.let { profile ->
            val uiUser =
                state.uiUser.copy(profile = profile.copy(interests = state.uiUser.profile.interests?.copy(leisures = newLeisure)))
            updateState { it.copy(uiUser = uiUser) }
        }
    }

    private suspend fun UpdateProfileInputScope.updateCulinaries(input: ToggleInterest) {
        val state = getCurrentState()
        logger.debug("interests: ${state.cachedUser?.profile?.interests}")
        val culinary: List<Culinary> = state.uiUser?.profile?.interests?.culinaries ?: emptyList()
        logger.debug("culinary: $culinary")
        val newCulinary = if (input.isChecked) {
            if (state.uiUser?.profile?.interests?.size()!! < MAX_INTERESTS)
                culinary + Culinary.valueOf(input.name)
            else culinary
        } else {
            culinary - Culinary.valueOf(input.name)
        }
        logger.debug("newCulinary: $newCulinary")

        state.uiUser?.profile?.let { profile ->
            val uiUser =
                state.uiUser.copy(profile = profile.copy(interests = state.uiUser.profile.interests?.copy(culinaries = newCulinary)))
            updateState { it.copy(uiUser = uiUser) }
        }
    }

    private suspend fun UpdateProfileInputScope.updateSports(input: ToggleInterest) {
        val state = getCurrentState()
        val sports: List<Sports> = state.uiUser?.profile?.interests?.sports ?: emptyList()
        val newSports = if (input.isChecked) {
            if (state.uiUser?.profile?.interests?.size()!! < MAX_INTERESTS)
                sports + Sports.valueOf(input.name)
            else sports
        } else {
            sports - Sports.valueOf(input.name)
        }

        state.uiUser?.profile?.let { profile ->
            val uiUser =
                state.uiUser.copy(profile = profile.copy(interests = state.uiUser.profile.interests?.copy(sports = newSports)))
            updateState { it.copy(uiUser = uiUser) }
        }
    }

    private suspend fun UpdateProfileInputScope.updateCreativities(input: ToggleInterest) {
        val state = getCurrentState()
        val creative: List<Creative> = state.uiUser?.profile?.interests?.creativities ?: emptyList()
        val newCreative = if (input.isChecked) {
            if (state.uiUser?.profile?.interests?.size()!! < MAX_INTERESTS)
                creative + Creative.valueOf(input.name)
            else creative
        } else {
            creative - Creative.valueOf(input.name)
        }

        state.uiUser?.profile?.let { profile ->
            val uiUser =
                state.uiUser.copy(profile = profile.copy(interests = state.uiUser.profile.interests?.copy(creativities = newCreative)))
            updateState { it.copy(uiUser = uiUser) }
        }
    }

    private fun UpdateProfileInputScope.saveOpenQuestion(openQuestion: OpenQuestion) {
        sideJob("saveOpenQuestion") {
            userService.updateUser(openQuestion = openQuestion)
                .onFailure { postEvent(OnError(it.message ?: "")) }
        }
    }

    private fun UpdateProfileInputScope.saveGender(gender: Gender?) {
        sideJob("saveGender") {
            postInput(SetIsLoading(true))
            userService.updateUser(gender = gender)
                .onFailure { postEvent(OnError(it.message ?: "")) }
            postInput(SetIsLoading(false))
        }
    }

    private fun UpdateProfileInputScope.saveInterests(interests: Interests?) {
        sideJob("saveInterests") {
            postInput(SetIsLoading(true))
            userService.updateUser(interests = interests?.toInterestsInput())
                .onFailure { postEvent(OnError(it.message ?: "")) }
            postInput(SetIsLoading(false))
        }
    }

    private fun UpdateProfileInputScope.saveAboutMe(aboutMe: AboutMe) {
        sideJob("saveAboutMe") {
            postInput(SetIsLoading(true))
            userService.updateUser(aboutMe = aboutMe.toInput())
                .onFailure { postEvent(OnError(it.message ?: "")) }
            postInput(SetIsLoading(false))
        }
    }

    private suspend fun UpdateProfileInputScope.saveBio(bio: String) {
        sideJob("saveBio") {
            postInput(SetIsLoading(true))
            userService.updateUser(bio = bio)
                .onSuccess {
                    postInput(FinishEditBio)
                }
                .onFailure { postEvent(OnError(it.message ?: "")) }
            postInput(SetIsLoading(false))
        }
    }

    private suspend fun UpdateProfileInputScope.handleFetchUser() {
        sideJob("handleFetchUser") {
            postInput(SetIsLoading(true))
            userService.getUser().collect {
                it.fold(
                    onSuccess = { data ->
                        val user = data.getUser
                        postInput(SetCachedUser(user))
                        postInput(SetUiUser(user))
                    },
                    onFailure = { error -> postEvent(OnError(error.message ?: "")) }
                )
            }
            postInput(SetIsLoading(false))
        }
    }
}

private fun AboutMe.toInput(): AboutMeInput {
    return AboutMeInput(
        languages = languages,
        children = Optional.present(children),
        diet = Companion.present(diet),
        drinking = Companion.present(drinking),
        education = Companion.present(education),
        fitness = Companion.present(fitness),
        height = Companion.present(height),
        loveLanguage = Companion.present(loveLanguage),
        personality = Companion.present(personality),
        pets = Companion.present(pets),
        politics = Companion.present(politics),
        relationship = Companion.present(relationship),
        religion = Companion.present(religion),
        smoking = Companion.present(smoking),
        zodiac = Companion.present(zodiac)
    )
}

private fun Interests.toInterestsInput(): InterestsInput {
    return InterestsInput(
        creativities = Companion.present(creativities),
        culinaries = Companion.present(culinaries),
        leisures = Companion.present(leisures),
        minds = Companion.present(minds),
        natures = Companion.present(natures),
        socials = Companion.present(socials),
        sports = Companion.present(sports),
        technologies = Companion.present(technologies)
    )
}

private fun Interests?.size(): Int {
    return this?.let {
        var size = 0
        if (it.creativities != null) size += it.creativities!!.size
        if (it.culinaries != null) size += it.culinaries!!.size
        if (it.leisures != null) size += it.leisures!!.size
        if (it.minds != null) size += it.minds!!.size
        if (it.natures != null) size += it.natures!!.size
        if (it.socials != null) size += it.socials!!.size
        if (it.sports != null) size += it.sports!!.size
        if (it.technologies != null) size += it.technologies!!.size
        size
    } ?: 0
}

private suspend fun UpdateProfileInputScope.onAddImageToState(imageUrl: String) {
    updateState { it.copy(imageUrls = it.imageUrls + imageUrl) }
}

private suspend fun UpdateProfileInputScope.onLoadImage(url: String) {
    sideJob("loadImage") {
//        val image = fetchImage(url)
        postInput(LoadImage(url))
    }
}
