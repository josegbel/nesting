package com.ajlabs.forevely.localization

import com.ajlabs.forevely.data.type.Children
import com.ajlabs.forevely.data.type.Culinary
import com.ajlabs.forevely.data.type.Culinary.BAKING
import com.ajlabs.forevely.data.type.Culinary.BARBECUING
import com.ajlabs.forevely.data.type.Diet
import com.ajlabs.forevely.data.type.Drinking
import com.ajlabs.forevely.data.type.Education
import com.ajlabs.forevely.data.type.Fitness
import com.ajlabs.forevely.data.type.Culinary.CHOCOLATE_MAKING
import com.ajlabs.forevely.data.type.Culinary.COOKING
import com.ajlabs.forevely.data.type.Culinary.CRAFT_BEER
import com.ajlabs.forevely.data.type.Culinary.FOODIE_TOURS
import com.ajlabs.forevely.data.type.Culinary.MIXOLOGY
import com.ajlabs.forevely.data.type.Culinary.VEGAN_CUISINE
import com.ajlabs.forevely.data.type.Culinary.VEGETARIAN_CUISINE
import com.ajlabs.forevely.data.type.Culinary.WINE_TASTING
import com.ajlabs.forevely.data.type.Gender
import com.ajlabs.forevely.data.type.LoveLanguage
import com.ajlabs.forevely.data.type.Personality
import com.ajlabs.forevely.data.type.Pet
import com.ajlabs.forevely.data.type.Politics
import com.ajlabs.forevely.data.type.Relationship
import com.ajlabs.forevely.data.type.Religion
import com.ajlabs.forevely.data.type.Smoking
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
import com.ajlabs.forevely.data.type.Zodiac
import com.ajlabs.forevely.localization.Strings.Interest.Baking
import com.ajlabs.forevely.localization.Strings.Interest.Barbecuing
import com.ajlabs.forevely.localization.Strings.Interest.ChocolateMaking
import com.ajlabs.forevely.localization.Strings.Interest.Cooking
import com.ajlabs.forevely.localization.Strings.Interest.CraftBeer
import com.ajlabs.forevely.localization.Strings.Interest.FoodieTours
import com.ajlabs.forevely.localization.Strings.Interest.Mixology
import com.ajlabs.forevely.localization.Strings.Interest.VeganCuisine
import com.ajlabs.forevely.localization.Strings.Interest.VegetarianCuisine
import com.ajlabs.forevely.localization.Strings.Interest.WineTasting

internal fun Children.toName() = when (this) {
    Children.I_HAVE_AND_WANT_MORE -> Strings.User.Children.IHaveAndWantMore
    Children.I_HAVE_AND_DONT_WANT_MORE -> Strings.User.Children.IHaveAndDontWantMore
    Children.UNKNOWN__ -> Strings.User.Children.IHaveAndWantMore
}

internal fun Diet.toName() = when (this) {
    Diet.OMNIVORE -> Strings.User.Diet.Omnivore
    Diet.VEGAN -> Strings.User.Diet.Vegan
    Diet.VEGETARIAN -> Strings.User.Diet.Vegetarian
    Diet.PESCATARIAN -> Strings.User.Diet.Pescatarian
    Diet.CARNIVORE -> Strings.User.Diet.Carnivore
    Diet.KOSHER -> Strings.User.Diet.Kosher
    Diet.HALAL -> Strings.User.Diet.Halal
    Diet.OTHER -> Strings.User.Diet.Other
    Diet.UNKNOWN__ -> Strings.User.Diet.Other
}

internal fun Drinking.toName() = when (this) {
    Drinking.RARELY -> Strings.User.Drink.Rarely
    Drinking.SOCIAL_DRINKER -> Strings.User.Drink.SocialDrinker
    Drinking.REGULAR_DRINKER -> Strings.User.Drink.RegularDrinker
    Drinking.HEAVY_DRINKER -> Strings.User.Drink.HeavyDrinker
    Drinking.THINKING_ABOUT_QUITTING -> Strings.User.Drink.ThinkingAboutQuitting
    Drinking.SOBER -> Strings.User.Drink.Sober
    Drinking.UNKNOWN__ -> Strings.User.Drink.Sober
}

