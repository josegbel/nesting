package com.ajlabs.forevely.feature.updateProfile

import com.ajlabs.forevely.data.type.Language
import com.ajlabs.forevely.data.type.Language.Afrikaans
import com.ajlabs.forevely.data.type.Language.Arabic
import com.ajlabs.forevely.data.type.Language.Armenian
import com.ajlabs.forevely.data.type.Language.Azerbaijani
import com.ajlabs.forevely.data.type.Language.Basque
import com.ajlabs.forevely.data.type.Language.Belarusian
import com.ajlabs.forevely.data.type.Language.Bengali
import com.ajlabs.forevely.data.type.Language.Bulgarian
import com.ajlabs.forevely.data.type.Language.Catalan
import com.ajlabs.forevely.data.type.Language.Chinese
import com.ajlabs.forevely.data.type.Language.Creole
import com.ajlabs.forevely.data.type.Language.Croatian
import com.ajlabs.forevely.data.type.Language.Czech
import com.ajlabs.forevely.data.type.Language.Danish
import com.ajlabs.forevely.data.type.Language.Dutch
import com.ajlabs.forevely.data.type.Language.English
import com.ajlabs.forevely.data.type.Language.Esperanto
import com.ajlabs.forevely.data.type.Language.Estonian
import com.ajlabs.forevely.data.type.Language.Filipino
import com.ajlabs.forevely.data.type.Language.Finnish
import com.ajlabs.forevely.data.type.Language.French
import com.ajlabs.forevely.data.type.Language.Galician
import com.ajlabs.forevely.data.type.Language.Georgian
import com.ajlabs.forevely.data.type.Language.German
import com.ajlabs.forevely.data.type.Language.Greek
import com.ajlabs.forevely.data.type.Language.Haitian
import com.ajlabs.forevely.data.type.Language.Hebrew
import com.ajlabs.forevely.data.type.Language.Hindi
import com.ajlabs.forevely.data.type.Language.Hungarian
import com.ajlabs.forevely.data.type.Language.Icelandic
import com.ajlabs.forevely.data.type.Language.Indonesian
import com.ajlabs.forevely.data.type.Language.Irish
import com.ajlabs.forevely.data.type.Language.Italian
import com.ajlabs.forevely.data.type.Language.Japanese
import com.ajlabs.forevely.data.type.Language.Kazakh
import com.ajlabs.forevely.data.type.Language.Korean
import com.ajlabs.forevely.data.type.Language.Latin
import com.ajlabs.forevely.data.type.Language.Latvian
import com.ajlabs.forevely.data.type.Language.Lithuanian
import com.ajlabs.forevely.data.type.Language.Macedonian
import com.ajlabs.forevely.data.type.Language.Malay
import com.ajlabs.forevely.data.type.Language.Norwegian
import com.ajlabs.forevely.data.type.Language.Persian
import com.ajlabs.forevely.data.type.Language.Polish
import com.ajlabs.forevely.data.type.Language.Portuguese
import com.ajlabs.forevely.data.type.Language.Romanian
import com.ajlabs.forevely.data.type.Language.Russian
import com.ajlabs.forevely.data.type.Language.Serbian
import com.ajlabs.forevely.data.type.Language.Slovak
import com.ajlabs.forevely.data.type.Language.Slovenian
import com.ajlabs.forevely.data.type.Language.Spanish
import com.ajlabs.forevely.data.type.Language.Swahili
import com.ajlabs.forevely.data.type.Language.Swedish
import com.ajlabs.forevely.data.type.Language.Thai
import com.ajlabs.forevely.data.type.Language.Turkish
import com.ajlabs.forevely.data.type.Language.UNKNOWN__
import com.ajlabs.forevely.data.type.Language.Ukrainian
import com.ajlabs.forevely.data.type.Language.Urdu
import com.ajlabs.forevely.data.type.Language.Uzbek
import com.ajlabs.forevely.data.type.Language.Vietnamese
import com.ajlabs.forevely.data.type.Language.Welsh
import com.ajlabs.forevely.localization.Strings.User.Languages
import com.ajlabs.forevely.localization.Strings.User.Languages.Unknown
import com.ajlabs.forevely.localization.getString

