package com.ajlabs.forevely.feature.updateProfile

sealed interface InterestCategory {
    data object Sports : InterestCategory
    data object Culinaries : InterestCategory
    data object Creativities : InterestCategory
    data object Leisures : InterestCategory
    data object Socials : InterestCategory
    data object Technologies : InterestCategory
    data object Natures : InterestCategory
    data object Minds : InterestCategory
}