internal fun Education.toName() = when (this) {
    Education.HIGH_SCHOOL -> Strings.User.Education.HighSchool
    Education.STUDYING_BACHELORS -> Strings.User.Education.StudyingBachelors
    Education.BACHELORS -> Strings.User.Education.Bachelors
    Education.STUDYING_POSTGRADUATE -> Strings.User.Education.StudyingPostGraduate
    Education.MASTERS -> Strings.User.Education.Masters
    Education.PHD -> Strings.User.Education.Phd
    Education.UNKNOWN__ -> Strings.User.Education.HighSchool
}

internal fun Fitness.toName() = when (this) {
    Fitness.EVERYDAY -> Strings.User.Fitness.Everyday
    Fitness.OFTEN -> Strings.User.Fitness.Often
    Fitness.SOMETIMES -> Strings.User.Fitness.Sometimes
    Fitness.RARELY -> Strings.User.Fitness.Rarely
    Fitness.NEVER -> Strings.User.Fitness.Never
    Fitness.UNKNOWN__ -> Strings.User.Fitness.Never
}

internal fun LoveLanguage.toName() = when (this) {
    LoveLanguage.WORDS_OF_AFFIRMATION -> Strings.User.LoveLanguage.WordsOfAffirmation
    LoveLanguage.ACTS_OF_SERVICE -> Strings.User.LoveLanguage.ActsOfService
    LoveLanguage.RECEIVING_GIFTS -> Strings.User.LoveLanguage.ReceivingGifts
    LoveLanguage.QUALITY_TIME -> Strings.User.LoveLanguage.QualityTime
    LoveLanguage.PHYSICAL_TOUCH -> Strings.User.LoveLanguage.PhysicalTouch
    LoveLanguage.UNKNOWN__ -> Strings.User.LoveLanguage.WordsOfAffirmation
}

internal fun Personality.toName() = when (this) {
    Personality.INTJ -> Strings.User.Personality.INTJ
    Personality.INTP -> Strings.User.Personality.INTP
    Personality.ENTJ -> Strings.User.Personality.ENTJ
    Personality.ENTP -> Strings.User.Personality.ENTP
    Personality.INFJ -> Strings.User.Personality.INFJ
    Personality.INFP -> Strings.User.Personality.INFP
    Personality.ENFJ -> Strings.User.Personality.ENFJ
    Personality.ENFP -> Strings.User.Personality.ENFP
    Personality.ISTJ -> Strings.User.Personality.ISTJ
    Personality.ISFJ -> Strings.User.Personality.ISFJ
    Personality.ESTJ -> Strings.User.Personality.ESTJ
    Personality.ESFJ -> Strings.User.Personality.ESFJ
    Personality.ISTP -> Strings.User.Personality.ISTP
    Personality.ISFP -> Strings.User.Personality.ISFP
    Personality.ESTP -> Strings.User.Personality.ESTP
    Personality.ESFP -> Strings.User.Personality.ESFP
    Personality.UNKNOWN__ -> Strings.User.Personality.INTJ
}

internal fun Pet.toName() = when (this) {
    Pet.DOGS -> Strings.User.Pets.Dogs
    Pet.BIRDS -> Strings.User.Pets.Birds
    Pet.CATS -> Strings.User.Pets.Cats
    Pet.REPTILES -> Strings.User.Pets.Reptiles
    Pet.FISH -> Strings.User.Pets.Fish
    Pet.OTHER -> Strings.User.Pets.Other
    Pet.UNKNOWN__ -> Strings.User.Pets.Other
}

internal fun Politics.toName() = when (this) {
    Politics.LEFT_WING -> Strings.User.Politics.LeftWing
    Politics.RIGHT_WING -> Strings.User.Politics.RightWing
    Politics.CENTER -> Strings.User.Politics.Center
    Politics.NON_POLITICAL -> Strings.User.Politics.NonPolitical
    Politics.UNKNOWN__ -> Strings.User.Politics.NonPolitical
}

internal fun Gender.toName() = when (this) {
    Gender.MALE -> Strings.User.Gender.Male
    Gender.FEMALE -> Strings.User.Gender.Female
    Gender.NON_BINARY -> Strings.User.Gender.NonBinary
    Gender.PREF_NOT_TO_SAY -> Strings.User.Gender.PreferNotToSay
    Gender.UNKNOWN__ -> Strings.User.Gender.PreferNotToSay
}

