package com.epicwindmill.decomposekmmnavigationsample.components.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.epicwindmill.decomposekmmnavigationsample.components.root.RootComponent
import com.epicwindmill.decomposekmmnavigationsample.components.tabs.first.IScreenA
import com.epicwindmill.decomposekmmnavigationsample.components.tabs.first.ScreenAComponent
import com.epicwindmill.decomposekmmnavigationsample.components.tabs.second.IScreenB
import com.epicwindmill.decomposekmmnavigationsample.components.tabs.second.ScreenBComponent
import com.epicwindmill.decomposekmmnavigationsample.components.tabs.third.IScreenC
import com.epicwindmill.decomposekmmnavigationsample.components.tabs.third.ScreenCComponent
import com.epicwindmill.decomposekmmnavigationsample.utils.navigateSingleTop

class MainComponent(
    componentContext: ComponentContext
) : IMain, ComponentContext by componentContext {

//    private val router =
//        childStack<Config, IMain.Child>(
//            initialStack = Config.ScreenA,
//            handleBackButton = true,
//            childFactory = ::createChild
//        )
//
//    override val routerState: Value<RouterState<*, IMain.Child>> = router.state
    private val navigation = StackNavigation<Config>()
    override val stack =
        childStack(
            source = navigation,
            initialStack = { listOf(Config.ScreenA) },
            childFactory = ::createChild,
            handleBackButton = true,
        )

    override val model: Value<IMain.Model> =
        stack.map { state ->
            IMain.Model(
                selectedTab = state.active.configuration.toTab()
            )
        }

    private fun createChild(config: Config, componentContext: ComponentContext): IMain.Child =
        when (config) {
            is Config.ScreenA -> IMain.Child.ScreenA(screenA(componentContext))
            is Config.ScreenB -> IMain.Child.ScreenB(screenB(componentContext))
            is Config.ScreenC -> IMain.Child.ScreenC(screenC(componentContext))
        }

    private fun screenA(componentContext: ComponentContext): IScreenA =
        ScreenAComponent(componentContext)

    private fun screenB(componentContext: ComponentContext): IScreenB =
        ScreenBComponent(componentContext)

    private fun screenC(componentContext: ComponentContext): IScreenC =
        ScreenCComponent(componentContext)

    override fun onTabClick(tab: IMain.Tab): Unit =
        when (tab) {
            IMain.Tab.A -> navigation.navigateSingleTop(config = {Config.ScreenA})
            IMain.Tab.B -> navigation.navigateSingleTop(config = {Config.ScreenB})
            IMain.Tab.C -> navigation.navigateSingleTop(config = {Config.ScreenC})
        }

    private fun Config.toTab(): IMain.Tab =
        when (this) {
            is Config.ScreenA -> IMain.Tab.A
            is Config.ScreenB -> IMain.Tab.B
            is Config.ScreenC -> IMain.Tab.C
        }

    sealed class Config : Parcelable {
        @Parcelize
        object ScreenA : Config()

        @Parcelize
        object ScreenB : Config()

        @Parcelize
        object ScreenC : Config()
    }
}