package com.ajlabs.forevely.feature.updateProfile.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.GolfCourse
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Hiking
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material.icons.filled.Kayaking
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.ModelTraining
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.material.icons.filled.Museum
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.OutdoorGrill
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Podcasts
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Rowing
import androidx.compose.material.icons.filled.Sailing
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Skateboarding
import androidx.compose.material.icons.filled.SocialDistance
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.SportsBar
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.SportsFootball
import androidx.compose.material.icons.filled.SportsHandball
import androidx.compose.material.icons.filled.SportsMartialArts
import androidx.compose.material.icons.filled.SportsMma
import androidx.compose.material.icons.filled.SportsTennis
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Surfing
import androidx.compose.material.icons.filled.Theaters
import androidx.compose.material.icons.filled.Toys
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Upgrade
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.Web
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.data.GetUserQuery.Interests
import com.ajlabs.forevely.data.type.Creative
import com.ajlabs.forevely.data.type.Creative.DANCING
import com.ajlabs.forevely.data.type.Creative.DIY_CRAFTS
import com.ajlabs.forevely.data.type.Creative.DRAWING
import com.ajlabs.forevely.data.type.Creative.FILMMAKING
import com.ajlabs.forevely.data.type.Creative.KNITTING
import com.ajlabs.forevely.data.type.Creative.PAINTING
import com.ajlabs.forevely.data.type.Creative.PHOTOGRAPHY
import com.ajlabs.forevely.data.type.Creative.PLAYING_AN_INSTRUMENT
import com.ajlabs.forevely.data.type.Creative.POTTERY
import com.ajlabs.forevely.data.type.Creative.SCULPTING
import com.ajlabs.forevely.data.type.Creative.SEWING
import com.ajlabs.forevely.data.type.Creative.SINGING
import com.ajlabs.forevely.data.type.Creative.STAND_UP_COMEDY
import com.ajlabs.forevely.data.type.Creative.THEATER
import com.ajlabs.forevely.data.type.Creative.WRITING
import com.ajlabs.forevely.data.type.Culinary
import com.ajlabs.forevely.data.type.Culinary.BAKING
import com.ajlabs.forevely.data.type.Culinary.BARBECUING
import com.ajlabs.forevely.data.type.Culinary.CHOCOLATE_MAKING
import com.ajlabs.forevely.data.type.Culinary.COOKING
import com.ajlabs.forevely.data.type.Culinary.CRAFT_BEER
import com.ajlabs.forevely.data.type.Culinary.FOODIE_TOURS
import com.ajlabs.forevely.data.type.Culinary.MIXOLOGY
import com.ajlabs.forevely.data.type.Culinary.VEGAN_CUISINE
import com.ajlabs.forevely.data.type.Culinary.VEGETARIAN_CUISINE
import com.ajlabs.forevely.data.type.Culinary.WINE_TASTING
import com.ajlabs.forevely.data.type.Leisure
import com.ajlabs.forevely.data.type.Leisure.ANTIQUING
import com.ajlabs.forevely.data.type.Leisure.ASTRONOMY
import com.ajlabs.forevely.data.type.Leisure.BIRD_WATCHING
import com.ajlabs.forevely.data.type.Leisure.BOARD_GAMES
import com.ajlabs.forevely.data.type.Leisure.BOATING
import com.ajlabs.forevely.data.type.Leisure.COIN_COLLECTING
import com.ajlabs.forevely.data.type.Leisure.FISHING
import com.ajlabs.forevely.data.type.Leisure.PUZZLES
import com.ajlabs.forevely.data.type.Leisure.READING
import com.ajlabs.forevely.data.type.Leisure.SHOPPING
import com.ajlabs.forevely.data.type.Leisure.TRAVELING
import com.ajlabs.forevely.data.type.Leisure.VIDEO_GAMING
import com.ajlabs.forevely.data.type.Leisure.WATCHING_MOVIES
import com.ajlabs.forevely.data.type.Mind
import com.ajlabs.forevely.data.type.Mind.ASTROLOGY
import com.ajlabs.forevely.data.type.Mind.MEDITATION
import com.ajlabs.forevely.data.type.Mind.MINDFULNESS
import com.ajlabs.forevely.data.type.Mind.PHILOSOPHICAL_DEBATES
import com.ajlabs.forevely.data.type.Mind.PSYCHOLOGY
import com.ajlabs.forevely.data.type.Mind.SELF_IMPROVEMENT
import com.ajlabs.forevely.data.type.Mind.TAROT
import com.ajlabs.forevely.data.type.Nature
import com.ajlabs.forevely.data.type.Nature.CAMPING
import com.ajlabs.forevely.data.type.Nature.ENVIRONMENTAL_ACTIVISM
import com.ajlabs.forevely.data.type.Nature.GARDENING
import com.ajlabs.forevely.data.type.Nature.GEOCACHING
import com.ajlabs.forevely.data.type.Nature.HORSEBACK_RIDING
import com.ajlabs.forevely.data.type.Nature.KAYAKING
import com.ajlabs.forevely.data.type.Nature.NATURE_PHOTOGRAPHY
import com.ajlabs.forevely.data.type.Nature.SAILING
import com.ajlabs.forevely.data.type.Nature.SCUBA_DIVING
import com.ajlabs.forevely.data.type.Nature.SUSTAINABLE_LIVING
import com.ajlabs.forevely.data.type.Nature.WILDLIFE_CONSERVATION
import com.ajlabs.forevely.data.type.Social
import com.ajlabs.forevely.data.type.Social.ANIMAL_RESCUE
import com.ajlabs.forevely.data.type.Social.BOOK_CLUB
import com.ajlabs.forevely.data.type.Social.CONCERTS
import com.ajlabs.forevely.data.type.Social.LANGUAGE_LEARNING
import com.ajlabs.forevely.data.type.Social.MUSEUM_VISITING
import com.ajlabs.forevely.data.type.Social.OPERA
import com.ajlabs.forevely.data.type.Social.POLITICAL_CAMPAIGNING
import com.ajlabs.forevely.data.type.Social.SOCIAL_ACTIVISM
import com.ajlabs.forevely.data.type.Social.THEATER_GOING
import com.ajlabs.forevely.data.type.Social.VOLUNTEERING
import com.ajlabs.forevely.data.type.Sports
import com.ajlabs.forevely.data.type.Sports.BASKETBALL
import com.ajlabs.forevely.data.type.Sports.BOXING
import com.ajlabs.forevely.data.type.Sports.CYCLING
import com.ajlabs.forevely.data.type.Sports.FOOTBALL
import com.ajlabs.forevely.data.type.Sports.GOLF
import com.ajlabs.forevely.data.type.Sports.GYM_WORKOUTS
import com.ajlabs.forevely.data.type.Sports.HIKING
import com.ajlabs.forevely.data.type.Sports.MARTIAL_ARTS
import com.ajlabs.forevely.data.type.Sports.PILATES
import com.ajlabs.forevely.data.type.Sports.ROCK_CLIMBING
import com.ajlabs.forevely.data.type.Sports.ROWING
import com.ajlabs.forevely.data.type.Sports.RUNNING
import com.ajlabs.forevely.data.type.Sports.SKATING
import com.ajlabs.forevely.data.type.Sports.SKIING
import com.ajlabs.forevely.data.type.Sports.SNOWBOARDING
import com.ajlabs.forevely.data.type.Sports.SURFING
import com.ajlabs.forevely.data.type.Sports.SWIMMING
import com.ajlabs.forevely.data.type.Sports.TENNIS
import com.ajlabs.forevely.data.type.Sports.UNKNOWN__
import com.ajlabs.forevely.data.type.Sports.YOGA
import com.ajlabs.forevely.data.type.Technology
import com.ajlabs.forevely.data.type.Technology.BLOGGING
import com.ajlabs.forevely.data.type.Technology.CODING
import com.ajlabs.forevely.data.type.Technology.CYBERSECURITY
import com.ajlabs.forevely.data.type.Technology.DRONE_PILOTING
import com.ajlabs.forevely.data.type.Technology.GADGETEERING
import com.ajlabs.forevely.data.type.Technology.PODCASTING
import com.ajlabs.forevely.data.type.Technology.ROBOTICS
import com.ajlabs.forevely.data.type.Technology.THREEDEE_PRINTING
import com.ajlabs.forevely.data.type.Technology.WEB_DESIGN
import com.ajlabs.forevely.feature.updateProfile.ChipsCard
import com.ajlabs.forevely.localization.Strings.UpdateProfile.MyInterestsSectionSubtitle
import com.ajlabs.forevely.localization.Strings.UpdateProfile.MyInterestsSectionTitle
import com.ajlabs.forevely.localization.getString

