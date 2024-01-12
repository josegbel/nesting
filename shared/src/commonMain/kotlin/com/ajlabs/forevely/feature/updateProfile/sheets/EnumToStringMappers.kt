package com.ajlabs.forevely.feature.updateProfile.sheets

import com.ajlabs.forevely.data.type.Children
import com.ajlabs.forevely.data.type.Children.I_HAVE_AND_DONT_WANT_MORE
import com.ajlabs.forevely.data.type.Children.I_HAVE_AND_WANT_MORE
import com.ajlabs.forevely.data.type.Diet
import com.ajlabs.forevely.data.type.Diet.CARNIVORE
import com.ajlabs.forevely.data.type.Diet.HALAL
import com.ajlabs.forevely.data.type.Diet.KOSHER
import com.ajlabs.forevely.data.type.Diet.OMNIVORE
import com.ajlabs.forevely.data.type.Diet.PESCATARIAN
import com.ajlabs.forevely.data.type.Diet.VEGAN
import com.ajlabs.forevely.data.type.Diet.VEGETARIAN
import com.ajlabs.forevely.data.type.Drinking
import com.ajlabs.forevely.data.type.Education
import com.ajlabs.forevely.data.type.Education.BACHELORS
import com.ajlabs.forevely.data.type.Education.HIGH_SCHOOL
import com.ajlabs.forevely.data.type.Education.MASTERS
import com.ajlabs.forevely.data.type.Education.PHD
import com.ajlabs.forevely.data.type.Education.STUDYING_BACHELORS
import com.ajlabs.forevely.data.type.Education.STUDYING_POSTGRADUATE
import com.ajlabs.forevely.data.type.Fitness
import com.ajlabs.forevely.data.type.Fitness.EVERYDAY
import com.ajlabs.forevely.data.type.Fitness.NEVER
import com.ajlabs.forevely.data.type.Fitness.OFTEN
import com.ajlabs.forevely.data.type.Fitness.RARELY
import com.ajlabs.forevely.data.type.Fitness.SOMETIMES
import com.ajlabs.forevely.data.type.Gender
import com.ajlabs.forevely.data.type.Gender.FEMALE
import com.ajlabs.forevely.data.type.Gender.MALE
import com.ajlabs.forevely.data.type.Gender.NON_BINARY
import com.ajlabs.forevely.data.type.Gender.PREF_NOT_TO_SAY
import com.ajlabs.forevely.data.type.LoveLanguage
import com.ajlabs.forevely.data.type.Personality
import com.ajlabs.forevely.data.type.Pet
import com.ajlabs.forevely.data.type.Politics
import com.ajlabs.forevely.data.type.Politics.CENTER
import com.ajlabs.forevely.data.type.Politics.LEFT_WING
import com.ajlabs.forevely.data.type.Politics.NON_POLITICAL
import com.ajlabs.forevely.data.type.Politics.RIGHT_WING
import com.ajlabs.forevely.data.type.Relationship
import com.ajlabs.forevely.data.type.Religion
import com.ajlabs.forevely.data.type.Religion.ATHEISM
import com.ajlabs.forevely.data.type.Religion.BUDDHISM
import com.ajlabs.forevely.data.type.Religion.CHRISTIANITY
import com.ajlabs.forevely.data.type.Religion.HINDUISM
import com.ajlabs.forevely.data.type.Religion.ISLAM
import com.ajlabs.forevely.data.type.Religion.JUDAISM
import com.ajlabs.forevely.data.type.Religion.OTHER
import com.ajlabs.forevely.data.type.Religion.SIKHISM
import com.ajlabs.forevely.data.type.Religion.UNKNOWN__
import com.ajlabs.forevely.data.type.Smoking
import com.ajlabs.forevely.data.type.Zodiac
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.Strings.Unknown
import com.ajlabs.forevely.localization.Strings.User
import com.ajlabs.forevely.localization.Strings.User.Children.IHaveAndDontWantMore
import com.ajlabs.forevely.localization.Strings.User.Children.IHaveAndWantMore
import com.ajlabs.forevely.localization.Strings.User.Diet.Vegan
import com.ajlabs.forevely.localization.Strings.User.Diet.Vegetarian
import com.ajlabs.forevely.localization.Strings.User.Drink.Rarely
import com.ajlabs.forevely.localization.Strings.User.Education.HighSchool
import com.ajlabs.forevely.localization.Strings.User.Education.Masters
import com.ajlabs.forevely.localization.Strings.User.Education.Phd
import com.ajlabs.forevely.localization.Strings.User.Gender.Female
import com.ajlabs.forevely.localization.Strings.User.Gender.Male
import com.ajlabs.forevely.localization.Strings.User.Gender.NonBinary
import com.ajlabs.forevely.localization.Strings.User.Gender.PreferNotToSay
import com.ajlabs.forevely.localization.Strings.User.Religion.Atheism
import com.ajlabs.forevely.localization.Strings.User.Religion.Buddhism
import com.ajlabs.forevely.localization.Strings.User.Religion.Christianity
import com.ajlabs.forevely.localization.Strings.User.Religion.Hinduism
import com.ajlabs.forevely.localization.Strings.User.Religion.Islam
import com.ajlabs.forevely.localization.Strings.User.Religion.Judaism
import com.ajlabs.forevely.localization.Strings.User.Religion.Other
import com.ajlabs.forevely.localization.Strings.User.Religion.Sikhism

