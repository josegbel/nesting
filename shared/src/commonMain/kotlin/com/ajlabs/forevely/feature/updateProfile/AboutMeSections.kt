package com.ajlabs.forevely.feature.updateProfile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SmokingRooms
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.data.type.Children
import com.ajlabs.forevely.data.type.Diet
import com.ajlabs.forevely.data.type.Drinking
import com.ajlabs.forevely.data.type.Education
import com.ajlabs.forevely.data.type.Fitness
import com.ajlabs.forevely.data.type.Gender
import com.ajlabs.forevely.data.type.LoveLanguage
import com.ajlabs.forevely.data.type.Personality
import com.ajlabs.forevely.data.type.Pet
import com.ajlabs.forevely.data.type.Politics
import com.ajlabs.forevely.data.type.Relationship
import com.ajlabs.forevely.data.type.Religion
import com.ajlabs.forevely.data.type.Smoking
import com.ajlabs.forevely.data.type.Zodiac
import com.ajlabs.forevely.feature.updateProfile.sheets.stringKey
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.Strings.User
import com.ajlabs.forevely.localization.Strings.User.Children.Name
import com.ajlabs.forevely.localization.Strings.User.Drink
import com.ajlabs.forevely.localization.Strings.User.Height
import com.ajlabs.forevely.localization.Strings.User.Pets
import com.ajlabs.forevely.localization.getString
import com.ajlabs.forevely.localization.toName

@Composable
fun AboutMeSubsection(icon: ImageVector, title: String, value: String, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Text(text = title, modifier = Modifier.weight(1f))
        Text(text = value)
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
    }
}

@Composable
fun HeightSubsection(value: Int?, onClick: () -> Unit) {
    AboutMeSubsection(
        icon = Icons.Default.Height,
        title = getString(Height.Name),
        value = value?.let {"${value}cm" } ?: getString(Strings.Add),
        onClick = onClick
    )
}

@Composable
fun GenderSubsection(value: Gender?, onClick: () -> Unit) {
    AboutMeSubsection(
        icon = Icons.Default.Person,
        title = getString(User.Gender.Name),
        value = getString(value?.stringKey() ?: Strings.Add),
        onClick = onClick
    )
}

@Composable
fun ChildrenSubsection(value: Children?, onClick: () -> Unit) {
    AboutMeSubsection(
        icon = Icons.Default.FamilyRestroom,
        title = getString(Name),
        value = getString(value?.toName() ?: Strings.Add),
        onClick = onClick
    )
}

@Composable
fun DietSubsection(value: Diet?, onClick: () -> Unit) {
    AboutMeSubsection(
        icon = Icons.Default.Restaurant,
        title = getString(User.Diet.Name),
        value = getString(value?.toName() ?: Strings.Add),
        onClick = onClick
    )
}

@Composable
fun DrinkingSubsection(value: Drinking?, onClick: () -> Unit) {
    AboutMeSubsection(
        icon = Icons.Default.LocalBar,
        title = getString(Drink.Name),
        value = getString(value?.toName() ?: Strings.Add),
        onClick = onClick
    )
}

@Composable
fun EducationSubsection(value: Education?, onClick: () -> Unit) {
    AboutMeSubsection(
        icon = Icons.Default.School,
        title = getString(User.Education.Name),
        value = getString(value?.toName() ?: Strings.Add),
        onClick = onClick
    )
}

@Composable
fun FitnessSubsection(value: Fitness?, onClick: () -> Unit) {
    AboutMeSubsection(
        icon = Icons.Default.FitnessCenter,
        title = getString(User.Fitness.Name),
        value = getString(value?.toName() ?: Strings.Add),
        onClick = onClick
    )
}

@Composable
fun LoveLanguageSubsection(value: LoveLanguage?, onClick: () -> Unit) {
    AboutMeSubsection(
        icon = Icons.Default.Favorite,
        title = getString(User.LoveLanguage.Name),
        value = getString(value?.toName() ?: Strings.Add),
        onClick = onClick
    )
}

@Composable
fun PersonalitySubsection(value: Personality?, onClick: () -> Unit) {
    AboutMeSubsection(
        icon = Icons.Default.Psychology,
        title = getString(User.Personality.Name),
        value = getString(value?.toName() ?: Strings.Add),
        onClick = onClick
    )
}

@Composable
fun PetsSubsection(value: Pet?, onClick: () -> Unit) {
    AboutMeSubsection(
        icon = Icons.Default.Pets,
        title = getString(Pets.Name),
        value = getString(value?.toName() ?: Strings.Add),
        onClick = onClick
    )
}

@Composable
fun PoliticsSubsection(value: Politics?, onClick: () -> Unit) {
    AboutMeSubsection(
        icon = Icons.Default.HowToVote,
        title = getString(User.Politics.Name),
        value = getString(value?.toName() ?: Strings.Add),
        onClick = onClick
    )
}

@Composable
fun RelationshipSubsection(value: Relationship?, onClick: () -> Unit) {
    AboutMeSubsection(
        icon = Icons.Default.FavoriteBorder,
        title = getString(User.Relationship.Name),
        value = getString(value?.toName() ?: Strings.Add),
        onClick = onClick
    )
}

@Composable
fun ReligionSubsection(value: Religion?, onClick: () -> Unit) {
    AboutMeSubsection(
        icon = Icons.Default.Place,
        title = getString(User.Religion.Name),
        value = getString(value?.toName() ?: Strings.Add),
        onClick = onClick
    )
}

@Composable
fun SmokingSubsection(value: Smoking?, onClick: () -> Unit) {
    AboutMeSubsection(
        icon = Icons.Default.SmokingRooms,
        title = getString(User.Smoking.Name),
        value = getString(value?.toName() ?: Strings.Add),
        onClick = onClick
    )
}

@Composable
fun ZodiacSubsection(value: Zodiac?, onClick: () -> Unit) {
    AboutMeSubsection(
        icon = Icons.Default.Star,
        title = getString(User.Zodiac.Name),
        value = getString(value?.toName() ?: Strings.Add),
        onClick = onClick
    )
}