@Composable
fun MyInterestsSection(interests: Interests?, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(getString(MyInterestsSectionTitle), style = MaterialTheme.typography.h6)
        Spacer(Modifier.padding(4.dp))
        Text(getString(MyInterestsSectionSubtitle))
        Spacer(Modifier.padding(8.dp))
        ChipsCard(
            interests = interests,
            onClick = onClick,
        )
    }
}


internal fun Interests.isEmpty(): Boolean {
    return listOfNotNull(sports, culinaries, creativities, leisures, socials, technologies, natures, minds)
        .all { it.isEmpty() }
}

internal fun Sports.icon(): ImageVector = when (this) {
    YOGA -> Icons.Default.Spa
    BOXING -> Icons.Default.SportsMma
    RUNNING -> Icons.Default.DirectionsRun
    GYM_WORKOUTS -> Icons.Default.FitnessCenter
    CYCLING -> Icons.Default.DirectionsBike
    HIKING -> Icons.Default.Hiking
    SWIMMING -> Icons.Default.Pool
    MARTIAL_ARTS -> Icons.Default.SportsMartialArts
    BASKETBALL -> Icons.Default.SportsBasketball
    FOOTBALL -> Icons.Default.SportsFootball
    TENNIS -> Icons.Default.SportsTennis
    GOLF -> Icons.Default.GolfCourse
    SKIING -> Icons.Default.AcUnit
    SNOWBOARDING -> Icons.Default.AcUnit
    SURFING -> Icons.Default.Surfing
    ROCK_CLIMBING -> Icons.Default.SportsHandball
    ROWING -> Icons.Default.Rowing
    PILATES -> Icons.Default.FitnessCenter
    SKATING -> Icons.Default.Skateboarding
    UNKNOWN__ -> Icons.Default.HelpOutline
}

