package com.ajlabs.forevely.localization

sealed interface Strings {
    data object AppName : Strings
    data object CompanyName : Strings
    data object AppVersion : Strings
    data object AppCopyright : Strings

    data object ShowOtherPeopleIfIRunOut : Strings
    data object NoImage : Strings
    data object CloseButton : Strings
    data object Apply : Strings
    data object GetPremium : Strings
    data object ShowMore : Strings

    sealed interface Login : Strings {
        data object WelcomeMessage : Login
        data object LoginButton : Login
        data object LogoutButton : Login
        data object RegisterButton : Login
        data object EmailLabel : Login
        data object PasswordLabel : Login
        data object ConfirmPasswordLabel : Login
        data object ForgotPasswordLabel : Login
        data object SendButton : Login
        data object GoBackToLoginButton : Login
        data object DontHaveAnAccount : Login
    }

    sealed interface User : Strings {
        data object Verified : User

        sealed interface Height: User {
            data object Name : User
            data object ModalTitle : User
        }

        sealed interface Children : User {
            data object ModalTitle: Children
            data object Name : Children
            data object Question : Children
            data object IHaveAndWantMore : Children
            data object IHaveAndDontWantMore : Children
        }

        sealed interface Diet : User {
            data object ModalTitle: Diet
            data object Name : Diet
            data object Question : Diet
            data object Omnivore : Diet
            data object Vegan : Diet
            data object Vegetarian : Diet
            data object Pescatarian : Diet
            data object Carnivore : Diet
            data object Kosher : Diet
            data object Halal : Diet
            data object Other : Diet
        }

        sealed interface Drink : User {
            data object ModalTitle: Drink
            data object Name : Drink
            data object Question : Drink
            data object Rarely : Drink
            data object SocialDrinker : Drink
            data object RegularDrinker : Drink
            data object HeavyDrinker : Drink
            data object ThinkingAboutQuitting : Drink
            data object Sober : Drink
        }

        sealed interface Education : User {
            data object ModalTitle: Education
            data object Name : Education
            data object Question : Education
            data object HighSchool : Education
            data object StudyingBachelors : Education
            data object Bachelors : Education
            data object StudyingPostGraduate : Education
            data object Masters : Education
            data object Phd : Education
        }

        sealed interface Fitness : User {
            data object ModalTitle: Fitness
            data object Name : Fitness
            data object Question : Fitness
            data object Everyday : Fitness
            data object Often : Fitness
            data object Sometimes : Fitness
            data object Rarely : Fitness
            data object Never : Fitness
        }

        sealed interface LoveLanguage : User {
            data object ModalTitle: LoveLanguage
            data object Name : LoveLanguage
            data object Question : LoveLanguage
            data object WordsOfAffirmation : LoveLanguage
            data object ActsOfService : LoveLanguage
            data object ReceivingGifts : LoveLanguage
            data object QualityTime : LoveLanguage
            data object PhysicalTouch : LoveLanguage
        }

        sealed interface Personality : User {
            data object ModalTitle: Personality
            data object Name : Personality
            data object Question : Personality
            data object INTJ : Personality
            data object INTP : Personality
            data object ENTJ : Personality
            data object ENTP : Personality
            data object INFJ : Personality
            data object INFP : Personality
            data object ENFJ : Personality
            data object ENFP : Personality
            data object ISTJ : Personality
            data object ISFJ : Personality
            data object ESTJ : Personality
            data object ESFJ : Personality
            data object ISTP : Personality
            data object ISFP : Personality
            data object ESTP : Personality
            data object ESFP : Personality
        }

