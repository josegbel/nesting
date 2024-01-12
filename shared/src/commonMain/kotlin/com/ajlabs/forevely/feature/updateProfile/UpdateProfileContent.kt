package com.ajlabs.forevely.feature.updateProfile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.ajlabs.forevely.components.BottomOption
import com.ajlabs.forevely.components.BottomOptionsMenu
import com.ajlabs.forevely.components.BottomSheet
import com.ajlabs.forevely.components.ModalSheet
import com.ajlabs.forevely.components.SimpleTopBar
import com.ajlabs.forevely.data.GetUserQuery.AboutMe
import com.ajlabs.forevely.data.GetUserQuery.GetUser
import com.ajlabs.forevely.data.GetUserQuery.Interests
import com.ajlabs.forevely.data.type.Children
import com.ajlabs.forevely.data.type.Diet
import com.ajlabs.forevely.data.type.Drinking
import com.ajlabs.forevely.data.type.Education
import com.ajlabs.forevely.data.type.Fitness
import com.ajlabs.forevely.data.type.Gender
import com.ajlabs.forevely.data.type.Language
import com.ajlabs.forevely.data.type.LoveLanguage
import com.ajlabs.forevely.data.type.OpenQuestion
import com.ajlabs.forevely.data.type.Personality
import com.ajlabs.forevely.data.type.Pet
import com.ajlabs.forevely.data.type.Politics
import com.ajlabs.forevely.data.type.Relationship
import com.ajlabs.forevely.data.type.Religion
import com.ajlabs.forevely.data.type.Smoking
import com.ajlabs.forevely.data.type.Zodiac
import com.ajlabs.forevely.feature.matcher.ModalShow
import com.ajlabs.forevely.feature.matcher.sections.Chip
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SaveAboutMe
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SaveBio
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SaveGender
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SaveInterests
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SaveOpenQuestion
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.SetUiUser
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.TappedAddPhoto
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.TappedOnPhotoGrid
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.TappedRemovePhoto
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.TappedToEditBio
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.ToggleInterest
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.Inputs.ToggleLanguage
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.State
import com.ajlabs.forevely.feature.updateProfile.sections.AboutMeSection
import com.ajlabs.forevely.feature.updateProfile.sections.LanguagesSection
import com.ajlabs.forevely.feature.updateProfile.sections.MediaSection
import com.ajlabs.forevely.feature.updateProfile.sections.MyBioSection
import com.ajlabs.forevely.feature.updateProfile.sections.MyInterestsSection
import com.ajlabs.forevely.feature.updateProfile.sections.OpeningQuestionSection
import com.ajlabs.forevely.feature.updateProfile.sections.icon
import com.ajlabs.forevely.feature.updateProfile.sections.isEmpty
import com.ajlabs.forevely.feature.updateProfile.sheets.OptionPickerModal
import com.ajlabs.forevely.feature.updateProfile.sheets.HeightModal
import com.ajlabs.forevely.feature.updateProfile.sheets.LanguagesModal
import com.ajlabs.forevely.feature.updateProfile.sheets.MyInterestsModal
import com.ajlabs.forevely.feature.updateProfile.sheets.OpeningQuestionModal
import com.ajlabs.forevely.feature.updateProfile.sheets.stringKey
import com.ajlabs.forevely.localization.Strings.AppName
import com.ajlabs.forevely.localization.Strings.UpdateProfile
import com.ajlabs.forevely.localization.Strings.UpdateProfile.NoInterests
import com.ajlabs.forevely.localization.Strings.UpdateProfile.ProfileStrengthContent
import com.ajlabs.forevely.localization.Strings.User
import com.ajlabs.forevely.localization.Strings.User.Drink
import com.ajlabs.forevely.localization.Strings.User.Religion.ModalTitle
import com.ajlabs.forevely.localization.Strings.User.Religion.Name
import com.ajlabs.forevely.localization.getString
import com.ajlabs.forevely.pictures.model.MediaSource.CAMERA
import com.ajlabs.forevely.pictures.model.MediaSource.GALLERY
import com.ajlabs.forevely.theme.Dimensions
import kotlinx.coroutines.launch

const val FADE_IN_ANIM_DURATION = 500
const val MAX_BIO_LENGTH = 300
const val GRID_SIZE: Int = 6

