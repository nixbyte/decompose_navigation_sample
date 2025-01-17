package com.epicwindmill.decomposekmmnavigationsample.components.tabs.first

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.epicwindmill.decomposekmmnavigationsample.components.tabs.first.screena1.IScreenA1
import com.epicwindmill.decomposekmmnavigationsample.components.tabs.first.screena1.ScreenA1Component
import com.epicwindmill.decomposekmmnavigationsample.components.tabs.first.screena2.IScreenA2
import com.epicwindmill.decomposekmmnavigationsample.components.tabs.first.screena2.ScreenA2Component

class ScreenAComponent(
    componentContext: ComponentContext
) : IScreenA, ComponentContext by componentContext {

//    private val router =
//        router<Config, IScreenA.Child>(
//            initialConfiguration = Config.ScreenA1,
//            handleBackButton = true,
//            childFactory = ::createChild
//        )
//
//    override val routerState: Value<RouterState<*, IScreenA.Child>> = router.state

    private val navigation = StackNavigation<Config>()
    override val stack =
        childStack(
            source = navigation,
            initialStack = { listOf(Config.ScreenA1) },
            childFactory = ::createChild,
            handleBackButton = true,
        )

    private fun createChild(config: Config, componentContext: ComponentContext): IScreenA.Child =
        when (config) {
            is Config.ScreenA1 -> IScreenA.Child.ScreenA1(screenA1(componentContext))
            is Config.ScreenA2 -> IScreenA.Child.ScreenA2(screenA2(componentContext))
        }

    private fun screenA1(componentContext: ComponentContext): IScreenA1 =
        ScreenA1Component(componentContext) {
//            router.push(Config.ScreenA2)
            navigation.push(Config.ScreenA2)
        }

    private fun screenA2(componentContext: ComponentContext): IScreenA2 =
        ScreenA2Component(componentContext, onFinished = {
//            router.pop()
            navigation.pop {  }
        })

    sealed class Config : Parcelable {
        @Parcelize
        object ScreenA1 : Config()

        @Parcelize
        object ScreenA2 : Config()
    }
}