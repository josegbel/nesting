package com.ajlabs.forevely.feature.updateProfile

import com.ajlabs.forevely.core.models.PermissionStatus
import com.ajlabs.forevely.data.GetUserQuery
import com.ajlabs.forevely.data.GetUserQuery.AboutMe
import com.ajlabs.forevely.data.GetUserQuery.Interests
import com.ajlabs.forevely.data.type.Gender
import com.ajlabs.forevely.data.type.Language
import com.ajlabs.forevely.data.type.OpenQuestion
import com.ajlabs.forevely.feature.updateProfile.model.ProfilePhoto
import com.ajlabs.forevely.pictures.model.MediaSource
import org.koin.core.component.KoinComponent


object UpdateProfileContract : KoinComponent {

    data class State(
        val isLoading: Boolean = false,
        val imageUrls: List<String> = emptyList(),

        val cachedUser: GetUserQuery.GetUser? = null,
        val uiUser: GetUserQuery.GetUser? = null,

        val isEditingBio: Boolean = false,
        val photoGridTap: PhotoGridTap? = null,

        val profilePhotos: List<ProfilePhoto> = arrayListOf(
            ProfilePhoto(),
            ProfilePhoto(),
            ProfilePhoto(),
            ProfilePhoto(),
            ProfilePhoto(),
            ProfilePhoto(),
        ),
        val galleryPermissionStatus: PermissionStatus = PermissionStatus.NOT_DETERMINED,
        val cameraPermissionStatus: PermissionStatus = PermissionStatus.NOT_DETERMINED,
    )

    sealed interface Inputs {
        data object FetchUser : Inputs
        data class SetIsLoading(val isLoading: Boolean) : Inputs
        data class SetUiUser(val user: GetUserQuery.GetUser) : Inputs
        data class SetCachedUser(val user: GetUserQuery.GetUser) : Inputs

        data object TappedToEditBio : Inputs
        data object FinishEditBio : Inputs
        data class TappedOnPhotoGrid(val gridIndex: Int) : Inputs
        data class TappedToEditAboutMe(val aboutMe: AboutMe) : Inputs

        data class CancelBioEdit(val bio: String?) : Inputs
        data class LoadImage(val url: String) : Inputs
        data class AddImageToState(val imageUrl: String) : Inputs

        data class SaveBio(val bio: String?) : Inputs
        data class SaveInterests(val interests: Interests?) : Inputs
        data class SaveAboutMe(val aboutMe: AboutMe) : Inputs
        data class SaveGender(val gender: Gender?) : Inputs
        data class SaveOpenQuestion(val openQuestion: OpenQuestion) : Inputs

        data class ToggleInterest(val category: InterestCategory, val name: String, val isChecked: Boolean) : Inputs
        data class ToggleLanguage(val language: Language, val isChecked: Boolean) : Inputs

        data object ObserveCameraPermission : Inputs
        data object ObserveGalleryPermission : Inputs
        data object RequestCameraPermission : Inputs
        data class SetCameraPermissionStatus(val status: PermissionStatus) : Inputs
        data object RequestGalleryPermission : Inputs
        data class SetGalleryPermission(val status: PermissionStatus) : Inputs
        data class SetProfilePhotos(val photos: List<ProfilePhoto>) : Inputs
        data object ClearPickedBitmaps : Inputs
        data object TappedCancelPhoto : Inputs
        data class TappedAddPhoto(val gridTap: PhotoGridTap, val source: MediaSource) : Inputs
        data class TappedRemovePhoto(val gridTap: PhotoGridTap) : Inputs
    }

    sealed interface Events {
        data class OnError(val message: String) : Events
    }
}

data class PhotoGridTap(val gridIndex: Int, val isGridSlotEmpty: Boolean)