        sealed interface Languages: Strings {
            data object ModalTitle: Languages
            data object ModalSubtitle: Languages
            data object Afrikaans: Languages
            data object Arabic: Languages
            data object Armenian: Languages
            data object Azerbaijani: Languages
            data object Basque: Languages
            data object Belarusian: Languages
            data object Bengali: Languages
            data object Bulgarian: Languages
            data object Catalan: Languages
            data object Chinese: Languages
            data object Croatian: Languages
            data object Czech: Languages
            data object Danish: Languages
            data object Dutch: Languages
            data object English: Languages
            data object Esperanto: Languages
            data object Estonian: Languages
            data object Filipino: Languages
            data object Finnish: Languages
            data object French: Languages
            data object Galician: Languages
            data object Georgian: Languages
            data object German: Languages
            data object Greek: Languages
            data object Haitian: Languages
            data object Creole: Languages
            data object Hebrew: Languages
            data object Hindi: Languages
            data object Hungarian: Languages
            data object Icelandic: Languages
            data object Indonesian: Languages
            data object Irish: Languages
            data object Italian: Languages
            data object Japanese: Languages
            data object Kazakh: Languages
            data object Korean: Languages
            data object Latin: Languages
            data object Latvian: Languages
            data object Lithuanian: Languages
            data object Macedonian: Languages
            data object Malay: Languages
            data object Norwegian: Languages
            data object Persian: Languages
            data object Polish: Languages
            data object Portuguese: Languages
            data object Romanian: Languages
            data object Russian: Languages
            data object Serbian: Languages
            data object Slovak: Languages
            data object Slovenian: Languages
            data object Spanish: Languages
            data object Swahili: Languages
            data object Swedish: Languages
            data object Thai: Languages
            data object Turkish: Languages
            data object Ukrainian: Languages
            data object Urdu: Languages
            data object Uzbek: Languages
            data object Vietnamese: Languages
            data object Welsh: Languages
            data object Unknown: Languages
        }

        sealed interface Pets : User {
            data object ModalTitle: Pets
            data object Name : Pets
            data object Question : Pets
            data object Dogs : Pets
            data object Cats : Pets
            data object Birds : Pets
            data object Reptiles : Pets
            data object Fish : Pets
            data object Other : Pets
        }

        sealed interface Politics : User {
            data object ModalTitle: Politics
            data object Name : Politics
            data object Question : Politics
            data object LeftWing : Politics
            data object RightWing : Politics
            data object Center : Politics
            data object NonPolitical : Politics
        }

        sealed interface Gender : User {
            data object Name : Gender
            data object ModalTitle : Gender
            data object Question : Gender
            data object Male : Gender
            data object Female : Gender
            data object NonBinary : Gender
            data object PreferNotToSay : Gender
        }

        sealed interface Relationship : User {
            data object ModalTitle: Relationship
            data object Name : Relationship
            data object Question : User
            data object Traditional : Relationship
            data object Contemporary : Relationship
            data object IDontMind : Relationship
        }

        sealed interface Religion : User {
            data object ModalTitle: Religion
            data object Name : Religion
            data object Question : Religion
            data object Christianity : Religion
            data object Islam : Religion
            data object Hinduism : Religion
            data object Buddhism : Religion
            data object Judaism : Religion
            data object Sikhism : Religion
            data object Atheism : Religion
            data object Other : Religion
        }

        sealed interface Smoking : User {
            data object ModalTitle: Smoking
            data object Name : Smoking
            data object Question : Smoking
            data object RegularSmoker : Smoking
            data object SocialSmoker : Smoking
            data object SmokerWhenDrinking : Smoking
            data object TryingToQuit : Smoking
            data object NonSmoker : Smoking
        }

        sealed interface Zodiac : User {
            data object ModalTitle: Zodiac
            data object Name : Zodiac
            data object Question : Zodiac
            data object Aries : Zodiac
            data object Taurus : Zodiac
            data object Gemini : Zodiac
            data object Cancer : Zodiac
            data object Leo : Zodiac
            data object Virgo : Zodiac
            data object Libra : Zodiac
            data object Scorpio : Zodiac
            data object Sagittarius : Zodiac
            data object Capricorn : Zodiac
            data object Aquarius : Zodiac
            data object Pisces : Zodiac
        }
    }