internal fun Relationship.toName() = when (this) {
    Relationship.TRADITIONAL -> Strings.User.Relationship.Traditional
    Relationship.CONTEMPORARY -> Strings.User.Relationship.Contemporary
    Relationship.I_DONT_MIND -> Strings.User.Relationship.IDontMind
    Relationship.UNKNOWN__ -> Strings.User.Relationship.IDontMind
}

internal fun Religion.toName() = when (this) {
    Religion.CHRISTIANITY -> Strings.User.Religion.Christianity
    Religion.ISLAM -> Strings.User.Religion.Islam
    Religion.HINDUISM -> Strings.User.Religion.Hinduism
    Religion.BUDDHISM -> Strings.User.Religion.Buddhism
    Religion.JUDAISM -> Strings.User.Religion.Judaism
    Religion.SIKHISM -> Strings.User.Religion.Sikhism
    Religion.OTHER -> Strings.User.Religion.Other
    Religion.ATHEISM -> Strings.User.Religion.Atheism
    Religion.UNKNOWN__ -> Strings.User.Religion.Other
}

internal fun Smoking.toName() = when (this) {
    Smoking.NON_SMOKER -> Strings.User.Smoking.NonSmoker
    Smoking.SOCIAL_SMOKER -> Strings.User.Smoking.SocialSmoker
    Smoking.SMOKER_WHEN_DRINKING -> Strings.User.Smoking.SmokerWhenDrinking
    Smoking.REGULAR_SMOKER -> Strings.User.Smoking.RegularSmoker
    Smoking.TRYING_TO_QUIT -> Strings.User.Smoking.TryingToQuit
    Smoking.UNKNOWN__ -> Strings.User.Smoking.NonSmoker
}

internal fun Zodiac.toName() = when (this) {
    Zodiac.ARIES -> Strings.User.Zodiac.Aries
    Zodiac.TAURUS -> Strings.User.Zodiac.Taurus
    Zodiac.GEMINI -> Strings.User.Zodiac.Gemini
    Zodiac.CANCER -> Strings.User.Zodiac.Cancer
    Zodiac.LEO -> Strings.User.Zodiac.Leo
    Zodiac.VIRGO -> Strings.User.Zodiac.Virgo
    Zodiac.LIBRA -> Strings.User.Zodiac.Libra
    Zodiac.SCORPIO -> Strings.User.Zodiac.Scorpio
    Zodiac.SAGITTARIUS -> Strings.User.Zodiac.Sagittarius
    Zodiac.CAPRICORN -> Strings.User.Zodiac.Capricorn
    Zodiac.AQUARIUS -> Strings.User.Zodiac.Aquarius
    Zodiac.PISCES -> Strings.User.Zodiac.Pisces
    Zodiac.UNKNOWN__ -> Strings.User.Zodiac.Aries
}

internal fun Sports.toName() = when (this) {
    YOGA -> Strings.Interest.Yoga
    BOXING -> Strings.Interest.Boxing
    RUNNING -> Strings.Interest.Running
    GYM_WORKOUTS -> Strings.Interest.GymWorkouts
    CYCLING -> Strings.Interest.Cycling
    HIKING -> Strings.Interest.Hiking
    SWIMMING -> Strings.Interest.Swimming
    MARTIAL_ARTS -> Strings.Interest.MartialArts
    BASKETBALL -> Strings.Interest.Basketball
    FOOTBALL -> Strings.Interest.Football
    TENNIS -> Strings.Interest.Tennis
    GOLF -> Strings.Interest.Golf
    SKIING -> Strings.Interest.Skiing
    SNOWBOARDING -> Strings.Interest.Snowboarding
    SURFING -> Strings.Interest.Surfing
    ROCK_CLIMBING -> Strings.Interest.RockClimbing
    ROWING -> Strings.Interest.Rowing
    PILATES -> Strings.Interest.Pilates
    SKATING -> Strings.Interest.Skating
    UNKNOWN__ -> Strings.Interest.Unknown
}

internal fun Culinary.toName() = when (this) {
    BAKING -> Baking
    BARBECUING -> Barbecuing
    COOKING -> Cooking
    CHOCOLATE_MAKING -> ChocolateMaking
    CRAFT_BEER -> CraftBeer
    FOODIE_TOURS -> FoodieTours
    MIXOLOGY -> Mixology
    VEGAN_CUISINE -> VeganCuisine
    VEGETARIAN_CUISINE -> VegetarianCuisine
    WINE_TASTING -> WineTasting
    Culinary.UNKNOWN__ -> Strings.Interest.Unknown
}