@Composable
internal fun UpdateProfileContent(
    onError: suspend (String) -> Unit,
    onBackClicked: () -> Unit,
    isModalShowing: (isShowingModal: Boolean) -> Unit,
) {
    var interestsModal by remember { modalShowMutableState(ModalSheet.MyInterests) }
    var openingQuestionModal by remember { modalShowMutableState(ModalSheet.OpeningQuestion) }
    var languagesModal by remember { modalShowMutableState(ModalSheet.Languages) }
    var genderModal by remember { modalShowMutableState(ModalSheet.Gender) }
    var religionModal by remember { modalShowMutableState(ModalSheet.Religion) }
    var childrenModal by remember { modalShowMutableState(ModalSheet.Children) }
    var dietModal by remember { modalShowMutableState(ModalSheet.Diet) }
    var fitnessModal by remember { modalShowMutableState(ModalSheet.Fitness) }
    var drinkingModal by remember { modalShowMutableState(ModalSheet.Drinking) }
    var educationModal by remember { modalShowMutableState(ModalSheet.Education) }
    var heightModal by remember { modalShowMutableState(ModalSheet.Height) }
    var loveLanguageModal by remember { modalShowMutableState(ModalSheet.LoveLanguage) }
    var personalityModal by remember { modalShowMutableState(ModalSheet.Personality) }
    var petsModal by remember { modalShowMutableState(ModalSheet.Pets) }
    var politicsModal by remember { modalShowMutableState(ModalSheet.Politics) }
    var relationshipModal by remember { modalShowMutableState(ModalSheet.Relationship) }
    var smokingModal by remember { modalShowMutableState(ModalSheet.Smoking) }
    var zodiacModal by remember { modalShowMutableState(ModalSheet.Zodiac) }

    val scope = rememberCoroutineScope()
    val vm = remember(scope) {
        UpdateProfileViewModel(scope, onError)
    }
    val state by vm.observeStates().collectAsState()
    val scrollableState = rememberLazyListState()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            state = scrollableState,
            userScrollEnabled = !state.isEditingBio,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(
                top = (Dimensions.TOP_BAR_HEIGHT).dp,
                bottom = (Dimensions.BOTTOM_BAR_HEIGHT).dp,
            ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            item { ProfileStrength(state) }
            item {
                MediaSection(
                    userName = state.cachedUser?.details?.name ?: "",
                    profilePhotos = state.profilePhotos,
                    onImageClicked = { gridIndex -> vm.trySend(TappedOnPhotoGrid(gridIndex)) }
                )
            }
            item {
                MyInterestsSection(
                    interests = state.cachedUser?.profile?.interests,
                    onClick = {
                        interestsModal = interestsModal.copy(show = true)
                        isModalShowing(true)
                    }
                )
            }
            item {
                OpeningQuestionSection(
                    openingQuestion = state.cachedUser?.profile?.openQuestion,
                    onClick = {
                        openingQuestionModal = openingQuestionModal.copy(show = true)
                        isModalShowing(true)
                    }
                )
            }
            item {
                MyBioSection(
                    state.isEditingBio,
                    state.uiUser?.profile?.bio,
                    onTextFieldFocusGained = {
                        scope.launch {
                            scrollableState.animateScrollToItem(4)
                            vm.send(TappedToEditBio)
                        }
                    },
                    onValueChanged = {
                        refreshUiUser(state.uiUser, vm, bio = it)
                    },
                )
            }
            item {
                AboutMeSection(
                    state = state,
                    onGenderClicked = {
                        genderModal = genderModal.copy(show = true)
                        isModalShowing(true)
                    },
                    onChildrenClicked = {
                        childrenModal = childrenModal.copy(show = true)
                        isModalShowing(true)
                    },
                    onDietClicked = {
                        dietModal = dietModal.copy(show = true)
                        isModalShowing(true)
                    },
                    onDrinkingClicked = {
                        drinkingModal = drinkingModal.copy(show = true)
                        isModalShowing(true)
                    },
                    onEducationClicked = {
                        educationModal = educationModal.copy(show = true)
                        isModalShowing(true)
                    },
                    onFitnessClicked = {
                        fitnessModal = fitnessModal.copy(show = true)
                        isModalShowing(true)
                    },
                    onHeightClicked = {
                        heightModal = heightModal.copy(show = true)
                        isModalShowing(true)
                    },
                    onLoveLanguageClicked = {
                        loveLanguageModal = loveLanguageModal.copy(show = true)
                        isModalShowing(true)
                    },
                    onPersonalityClicked = {
                        personalityModal = personalityModal.copy(show = true)
                        isModalShowing(true)
                    },
                    onPetsClicked = {
                        petsModal = petsModal.copy(show = true)
                        isModalShowing(true)
                    },
                    onPoliticsClicked = {
                        politicsModal = politicsModal.copy(show = true)
                        isModalShowing(true)
                    },
                    onRelationshipClicked = {
                        relationshipModal = relationshipModal.copy(show = true)
                        isModalShowing(true)
                    },
                    onReligionClicked = {
                        religionModal = religionModal.copy(show = true)
                        isModalShowing(true)
                    },
                    onSmokingClicked = {
                        smokingModal = smokingModal.copy(show = true)
                        isModalShowing(true)
                    },
                    onZodiacClicked = {
                        zodiacModal = zodiacModal.copy(show = true)
                        isModalShowing(true)
                    },
                )
            }
            item {
                LanguagesSection(
                    languages = state.cachedUser?.profile?.aboutMe?.languages,
                    onClick = {
                        languagesModal = languagesModal.copy(show = true)
                        isModalShowing(true)
                    }
                )
            }
        }
        AnimatedVisibility(
            state.isEditingBio,
            enter = fadeIn(tween(FADE_IN_ANIM_DURATION)),
            exit = fadeOut(tween(FADE_IN_ANIM_DURATION)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.ime),
        ) {
            SaveActionButtons(
                onCancelClicked = {
                    vm.trySend(Inputs.CancelBioEdit(state.cachedUser?.profile?.bio))
                },
                onSaveClicked = { vm.trySend(SaveBio(state.uiUser?.profile?.bio)) },
            )
        }
        SimpleTopBar(
            onBackClicked = { onBackClicked() },
            headerText = getString(AppName),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
        )
        BottomSheet(
            showSheet = interestsModal.show,
            modalSheet = interestsModal.modalSheet,
            modifier = Modifier
                .zIndex((interestsModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            MyInterestsModal(
                onCloseClicked = {
                    interestsModal = interestsModal.copy(show = false)
                    isModalShowing(false)
                    refreshUiUser(state.uiUser, vm, interests = state.uiUser?.profile?.interests)
                },
                onSaveClicked = {
                    interestsModal = interestsModal.copy(show = false)
                    isModalShowing(false)
                    vm.trySend(SaveInterests(it))
                },
                state = state,
            ) { category, name, toggleSelection ->
                vm.trySend(ToggleInterest(category, name, toggleSelection))
            }
        }
        BottomSheet(
            showSheet = openingQuestionModal.show,
            modalSheet = openingQuestionModal.modalSheet,
            modifier = Modifier
                .zIndex((openingQuestionModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            OpeningQuestionModal(
                onCloseClicked = {
                    openingQuestionModal = openingQuestionModal.copy(show = false)
                    isModalShowing(false)
                    refreshUiUser(state.uiUser, vm, openingQuestion = state.uiUser?.profile?.openQuestion)
                },
                onSaveClicked = {
                    vm.trySend(SaveOpenQuestion(it))
                },
                onItemSelected = { openingQuestion ->
                    refreshUiUser(state.uiUser, vm, openingQuestion = openingQuestion)
                },
                state = state,
            )
        }
        BottomSheet(
            showSheet = languagesModal.show,
            modalSheet = languagesModal.modalSheet,
            modifier = Modifier
                .zIndex((languagesModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            LanguagesModal(
                onCloseClicked = {
                    languagesModal = languagesModal.copy(show = false)
                    isModalShowing(false)

                    refreshUiUser(state.uiUser, vm, aboutMe = state.cachedUser?.profile?.aboutMe)
                },
                onSaveClicked = {
                    languagesModal = languagesModal.copy(show = false)
                    isModalShowing(false)

                    val aboutMe = state.cachedUser?.profile?.aboutMe?.copy(languages = it) ?: emptyAboutMe(it)
                    vm.trySend(SaveAboutMe(aboutMe))
                },
                onLanguageChecked = { language, isChecked ->
                    vm.trySend(ToggleLanguage(language, isChecked))
                },
                state = state,
            )
        }

        BottomSheet(
            showSheet = genderModal.show,
            modalSheet = genderModal.modalSheet,
            modifier = Modifier
                .zIndex((genderModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            OptionPickerModal(
                onCloseClicked = {
                    genderModal = genderModal.copy(show = false)
                    isModalShowing(false)
                    refreshUiUser(state.uiUser, vm, gender = state.cachedUser?.details?.gender)
                },
                onSaveClicked = {
                    genderModal = genderModal.copy(show = false)
                    isModalShowing(false)

                    vm.trySend(SaveGender(state.uiUser?.details?.gender))
                },
                onItemClicked = { refreshUiUser(state.uiUser, vm, gender = it) },
                headerText = getString(User.Gender.Name),
                subtitle = getString(User.Gender.ModalTitle),
                enumValues = Gender.values().filter { it != Gender.UNKNOWN__ }.toTypedArray(),
                stringMapper = Gender::stringKey,
                stateField = state.uiUser?.details?.gender
            )
        }

        BottomSheet(
            showSheet = religionModal.show,
            modalSheet = religionModal.modalSheet,
            modifier = Modifier
                .zIndex((religionModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            OptionPickerModal(
                onCloseClicked = {
                    religionModal = religionModal.copy(show = false)
                    isModalShowing(false)

                    refreshUiUser(state.uiUser, vm, aboutMe = state.cachedUser?.profile?.aboutMe)
                },
                onSaveClicked = {
                    religionModal = religionModal.copy(show = false)
                    isModalShowing(false)

                    val aboutMe =
                        state.uiUser?.profile?.aboutMe?.copy(religion = state.uiUser?.profile?.aboutMe?.religion)
                            ?: emptyAboutMe(
                                withReligion = state.uiUser?.profile?.aboutMe?.religion
                            )
                    vm.trySend(SaveAboutMe(aboutMe))
                },
                onItemClicked = {
                    refreshUiUser(state.uiUser, vm, religion = it)
                },
                headerText = getString(Name),
                subtitle = getString(ModalTitle),
                enumValues = Religion.values().filter { it != Religion.UNKNOWN__ }.toTypedArray(),
                stringMapper = Religion::stringKey,
                stateField = state.uiUser?.profile?.aboutMe?.religion
            )
        }

        BottomSheet(
            showSheet = childrenModal.show,
            modalSheet = childrenModal.modalSheet,
            modifier = Modifier
                .zIndex((childrenModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            OptionPickerModal(
                onCloseClicked = {
                    childrenModal = childrenModal.copy(show = false)
                    isModalShowing(false)

                    refreshUiUser(state.uiUser, vm, aboutMe = state.cachedUser?.profile?.aboutMe)
                },
                onSaveClicked = {
                    childrenModal = childrenModal.copy(show = false)
                    isModalShowing(false)

                    val aboutMe =
                        state.uiUser?.profile?.aboutMe?.copy(children = state.uiUser?.profile?.aboutMe?.children)
                            ?: emptyAboutMe(withChildren = state.uiUser?.profile?.aboutMe?.children)
                    vm.trySend(SaveAboutMe(aboutMe))
                },
                onItemClicked = {
                    val aboutMe =
                        state.uiUser?.profile?.aboutMe?.copy(children = it)
                            ?: emptyAboutMe(withChildren = it)
                    val profile = state.cachedUser?.profile?.copy(aboutMe = aboutMe)
                    val uiUser = profile?.let { state.uiUser?.copy(profile = profile) }
                    uiUser?.let { vm.trySend(SetUiUser(uiUser)) }
                },
                headerText = getString(User.Children.Name),
                subtitle = getString(User.Children.ModalTitle),
                stringMapper = Children::stringKey,
                stateField = state.uiUser?.profile?.aboutMe?.children,
                enumValues = Children.values().filter { it != Children.UNKNOWN__ }.toTypedArray()
            )
        }

        BottomSheet(
            showSheet = dietModal.show,
            modalSheet = dietModal.modalSheet,
            modifier = Modifier
                .zIndex((dietModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            OptionPickerModal(
                onCloseClicked = {
                    dietModal = dietModal.copy(show = false)
                    isModalShowing(false)
                    refreshUiUser(state.uiUser, vm, aboutMe = state.cachedUser?.profile?.aboutMe)
                },
                onSaveClicked = {
                    dietModal = dietModal.copy(show = false)
                    isModalShowing(false)

                    val aboutMe =
                        state.uiUser?.profile?.aboutMe?.copy(diet = state.uiUser?.profile?.aboutMe?.diet)
                            ?: emptyAboutMe(withDiet = state.uiUser?.profile?.aboutMe?.diet)

                    vm.trySend(SaveAboutMe(aboutMe))
                },
                onItemClicked = {
                    val aboutMe =
                        state.uiUser?.profile?.aboutMe?.copy(diet = it)
                            ?: emptyAboutMe(withDiet = it)
                    val profile = state.cachedUser?.profile?.copy(aboutMe = aboutMe)
                    val uiUser = profile?.let { state.uiUser?.copy(profile = profile) }
                    uiUser?.let { vm.trySend(SetUiUser(uiUser)) }
                },
                headerText = getString(User.Diet.Name),
                subtitle = getString(User.Diet.ModalTitle),
                stringMapper = Diet::stringKey,
                stateField = state.uiUser?.profile?.aboutMe?.diet,
                enumValues = Diet.values().filter { it != Diet.UNKNOWN__ }.toTypedArray()
            )
        }

        BottomSheet(
            showSheet = drinkingModal.show,
            modalSheet = drinkingModal.modalSheet,
            modifier = Modifier
                .zIndex((drinkingModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            OptionPickerModal(
                onCloseClicked = {
                    drinkingModal = drinkingModal.copy(show = false)
                    isModalShowing(false)

                    refreshUiUser(state.uiUser, vm, aboutMe = state.cachedUser?.profile?.aboutMe)
                },
                onSaveClicked = {
                    drinkingModal = drinkingModal.copy(show = false)
                    isModalShowing(false)

                    val aboutMe =
                        state.uiUser?.profile?.aboutMe?.copy(drinking = state.uiUser?.profile?.aboutMe?.drinking)
                            ?: emptyAboutMe(
                                withDrinking = state.uiUser?.profile?.aboutMe?.drinking
                            )
                    vm.trySend(SaveAboutMe(aboutMe))
                },
                onItemClicked = {
                    val aboutMe =
                        state.uiUser?.profile?.aboutMe?.copy(drinking = it)
                            ?: emptyAboutMe(withDrinking = it)
                    val profile = state.cachedUser?.profile?.copy(aboutMe = aboutMe)
                    val uiUser = profile?.let { state.uiUser?.copy(profile = profile) }
                    uiUser?.let { vm.trySend(SetUiUser(uiUser)) }
                },
                headerText = getString(Drink.Name),
                subtitle = getString(Drink.ModalTitle),
                stringMapper = Drinking::stringKey,
                stateField = state.uiUser?.profile?.aboutMe?.drinking,
                enumValues = Drinking.values().filter { it != Drinking.UNKNOWN__ }.toTypedArray()
            )
        }

        BottomSheet(
            showSheet = fitnessModal.show,
            modalSheet = fitnessModal.modalSheet,
            modifier = Modifier
                .zIndex((fitnessModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            OptionPickerModal(
                onCloseClicked = {
                    fitnessModal = fitnessModal.copy(show = false)
                    isModalShowing(false)

                    refreshUiUser(state.uiUser, vm, aboutMe = state.cachedUser?.profile?.aboutMe)
                },
                onSaveClicked = {
                    fitnessModal = fitnessModal.copy(show = false)
                    isModalShowing(false)

                    val aboutMe =
                        state.uiUser?.profile?.aboutMe?.copy(fitness = state.uiUser?.profile?.aboutMe?.fitness)
                            ?: emptyAboutMe(
                                withFitness = state.uiUser?.profile?.aboutMe?.fitness
                            )
                    vm.trySend(SaveAboutMe(aboutMe))
                },
                onItemClicked = {
                    val aboutMe =
                        state.uiUser?.profile?.aboutMe?.copy(fitness = it)
                            ?: emptyAboutMe(withFitness = it)
                    val profile = state.cachedUser?.profile?.copy(aboutMe = aboutMe)
                    val uiUser = profile?.let { state.uiUser?.copy(profile = profile) }
                    uiUser?.let { vm.trySend(SetUiUser(uiUser)) }
                },
                headerText = getString(Drink.Name),
                subtitle = getString(Drink.ModalTitle),
                stringMapper = Fitness::stringKey,
                stateField = state.uiUser?.profile?.aboutMe?.fitness,
                enumValues = Fitness.values().filter { it != Fitness.UNKNOWN__ }.toTypedArray()
            )
        }

        BottomSheet(
            showSheet = educationModal.show,
            modalSheet = educationModal.modalSheet,
            modifier = Modifier
                .zIndex((educationModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            OptionPickerModal(
                onCloseClicked = {
                    educationModal = educationModal.copy(show = false)
                    isModalShowing(false)

                    refreshUiUser(state.uiUser, vm, aboutMe = state.cachedUser?.profile?.aboutMe)
                },
                onSaveClicked = {
                    educationModal = educationModal.copy(show = false)
                    isModalShowing(false)

                    val aboutMe =
                        state.uiUser?.profile?.aboutMe?.copy(education = state.uiUser?.profile?.aboutMe?.education)
                            ?: emptyAboutMe(withEducation = state.uiUser?.profile?.aboutMe?.education)

                    vm.trySend(SaveAboutMe(aboutMe))
                },
                onItemClicked = { refreshUiUser(state.uiUser, vm, education = it) },
                headerText = getString(User.Education.Name),
                subtitle = getString(User.Education.ModalTitle),
                stringMapper = Education::stringKey,
                stateField = state.uiUser?.profile?.aboutMe?.education,
                enumValues = Education.values().filter { it != Education.UNKNOWN__ }.toTypedArray()
            )
        }

        BottomSheet(
            showSheet = heightModal.show,
            modalSheet = heightModal.modalSheet,
            modifier = Modifier
                .zIndex((heightModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            HeightModal(
                onCloseClicked = {
                    heightModal = heightModal.copy(show = false)
                    isModalShowing(false)

                    refreshUiUser(state.uiUser, vm, aboutMe = state.cachedUser?.profile?.aboutMe)
                },
                onSaveClicked = {
                    heightModal = heightModal.copy(show = false)
                    isModalShowing(false)

                    val aboutMe =
                        state.uiUser?.profile?.aboutMe?.copy(height = state.uiUser?.profile?.aboutMe?.height)
                            ?: emptyAboutMe(withHeight = state.uiUser?.profile?.aboutMe?.height)

                    vm.trySend(SaveAboutMe(aboutMe))
                },
                onValueChanged = {
                    refreshUiUser(state.uiUser, vm, height = it.toInt())
                },
                height = state.uiUser?.profile?.aboutMe?.height ?: 0,
                headerText = getString(User.Height.Name),
                subtitle = getString(User.Height.ModalTitle),
            )
        }

        BottomSheet(
            showSheet = loveLanguageModal.show,
            modalSheet = loveLanguageModal.modalSheet,
            modifier = Modifier
                .zIndex((loveLanguageModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            OptionPickerModal(
                onCloseClicked = {
                    loveLanguageModal = loveLanguageModal.copy(show = false)
                    isModalShowing(false)
                    refreshUiUser(state.uiUser, vm, aboutMe = state.cachedUser?.profile?.aboutMe)
                },
                onSaveClicked = {
                    loveLanguageModal = loveLanguageModal.copy(show = false)
                    isModalShowing(false)

                    val aboutMe =
                        state.uiUser?.profile?.aboutMe?.copy(loveLanguage = state.uiUser?.profile?.aboutMe?.loveLanguage)
                            ?: emptyAboutMe(withLoveLanguage = state.uiUser?.profile?.aboutMe?.loveLanguage)

                    vm.trySend(SaveAboutMe(aboutMe))
                },
                onItemClicked = { refreshUiUser(state.uiUser, vm, loveLanguage = it) },
                headerText = getString(User.LoveLanguage.Name),
                subtitle = getString(User.LoveLanguage.ModalTitle),
                stringMapper = LoveLanguage::stringKey,
                stateField = state.uiUser?.profile?.aboutMe?.loveLanguage,
                enumValues = LoveLanguage.values().filter { it != LoveLanguage.UNKNOWN__ }.toTypedArray()
            )
        }

        BottomSheet(
            showSheet = personalityModal.show,
            modalSheet = personalityModal.modalSheet,
            modifier = Modifier
                .zIndex((personalityModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            OptionPickerModal(
                onCloseClicked = {
                    personalityModal = personalityModal.copy(show = false)
                    isModalShowing(false)
                    refreshUiUser(state.uiUser, vm, aboutMe = state.cachedUser?.profile?.aboutMe)
                },
                onSaveClicked = {
                    personalityModal = personalityModal.copy(show = false)
                    isModalShowing(false)

                    val aboutMe =
                        state.uiUser?.profile?.aboutMe?.copy(personality = state.uiUser?.profile?.aboutMe?.personality)
                            ?: emptyAboutMe(withPersonality = state.uiUser?.profile?.aboutMe?.personality)

                    vm.trySend(SaveAboutMe(aboutMe))
                },
                onItemClicked = { refreshUiUser(state.uiUser, vm, personality = it) },
                headerText = getString(User.Personality.Name),
                subtitle = getString(User.Personality.ModalTitle),
                stringMapper = Personality::stringKey,
                stateField = state.uiUser?.profile?.aboutMe?.personality,
                enumValues = Personality.values().filter { it != Personality.UNKNOWN__ }.toTypedArray()
            )
        }

        BottomSheet(
            showSheet = petsModal.show,
            modalSheet = petsModal.modalSheet,
            modifier = Modifier
                .zIndex((petsModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            OptionPickerModal(
                onCloseClicked = {
                    petsModal = petsModal.copy(show = false)
                    isModalShowing(false)
                    refreshUiUser(state.uiUser, vm, aboutMe = state.cachedUser?.profile?.aboutMe)
                },
                onSaveClicked = {
                    petsModal = petsModal.copy(show = false)
                    isModalShowing(false)

                    val aboutMe = state.uiUser?.profile?.aboutMe?.copy(pets = state.uiUser?.profile?.aboutMe?.pets)
                        ?: emptyAboutMe(withPets = state.uiUser?.profile?.aboutMe?.pets)

                    vm.trySend(SaveAboutMe(aboutMe))
                },
                onItemClicked = { refreshUiUser(state.uiUser, vm, pets = it) },
                headerText = getString(User.Pets.Name),
                subtitle = getString(User.Pets.ModalTitle),
                stringMapper = Pet::stringKey,
                stateField = state.uiUser?.profile?.aboutMe?.pets,
                enumValues = Pet.values().filter { it != Pet.UNKNOWN__ }.toTypedArray()
            )
        }

        BottomSheet(
            showSheet = politicsModal.show,
            modalSheet = politicsModal.modalSheet,
            modifier = Modifier
                .zIndex((politicsModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            OptionPickerModal(
                onCloseClicked = {
                    politicsModal = politicsModal.copy(show = false)
                    isModalShowing(false)
                    refreshUiUser(state.uiUser, vm, aboutMe = state.cachedUser?.profile?.aboutMe)
                },
                onSaveClicked = {
                    politicsModal = politicsModal.copy(show = false)
                    isModalShowing(false)

                    val aboutMe =
                        state.uiUser?.profile?.aboutMe?.copy(politics = state.uiUser?.profile?.aboutMe?.politics)
                            ?: emptyAboutMe(withPolitics = state.uiUser?.profile?.aboutMe?.politics)

                    vm.trySend(SaveAboutMe(aboutMe))
                },
                onItemClicked = { refreshUiUser(state.uiUser, vm, politics = it) },
                headerText = getString(User.Politics.Name),
                subtitle = getString(User.Politics.ModalTitle),
                stringMapper = Politics::stringKey,
                stateField = state.uiUser?.profile?.aboutMe?.politics,
                enumValues = Politics.values().filter { it != Politics.UNKNOWN__ }.toTypedArray()
            )
        }

        BottomSheet(
            showSheet = relationshipModal.show,
            modalSheet = relationshipModal.modalSheet,
            modifier = Modifier
                .zIndex((relationshipModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            OptionPickerModal(
                onCloseClicked = {
                    relationshipModal = relationshipModal.copy(show = false)
                    isModalShowing(false)
                    refreshUiUser(state.uiUser, vm, aboutMe = state.cachedUser?.profile?.aboutMe)
                },
                onSaveClicked = {
                    relationshipModal = relationshipModal.copy(show = false)
                    isModalShowing(false)

                    val aboutMe =
                        state.uiUser?.profile?.aboutMe?.copy(relationship = state.uiUser?.profile?.aboutMe?.relationship)
                            ?: emptyAboutMe(withRelationship = state.uiUser?.profile?.aboutMe?.relationship)

                    vm.trySend(SaveAboutMe(aboutMe))
                },
                onItemClicked = { refreshUiUser(state.uiUser, vm, relationship = it) },
                headerText = getString(User.Relationship.Name),
                subtitle = getString(User.Relationship.ModalTitle),
                stringMapper = Relationship::stringKey,
                stateField = state.uiUser?.profile?.aboutMe?.relationship,
                enumValues = Relationship.values().filter { it != Relationship.UNKNOWN__ }.toTypedArray()
            )
        }

        BottomSheet(
            showSheet = smokingModal.show,
            modalSheet = smokingModal.modalSheet,
            modifier = Modifier
                .zIndex((smokingModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            OptionPickerModal(
                onCloseClicked = {
                    smokingModal = smokingModal.copy(show = false)
                    isModalShowing(false)
                    refreshUiUser(state.uiUser, vm, aboutMe = state.cachedUser?.profile?.aboutMe)
                },
                onSaveClicked = {
                    smokingModal = smokingModal.copy(show = false)
                    isModalShowing(false)

                    val aboutMe =
                        state.uiUser?.profile?.aboutMe?.copy(smoking = state.uiUser?.profile?.aboutMe?.smoking)
                            ?: emptyAboutMe(withSmoking = state.uiUser?.profile?.aboutMe?.smoking)

                    vm.trySend(SaveAboutMe(aboutMe))
                },
                onItemClicked = { refreshUiUser(state.uiUser, vm, smoking = it) },
                headerText = getString(User.Smoking.Name),
                subtitle = getString(User.Smoking.ModalTitle),
                stringMapper = Smoking::stringKey,
                stateField = state.uiUser?.profile?.aboutMe?.smoking,
                enumValues = Smoking.values().filter { it != Smoking.UNKNOWN__ }.toTypedArray()
            )
        }

        BottomSheet(
            showSheet = zodiacModal.show,
            modalSheet = zodiacModal.modalSheet,
            modifier = Modifier
                .zIndex((zodiacModal.id).toFloat())
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            OptionPickerModal(
                onCloseClicked = {
                    zodiacModal = zodiacModal.copy(show = false)
                    isModalShowing(false)
                    refreshUiUser(state.uiUser, vm, aboutMe = state.cachedUser?.profile?.aboutMe)
                },
                onSaveClicked = {
                    zodiacModal = zodiacModal.copy(show = false)
                    isModalShowing(false)

                    val aboutMe = state.uiUser?.profile?.aboutMe?.copy(zodiac = state.uiUser?.profile?.aboutMe?.zodiac)
                        ?: emptyAboutMe(withZodiac = state.uiUser?.profile?.aboutMe?.zodiac)

                    vm.trySend(SaveAboutMe(aboutMe))
                },
                onItemClicked = { refreshUiUser(state.uiUser, vm, zodiac = it) },
                headerText = getString(User.Zodiac.Name),
                subtitle = getString(User.Zodiac.ModalTitle),
                stringMapper = Zodiac::stringKey,
                stateField = state.uiUser?.profile?.aboutMe?.zodiac,
                enumValues = Zodiac.values().filter { it != Zodiac.UNKNOWN__ }.toTypedArray()
            )
        }

        state.photoGridTap?.let { gridTap ->
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black.copy(alpha = 0.5f),
            ) {
                val cancelOption = BottomOption(
                    title = getString(UpdateProfile.Cancel),
                    onClick = {
                        vm.trySend(Inputs.TappedCancelPhoto)
                    }
                )

                if (gridTap.isGridSlotEmpty) {
                    BottomOptionsMenu(
                        options = listOf(
                            BottomOption(
                                title = getString(UpdateProfile.AddPhotoFromCamera),
                                onClick = {
                                    vm.trySend(TappedAddPhoto(gridTap, CAMERA))
                                }
                            ),
                            BottomOption(
                                title = getString(UpdateProfile.AddPhotoFromGallery),
                                onClick = {
                                    vm.trySend(TappedAddPhoto(gridTap, GALLERY))
                                }
                            ),
                            cancelOption,
                        ),
                        modifier = Modifier,
                    )
                } else {
                    BottomOptionsMenu(
                        options = listOf(
                            BottomOption(
                                title = getString(UpdateProfile.RemovePhoto),
                                onClick = {
                                    vm.trySend(TappedRemovePhoto(gridTap))
                                }
                            ),
                            cancelOption,
                        ),
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}

private fun refreshUiUser(
    usr: GetUser?,
    vm: UpdateProfileViewModel,
    aboutMe: AboutMe? = null,
    bio: String? = null,
    height: Int? = null,
    religion: Religion? = null,
    education: Education? = null,
    loveLanguage: LoveLanguage? = null,
    personality: Personality? = null,
    pets: Pet? = null,
    politics: Politics? = null,
    relationship: Relationship? = null,
    smoking: Smoking? = null,
    zodiac: Zodiac? = null,
    openingQuestion: OpenQuestion? = null,
    gender: Gender? = null,
    interests: Interests? = null,
) {
    usr?.let { user ->
        aboutMe?.let {
            val profile = user.profile.copy(aboutMe = it)
            vm.trySend(SetUiUser(user.copy(profile = profile)))
        }

        bio?.let {
            vm.trySend(SetUiUser(user.copy(profile = user.profile.copy(bio = it))))
        }

        height?.let {
            val abutMe = user.profile.aboutMe.copy(height = it)
            val profile = user.profile.copy(aboutMe = abutMe)
            vm.trySend(SetUiUser(user.copy(profile = profile)))
        }

        gender?.let {
            vm.trySend(SetUiUser(user.copy(details = user.details.copy(gender = it))))
        }

        religion?.let {
            val abutMe = user.profile.aboutMe.copy(religion = it)
            val profile = user.profile.copy(aboutMe = abutMe)
            vm.trySend(SetUiUser(user.copy(profile = profile)))
        }

        education?.let {
            val abutMe = user.profile.aboutMe.copy(education = it)
            val profile = user.profile.copy(aboutMe = abutMe)
            vm.trySend(SetUiUser(user.copy(profile = profile)))
        }

        loveLanguage?.let {
            val abutMe = user.profile.aboutMe.copy(loveLanguage = it)
            val profile = user.profile.copy(aboutMe = abutMe)
            vm.trySend(SetUiUser(user.copy(profile = profile)))
        }

        smoking?.let {
            val abutMe = user.profile.aboutMe.copy(smoking = it)
            val profile = user.profile.copy(aboutMe = abutMe)
            vm.trySend(SetUiUser(user.copy(profile = profile)))
        }

        zodiac?.let {
            val abutMe = user.profile.aboutMe.copy(zodiac = it)
            val profile = user.profile.copy(aboutMe = abutMe)
            vm.trySend(SetUiUser(user.copy(profile = profile)))
        }

        relationship?.let {
            val abutMe = user.profile.aboutMe.copy(relationship = it)
            val profile = user.profile.copy(aboutMe = abutMe)
            vm.trySend(SetUiUser(user.copy(profile = profile)))
        }

        pets?.let {
            val abutMe = user.profile.aboutMe.copy(pets = it)
            val profile = user.profile.copy(aboutMe = abutMe)
            vm.trySend(SetUiUser(user.copy(profile = profile)))
        }

        politics?.let {
            val abutMe = user.profile.aboutMe.copy(politics = it)
            val profile = user.profile.copy(aboutMe = abutMe)
            vm.trySend(SetUiUser(user.copy(profile = profile)))
        }

        personality?.let {
            val abutMe = user.profile.aboutMe.copy(personality = it)
            val profile = user.profile.copy(aboutMe = abutMe)
            vm.trySend(SetUiUser(user.copy(profile = profile)))
        }

        interests?.let {
            val profile = user.profile.copy(interests = interests)
            vm.trySend(SetUiUser(user.copy(profile = profile)))
        }

        openingQuestion?.let {
            val profile = user.profile.copy(openQuestion = openingQuestion)
            vm.trySend(SetUiUser(user.copy(profile = profile)))
        }
    }
}

private fun modalShowMutableState(modalSheet: ModalSheet) = mutableStateOf(
    ModalShow(
        id = 0,
        modalSheet = modalSheet,
        show = false,
    )
)

private fun emptyAboutMe(
    withLanguages: List<Language> = emptyList(),
    withChildren: Children? = null,
    withDiet: Diet? = null,
    withDrinking: Drinking? = null,
    withEducation: Education? = null,
    withFitness: Fitness? = null,
    withHeight: Int? = null,
    withLoveLanguage: LoveLanguage? = null,
    withPersonality: Personality? = null,
    withPets: Pet? = null,
    withPolitics: Politics? = null,
    withRelationship: Relationship? = null,
    withReligion: Religion? = null,
    withSmoking: Smoking? = null,
    withZodiac: Zodiac? = null,
) = AboutMe(
    languages = withLanguages,
    children = withChildren,
    diet = withDiet,
    drinking = withDrinking,
    education = withEducation,
    fitness = withFitness,
    height = withHeight,
    loveLanguage = withLoveLanguage,
    personality = withPersonality,
    pets = withPets,
    politics = withPolitics,
    relationship = withRelationship,
    religion = withReligion,
    smoking = withSmoking,
    zodiac = withZodiac
)

@Composable
fun ProfileStrength(state: State) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(getString(ProfileStrengthContent, state.cachedUser?.profileCompletion ?: 0))
        LinearProgressIndicator(progress = state.cachedUser?.profileCompletion?.toFloat() ?: 0f)
    }
}

@Composable
fun ContentCard(modifier: Modifier, content: @Composable () -> Unit) {
    Surface(
        color = MaterialTheme.colors.background,
        shape = RoundedCornerShape(8.dp), border = BorderStroke(1.dp, Color.LightGray),
        modifier = modifier.fillMaxWidth()

    ) {
        Box(
            modifier = Modifier.padding(16.dp),
        ) {
            content()
        }
    }
}

@Composable
fun ChipsCard(interests: Interests?, modifier: Modifier = Modifier, onClick: () -> Unit) {
    ContentCard(modifier = modifier.fillMaxWidth().clickable { onClick() }) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (interests == null || interests.isEmpty()) {
                Text(getString(NoInterests))
            } else {
                interests.sports?.forEach {
                    Chip(text = it.name, isSelected = false, icon = it.icon())
                }
                interests.creativities?.forEach {
                    Chip(text = it.name, isSelected = false, icon = it.icon())
                }
                interests.culinaries?.forEach {
                    Chip(text = it.name, isSelected = false, icon = it.icon())
                }
                interests.leisures?.forEach {
                    Chip(text = it.name, isSelected = false, icon = it.icon())
                }
                interests.socials?.forEach {
                    Chip(text = it.name, isSelected = false, icon = it.icon())
                }
                interests.technologies?.forEach {
                    Chip(text = it.name, isSelected = false, icon = it.icon())
                }
                interests.natures?.forEach {
                    Chip(text = it.name, isSelected = false, icon = it.icon())
                }
                interests.minds?.forEach {
                    Chip(text = it.name, isSelected = false, icon = it.icon())
                }
            }
        }
    }
}

@Composable
internal fun SaveActionButtons(onCancelClicked: () -> Unit, onSaveClicked: () -> Unit, modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colors.background) {
        Column(modifier = modifier.fillMaxWidth()) {
            Divider(Modifier.fillMaxWidth())
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                    ),
                    onClick = { onCancelClicked() },
                ) {
                    Text(getString(UpdateProfile.Cancel))
                }
                Button(
                    onClick = { onSaveClicked() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                ) {
                    Text(getString(UpdateProfile.Save))
                }
            }
        }
    }
}