    sealed interface UpdateProfile: Strings {
        data object AddPhotoFromGallery : UpdateProfile
        data object AddPhotoFromCamera : UpdateProfile
        data object RemovePhoto : UpdateProfile
        data object ProfileStrengthContent : UpdateProfile
        data object MyInterestsSectionTitle : UpdateProfile
        data object MyInterestsSectionSubtitle : UpdateProfile
        data object ProfilePromptsSectionTitle : UpdateProfile
        data object ProfilePromptsSectionSubtitle : UpdateProfile
        data object OpeningQuestionSectionTitle : UpdateProfile
        data object OpeningQuestionSectionSubtitle : UpdateProfile
        data object MyBioSectionTitle : UpdateProfile
        data object MyBioSectionSubtitle : UpdateProfile
        data object AboutMeSectionTitle : UpdateProfile
        data object AboutMeSectionSubtitle : UpdateProfile
        data object LanguagesSectionTitle : UpdateProfile
        data object MainPictureTitle : UpdateProfile
        data object MediaSectionTitle : UpdateProfile
        data object MediaSectionSubtitle : UpdateProfile
        data object FirstPictureContentDescription : UpdateProfile
        data object SecondPictureContentDescription : UpdateProfile
        data object ThirdPictureContentDescription : UpdateProfile
        data object ForthPictureContentDescription : UpdateProfile
        data object FifthPictureContentDescription : UpdateProfile
        data object SixthPictureContentDescription : UpdateProfile
        data object VerifyMyProfile : UpdateProfile
        data object Verified : UpdateProfile
        data object NotVerified : UpdateProfile
        data object NoInterests : UpdateProfile
        data object NoPrompts : UpdateProfile
        data object NoBio : UpdateProfile
        data object NoOpeningQuestion : UpdateProfile
        data object NoAboutMe : UpdateProfile
        data object NoLanguage : UpdateProfile
        data object Cancel: UpdateProfile
        data object Save: UpdateProfile
    }

    sealed interface Matcher : Strings {
        data object FilterIcon : Matcher
        data object SpeedDatingIcon : Matcher
        data object UndoSwipe : Matcher
        data object HideAndReport : Matcher
        data object LivesIn : Matcher
        data object MainPictureContentDescription : Matcher
        data object MyBasics : Matcher
        data object RecommendToFriendButton : Matcher
        data object LocationMilesAway : Matcher
        data object FiltersLabel : Matcher
        data object BasicFilters : Matcher
        data object AdvancedFilters : Matcher

        data object AgePrefQuestion : Matcher
        data object AgeRange : Matcher
        data object AgeSafeMargin : Matcher
        data object MaxDistancePrefQuestion : Matcher
        data object DistanceNoLimit : Matcher
        data object MaxDistancePrefRange : Matcher
        data object MaxDistanceSafeMargin : Matcher
        data object KnownLanguagesPrefQuestion : Matcher
        data object KnownLanguagesSelectLanguages : Matcher
        data object VerifiedPrefQuestion : Matcher
        data object VerifiedProfilesOnly : Matcher
        data object WhatsThisText : Matcher

        sealed interface HeightPref : Matcher {
            data object Question : HeightPref
            data object ValueText : HeightPref
            data object AnyHeightIsFine : HeightPref
            data object TallerThan : HeightPref
            data object ShorterThan : HeightPref
        }

        data object AddFilter : Strings
        data object RemoveFilter : Strings
    }

    sealed interface MyPlan : Strings {
        data object Premium : MyPlan
        data object ProfileCompleted : MyPlan
        data object ProfileNotCompleted : MyPlan
        data object UsersProfileImageCircle : MyPlan
    }

    sealed interface OpenQuestion : Strings {
        data object ModalTitle: OpenQuestion
        data object ModalSubtitle: OpenQuestion
        data object Achievement: OpenQuestion
        data object HistoricalEra: OpenQuestion
        data object ImpactfulBook: OpenQuestion
        data object UnwindMethod: OpenQuestion
        data object DinnerGuest: OpenQuestion
        data object LearnSkill: OpenQuestion
        data object ChildhoodMemory: OpenQuestion
        data object SpontaneousAct: OpenQuestion
        data object BiggestFear: OpenQuestion
        data object Passion: OpenQuestion
        data object PerfectDay: OpenQuestion
        data object LaughterMoment: OpenQuestion
        data object PeaceSource: OpenQuestion
        data object ChangeWorld: OpenQuestion
        data object FavoriteQuote: OpenQuestion
    }

