package com.ajlabs.forevely.feature.root

import com.ajlabs.forevely.data.dataModule
import org.koin.dsl.module

val rootModule = module {
    includes(dataModule)
}