internal fun Culinary.icon(): ImageVector = when (this) {
    COOKING -> Icons.Default.Kitchen
    BAKING -> Icons.Default.BakeryDining
    WINE_TASTING -> Icons.Default.WineBar
    CRAFT_BEER -> Icons.Default.SportsBar
    FOODIE_TOURS -> Icons.Default.Fastfood
    BARBECUING -> Icons.Default.OutdoorGrill
    VEGAN_CUISINE -> Icons.Default.Grass
    VEGETARIAN_CUISINE -> Icons.Default.Grass
    MIXOLOGY -> Icons.Default.LocalBar
    CHOCOLATE_MAKING -> Icons.Default.Cake
    Culinary.UNKNOWN__ -> Icons.Default.HelpOutline
}

internal fun Creative.icon(): ImageVector = when (this) {
    PAINTING -> Icons.Default.Brush
    DRAWING -> Icons.Default.Draw
    SCULPTING -> Icons.Default.ModelTraining
    PHOTOGRAPHY -> Icons.Default.CameraAlt
    THEATER -> Icons.Default.Theaters
    SINGING -> Icons.Default.MusicNote
    PLAYING_AN_INSTRUMENT -> Icons.Default.MusicNote
    WRITING -> Icons.Default.Create
    POTTERY -> Icons.Default.Palette
    KNITTING -> Icons.Default.Home
    SEWING -> Icons.Default.Home
    DIY_CRAFTS -> Icons.Default.Construction
    FILMMAKING -> Icons.Default.MovieCreation
    DANCING -> Icons.Default.SocialDistance
    STAND_UP_COMEDY -> Icons.Default.Mic
    Creative.UNKNOWN__ -> Icons.Default.HelpOutline
}