    sealed interface Interest: Strings {
        data object ModalTitle: Interest
        data object ModalSubtitle: Interest
        data object Yoga: Interest
        data object Boxing: Interest
        data object Running: Interest
        data object GymWorkouts: Interest
        data object Cycling: Interest
        data object Hiking: Interest
        data object Swimming: Interest
        data object MartialArts: Interest
        data object Basketball: Interest
        data object Football: Interest
        data object Tennis: Interest
        data object Golf: Interest
        data object Skiing: Interest
        data object Snowboarding: Interest
        data object Surfing: Interest
        data object RockClimbing: Interest
        data object Rowing: Interest
        data object Pilates: Interest
        data object Skating: Interest
        data object Cooking: Interest
        data object Baking: Interest
        data object WineTasting: Interest
        data object CraftBeer: Interest
        data object FoodieTours: Interest
        data object Barbecuing: Interest
        data object VeganCuisine: Interest
        data object VegetarianCuisine: Interest
        data object Mixology: Interest
        data object ChocolateMaking: Interest
        data object Painting: Interest
        data object Drawing: Interest
        data object Sculpting: Interest
        data object Photography: Interest
        data object Theater: Interest
        data object Singing: Interest
        data object PlayingAnInstrument: Interest
        data object Writing: Interest
        data object Pottery: Interest
        data object Knitting: Interest
        data object Sewing: Interest
        data object DiyCrafts: Interest
        data object Filmmaking: Interest
        data object Dancing: Interest
        data object StandUpComedy: Interest
        data object Shopping: Interest
        data object Traveling: Interest
        data object Fishing: Interest
        data object Boating: Interest
        data object BirdWatching: Interest
        data object BoardGames: Interest
        data object VideoGaming: Interest
        data object WatchingMovies: Interest
        data object Reading: Interest
        data object Puzzles: Interest
        data object CoinCollecting: Interest
        data object Antiquing: Interest
        data object Astronomy: Interest
        data object Concerts: Interest
        data object MuseumVisiting: Interest
        data object TheaterGoing: Interest
        data object Opera: Interest
        data object BookClub: Interest
        data object LanguageLearning: Interest
        data object SocialActivism: Interest
        data object Volunteering: Interest
        data object PoliticalCampaigning: Interest
        data object AnimalRescue: Interest
        data object Coding: Interest
        data object Robotics: Interest
        data object WebDesign: Interest
        data object Blogging: Interest
        data object Podcasting: Interest
        data object DronePiloting: Interest
        data object ThreedeePrinting: Interest
        data object Gadgeteering: Interest
        data object Cybersecurity: Interest
        data object Camping: Interest
        data object WildlifeConservation: Interest
        data object Gardening: Interest
        data object EnvironmentalActivism: Interest
        data object SustainableLiving: Interest
        data object HorsebackRiding: Interest
        data object NaturePhotography: Interest
        data object ScubaDiving: Interest
        data object Kayaking: Interest
        data object Sailing: Interest
        data object Geocaching: Interest
        data object Meditation: Interest
        data object Tarot: Interest
        data object Astrology: Interest
        data object PhilosophicalDebates: Interest
        data object Psychology: Interest
        data object Mindfulness: Interest
        data object SelfImprovement: Interest
        data object Unknown: Interest
    }

    sealed interface InterestCategory: Strings {
        data object Sports : InterestCategory
        data object Culinary : InterestCategory
        data object Creative : InterestCategory
        data object Leisure : InterestCategory
        data object Social : InterestCategory
        data object Technology : InterestCategory
        data object Nature : InterestCategory
        data object Mind : InterestCategory
    }

    data object Unknown : Strings
    data object Save : Strings
    data object Add : Strings

    sealed interface Conversations : Strings {
        data object NoConversations : Conversations
        data object NoConversationsDescriptionWithMatches : Conversations
        data object NoConversationsDescriptionWithOutMatches : Conversations
        data object MatchQueue : Conversations
        data object Chats : Conversations
        data object Recent : Conversations
    }

    sealed interface OnBoarding : Strings {
        sealed interface PhoneNumber : OnBoarding {
            data object WhatsYourNumber : PhoneNumber
            data object HeaderDescription : PhoneNumber
            data object PhoneHeader : PhoneNumber
            data object PhoneDescription : PhoneNumber
        }
    }
}