fun getFlagEmoji(language: Language): String {
    return when (language) {
        Afrikaans -> "🇿🇦"
        Arabic -> "🇸🇦"
        Armenian -> "🇦🇲"
        Azerbaijani -> "🇦🇿"
        Basque -> "🏴󠁥󠁳󠁰󠁶󠁿" // Basque Country
        Belarusian -> "🇧🇾"
        Bengali -> "🇧🇩"
        Bulgarian -> "🇧🇬"
        Catalan -> "🏴󠁥󠁳󠁣󠁴󠁿" // Catalonia
        Chinese -> "🇨🇳"
        Croatian -> "🇭🇷"
        Czech -> "🇨🇿"
        Danish -> "🇩🇰"
        Dutch -> "🇳🇱"
        English -> "🇬🇧"
        Esperanto -> "🌟" // Esperanto has no associated flag
        Estonian -> "🇪🇪"
        Filipino -> "🇵🇭"
        Finnish -> "🇫🇮"
        French -> "🇫🇷"
        Galician -> "🏴󠁥󠁳󠁧󠁡󠁿" // Galicia
        Georgian -> "🇬🇪"
        German -> "🇩🇪"
        Greek -> "🇬🇷"
        Haitian -> "🇭🇹"
        Hebrew -> "🇮🇱"
        Hindi -> "🇮🇳"
        Hungarian -> "🇭🇺"
        Icelandic -> "🇮🇸"
        Indonesian -> "🇮🇩"
        Irish -> "🇮🇪"
        Italian -> "🇮🇹"
        Japanese -> "🇯🇵"
        Kazakh -> "🇰🇿"
        Korean -> "🇰🇷"
        Latin -> "🏛️" // Ancient language
        Latvian -> "🇱🇻"
        Lithuanian -> "🇱🇹"
        Macedonian -> "🇲🇰"
        Malay -> "🇲🇾"
        Norwegian -> "🇳🇴"
        Persian -> "🇮🇷"
        Polish -> "🇵🇱"
        Portuguese -> "🇵🇹"
        Romanian -> "🇷🇴"
        Russian -> "🇷🇺"
        Serbian -> "🇷🇸"
        Slovak -> "🇸🇰"
        Slovenian -> "🇸🇮"
        Spanish -> "🇪🇸"
        Swahili -> "🇹🇿" // Swahili is widely spoken in Tanzania
        Swedish -> "🇸🇪"
        Thai -> "🇹🇭"
        Turkish -> "🇹🇷"
        Ukrainian -> "🇺🇦"
        Urdu -> "🇵🇰"
        Uzbek -> "🇺🇿"
        Vietnamese -> "🇻🇳"
        Welsh -> "🏴󠁧󠁢󠁷󠁬󠁳󠁿"
        else -> "❓" // Unknown or not applicable
    }
}

fun Language.getString(): String {
    return when (this) {
        Afrikaans -> getString(Languages.Afrikaans)
        Arabic -> getString(Languages.Arabic)
        Armenian -> getString(Languages.Armenian)
        Azerbaijani -> getString(Languages.Azerbaijani)
        Basque -> getString(Languages.Basque)
        Belarusian -> getString(Languages.Belarusian)
        Bengali -> getString(Languages.Bengali)
        Bulgarian -> getString(Languages.Bulgarian)
        Catalan -> getString(Languages.Catalan)
        Chinese -> getString(Languages.Chinese)
        Croatian -> getString(Languages.Croatian)
        Czech -> getString(Languages.Czech)
        Danish -> getString(Languages.Danish)
        Dutch -> getString(Languages.Dutch)
        English -> getString(Languages.English)
        Esperanto -> getString(Languages.Esperanto)
        Estonian -> getString(Languages.Estonian)
        Filipino -> getString(Languages.Filipino)
        Finnish -> getString(Languages.Finnish)
        French -> getString(Languages.French)
        Galician -> getString(Languages.Galician)
        Georgian -> getString(Languages.Georgian)
        German -> getString(Languages.German)
        Greek -> getString(Languages.Greek)
        Haitian -> getString(Languages.Haitian)
        Creole -> getString(Languages.Creole)
        Hebrew -> getString(Languages.Hebrew)
        Hindi -> getString(Languages.Hindi)
        Hungarian -> getString(Languages.Hungarian)
        Icelandic -> getString(Languages.Icelandic)
        Indonesian -> getString(Languages.Indonesian)
        Irish -> getString(Languages.Irish)
        Italian -> getString(Languages.Italian)
        Japanese -> getString(Languages.Japanese)
        Kazakh -> getString(Languages.Kazakh)
        Korean -> getString(Languages.Korean)
        Latin -> getString(Languages.Latin)
        Latvian -> getString(Languages.Latvian)
        Lithuanian -> getString(Languages.Lithuanian)
        Macedonian -> getString(Languages.Macedonian)
        Malay -> getString(Languages.Malay)
        Norwegian -> getString(Languages.Norwegian)
        Persian -> getString(Languages.Persian)
        Polish -> getString(Languages.Polish)
        Portuguese -> getString(Languages.Portuguese)
        Romanian -> getString(Languages.Romanian)
        Russian -> getString(Languages.Russian)
        Serbian -> getString(Languages.Serbian)
        Slovak -> getString(Languages.Slovak)
        Slovenian -> getString(Languages.Slovenian)
        Spanish -> getString(Languages.Spanish)
        Swahili -> getString(Languages.Swahili)
        Swedish -> getString(Languages.Swedish)
        Thai -> getString(Languages.Thai)
        Turkish -> getString(Languages.Turkish)
        Ukrainian -> getString(Languages.Ukrainian)
        Urdu -> getString(Languages.Urdu)
        Uzbek -> getString(Languages.Uzbek)
        Vietnamese -> getString(Languages.Vietnamese)
        Welsh -> getString(Languages.Welsh)
        UNKNOWN__ -> getString(Unknown)
    }
}