internal fun Leisure.icon(): ImageVector = when (this) {
    SHOPPING -> Icons.Default.ShoppingCart
    TRAVELING -> Icons.Default.Flight
    FISHING -> Icons.Default.Sports
    BOATING -> Icons.Default.DirectionsBoat
    BIRD_WATCHING -> Icons.Default.Park
    BOARD_GAMES -> Icons.Default.Casino
    VIDEO_GAMING -> Icons.Default.SportsEsports
    WATCHING_MOVIES -> Icons.Default.Movie
    READING -> Icons.Default.Book
    PUZZLES -> Icons.Default.Extension
    COIN_COLLECTING -> Icons.Default.CollectionsBookmark
    ANTIQUING -> Icons.Default.Search
    ASTRONOMY -> Icons.Default.Star
    Leisure.UNKNOWN__ -> Icons.Default.HelpOutline
}

internal fun Social.icon(): ImageVector = when (this) {
    CONCERTS -> Icons.Default.MusicNote
    MUSEUM_VISITING -> Icons.Default.Museum
    THEATER_GOING -> Icons.Default.Theaters
    OPERA -> Icons.Default.MusicNote
    BOOK_CLUB -> Icons.Default.Book
    LANGUAGE_LEARNING -> Icons.Default.Translate
    SOCIAL_ACTIVISM -> Icons.Default.People
    VOLUNTEERING -> Icons.Default.VolunteerActivism
    POLITICAL_CAMPAIGNING -> Icons.Default.HowToVote
    ANIMAL_RESCUE -> Icons.Default.Pets
    Social.UNKNOWN__ -> Icons.Default.HelpOutline
}

internal fun Technology.icon(): ImageVector = when (this) {
    CODING -> Icons.Default.Code
    ROBOTICS -> Icons.Default.Build
    WEB_DESIGN -> Icons.Default.Web
    BLOGGING -> Icons.Default.Edit
    PODCASTING -> Icons.Default.Podcasts
    DRONE_PILOTING -> Icons.Default.Toys
    THREEDEE_PRINTING -> Icons.Default.Print
    GADGETEERING -> Icons.Default.Build
    CYBERSECURITY -> Icons.Default.Security
    Technology.UNKNOWN__ -> Icons.Default.HelpOutline
}

fun Nature.icon(): ImageVector = when (this) {
    CAMPING -> Icons.Default.Campaign
    WILDLIFE_CONSERVATION -> Icons.Default.Park
    GARDENING -> Icons.Default.LocalFlorist
    ENVIRONMENTAL_ACTIVISM -> Icons.Default.Eco
    SUSTAINABLE_LIVING -> Icons.Default.Eco
    HORSEBACK_RIDING -> Icons.Default.Agriculture
    NATURE_PHOTOGRAPHY -> Icons.Default.Landscape
    SCUBA_DIVING -> Icons.Default.Pool
    KAYAKING -> Icons.Default.Kayaking
    SAILING -> Icons.Default.Sailing
    GEOCACHING -> Icons.Default.MyLocation
    Nature.UNKNOWN__ -> Icons.Default.HelpOutline
}

fun Mind.icon(): ImageVector = when (this) {
    MEDITATION -> Icons.Default.Spa
    TAROT -> Icons.Default.Mood
    ASTROLOGY -> Icons.Default.Stars
    PHILOSOPHICAL_DEBATES -> Icons.Default.Balance
    PSYCHOLOGY -> Icons.Default.Psychology
    MINDFULNESS -> Icons.Default.Spa
    SELF_IMPROVEMENT -> Icons.Default.Upgrade
    Mind.UNKNOWN__ -> Icons.Default.HelpOutline
}