internal fun Religion.stringKey(): Strings {
    return when (this) {
        CHRISTIANITY -> Christianity
        ISLAM -> Islam
        HINDUISM -> Hinduism
        BUDDHISM -> Buddhism
        JUDAISM -> Judaism
        SIKHISM -> Sikhism
        ATHEISM -> Atheism
        OTHER -> Other
        UNKNOWN__ -> Unknown
        else -> throw IllegalArgumentException("Unknown enum value: $this")
    }
}

internal fun Gender.stringKey(): Strings {
    return when (this) {
        MALE -> Male
        FEMALE -> Female
        NON_BINARY -> NonBinary
        PREF_NOT_TO_SAY -> PreferNotToSay
        Gender.UNKNOWN__ -> Unknown
    }
}

internal fun Children.stringKey(): Strings {
    return when (this) {
        I_HAVE_AND_WANT_MORE -> IHaveAndWantMore
        I_HAVE_AND_DONT_WANT_MORE -> IHaveAndDontWantMore
        Children.UNKNOWN__ -> Unknown
    }
}

internal fun Smoking.stringKey(): Strings {
    return when (this) {
        Smoking.NON_SMOKER -> User.Smoking.NonSmoker
        Smoking.SOCIAL_SMOKER -> User.Smoking.SocialSmoker
        Smoking.SMOKER_WHEN_DRINKING -> User.Smoking.SmokerWhenDrinking
        Smoking.REGULAR_SMOKER -> User.Smoking.RegularSmoker
        Smoking.TRYING_TO_QUIT -> User.Smoking.TryingToQuit
        Smoking.UNKNOWN__ -> Unknown
    }
}

internal fun Zodiac.stringKey(): Strings {
    return when (this) {
        Zodiac.ARIES -> User.Zodiac.Aries
        Zodiac.TAURUS -> User.Zodiac.Taurus
        Zodiac.GEMINI -> User.Zodiac.Gemini
        Zodiac.CANCER -> User.Zodiac.Cancer
        Zodiac.LEO -> User.Zodiac.Leo
        Zodiac.VIRGO -> User.Zodiac.Virgo
        Zodiac.LIBRA -> User.Zodiac.Libra
        Zodiac.SCORPIO -> User.Zodiac.Scorpio
        Zodiac.SAGITTARIUS -> User.Zodiac.Sagittarius
        Zodiac.CAPRICORN -> User.Zodiac.Capricorn
        Zodiac.AQUARIUS -> User.Zodiac.Aquarius
        Zodiac.PISCES -> User.Zodiac.Pisces
        Zodiac.UNKNOWN__ -> Unknown
    }
}

internal fun Relationship.stringKey(): Strings {
    return when (this) {
        Relationship.TRADITIONAL -> User.Relationship.Traditional
        Relationship.CONTEMPORARY -> User.Relationship.Contemporary
        Relationship.I_DONT_MIND -> User.Relationship.IDontMind
        Relationship.UNKNOWN__ -> Unknown
    }
}

