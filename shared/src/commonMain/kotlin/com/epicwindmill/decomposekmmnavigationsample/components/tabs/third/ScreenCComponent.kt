package com.epicwindmill.decomposekmmnavigationsample.components.tabs.third

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.epicwindmill.decomposekmmnavigationsample.components.tabs.third.screenc1.IScreenC1
import com.epicwindmill.decomposekmmnavigationsample.components.tabs.third.screenc1.ScreenC1Component
import com.epicwindmill.decomposekmmnavigationsample.components.tabs.third.screenc2.IScreenC2
import com.epicwindmill.decomposekmmnavigationsample.components.tabs.third.screenc2.ScreenC2Component
import com.epicwindmill.decomposekmmnavigationsample.utils.navigateSingleTop

class ScreenCComponent(
    componentContext: ComponentContext
) : IScreenC, ComponentContext by componentContext {

//    private val router =
//        router<Config, IScreenC.Child>(
//            initialConfiguration = Config.ScreenC1,
//            handleBackButton = false,
//            childFactory = ::createChild
//        )
//
//    override val routerState: Value<RouterState<*, IScreenC.Child>> = router.state
    private val navigation = StackNavigation<Config>()
    override val stack =
        childStack(
            source = navigation,
            initialStack = { listOf(Config.ScreenC1) },
            childFactory = ::createChild,
            handleBackButton = true,
        )

    private fun createChild(config: Config, componentContext: ComponentContext): IScreenC.Child =
        when (config) {
            is Config.ScreenC1 -> IScreenC.Child.ScreenC1(screenC1(componentContext))
            is Config.ScreenC2 -> IScreenC.Child.ScreenC2(screenC2(componentContext))
        }

    private fun screenC1(componentContext: ComponentContext): IScreenC1 =
        ScreenC1Component(componentContext) {
            navigation.push(Config.ScreenC2)
        }

    private fun screenC2(componentContext: ComponentContext): IScreenC2 =
        ScreenC2Component(componentContext, onFinished = {
            result ->
            navigation.pop {  }
            // The new active child should be Screen C1
            (stack.value.active.instance as IScreenC.Child.ScreenC1).component.onResult(result)
        })

    sealed class Config : Parcelable {
        @Parcelize
        object ScreenC1 : Config()

        @Parcelize
        object ScreenC2 : Config()
    }
}