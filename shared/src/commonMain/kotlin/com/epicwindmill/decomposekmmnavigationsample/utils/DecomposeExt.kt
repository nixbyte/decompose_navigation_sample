package com.epicwindmill.decomposekmmnavigationsample.utils

import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.decompose.router.stack.navigate

inline fun <C : Parcelable, reified T : C> StackNavigation<C>.navigateSingleTop(crossinline config: () -> T) {
    navigate { stack ->
        val oldConfig: C? = stack.find { it is T }
        if (oldConfig != null) {
            (stack - oldConfig) + oldConfig
        } else {
            stack + config()
        }
    }
}