internal fun Drinking.stringKey(): Strings {
    return when (this) {
        Drinking.RARELY -> Rarely
        Drinking.SOCIAL_DRINKER -> User.Drink.SocialDrinker
        Drinking.REGULAR_DRINKER -> User.Drink.RegularDrinker
        Drinking.HEAVY_DRINKER -> User.Drink.HeavyDrinker
        Drinking.THINKING_ABOUT_QUITTING -> User.Drink.ThinkingAboutQuitting
        Drinking.SOBER -> User.Drink.Sober
        Drinking.UNKNOWN__ -> Unknown
    }
}

internal fun Fitness.stringKey(): Strings {
    return when (this) {
        EVERYDAY -> User.Fitness.Everyday
        OFTEN -> User.Fitness.Often
        SOMETIMES -> User.Fitness.Sometimes
        RARELY -> User.Fitness.Rarely
        NEVER -> User.Fitness.Never
        Fitness.UNKNOWN__ -> Unknown
    }
}

internal fun Diet.stringKey(): Strings {
    return when (this) {
        VEGETARIAN -> Vegetarian
        VEGAN -> Vegan
        OMNIVORE -> User.Diet.Omnivore
        PESCATARIAN -> User.Diet.Pescatarian
        CARNIVORE -> User.Diet.Carnivore
        KOSHER -> User.Diet.Kosher
        HALAL -> User.Diet.Halal
        Diet.OTHER -> User.Diet.Other
        Diet.UNKNOWN__ -> Unknown
    }
}

internal fun Education.stringKey(): Strings {
    return when (this) {
        HIGH_SCHOOL -> HighSchool
        MASTERS -> Masters
        PHD -> Phd
        STUDYING_BACHELORS -> User.Education.StudyingBachelors
        BACHELORS -> User.Education.Bachelors
        STUDYING_POSTGRADUATE -> User.Education.StudyingPostGraduate
        Education.UNKNOWN__ -> Unknown
    }
}

internal fun LoveLanguage.stringKey(): Strings {
    return when (this) {
        LoveLanguage.WORDS_OF_AFFIRMATION -> User.LoveLanguage.WordsOfAffirmation
        LoveLanguage.QUALITY_TIME -> User.LoveLanguage.QualityTime
        LoveLanguage.RECEIVING_GIFTS -> User.LoveLanguage.ReceivingGifts
        LoveLanguage.ACTS_OF_SERVICE -> User.LoveLanguage.ActsOfService
        LoveLanguage.PHYSICAL_TOUCH -> User.LoveLanguage.PhysicalTouch
        LoveLanguage.UNKNOWN__ -> Unknown
    }
}

internal fun Personality.stringKey(): Strings {
    return when (this) {
        Personality.INTJ -> User.Personality.INTJ
        Personality.INTP -> User.Personality.INTP
        Personality.ENTJ -> User.Personality.ENTJ
        Personality.ENTP -> User.Personality.ENTP
        Personality.INFJ -> User.Personality.INFJ
        Personality.INFP -> User.Personality.INFP
        Personality.ENFJ -> User.Personality.ENFJ
        Personality.ENFP -> User.Personality.ENFP
        Personality.ISTJ -> User.Personality.ISTJ
        Personality.ISFJ -> User.Personality.ISFJ
        Personality.ESTJ -> User.Personality.ESTJ
        Personality.ESFJ -> User.Personality.ESFJ
        Personality.ISTP -> User.Personality.ISTP
        Personality.ISFP -> User.Personality.ISFP
        Personality.ESTP -> User.Personality.ESTP
        Personality.ESFP -> User.Personality.ESFP
        Personality.UNKNOWN__ -> Unknown
    }
}

internal fun Pet.stringKey(): Strings {
    return when (this) {
        Pet.DOGS -> User.Pets.Dogs
        Pet.CATS -> User.Pets.Cats
        Pet.BIRDS -> User.Pets.Birds
        Pet.REPTILES -> User.Pets.Reptiles
        Pet.FISH -> User.Pets.Fish
        Pet.OTHER -> User.Pets.Other
        Pet.UNKNOWN__ -> Unknown
    }
}

internal fun Politics.stringKey(): Strings {
    return when (this) {
        LEFT_WING -> User.Politics.LeftWing
        RIGHT_WING -> User.Politics.RightWing
        CENTER -> User.Politics.Center
        NON_POLITICAL -> User.Politics.NonPolitical
        Politics.UNKNOWN__ -> Unknown
    }
}
