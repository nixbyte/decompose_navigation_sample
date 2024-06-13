package com.epicwindmill.decomposekmmnavigationsample.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.epicwindmill.decomposekmmnavigationsample.components.main.IMain
import com.epicwindmill.decomposekmmnavigationsample.components.main.MainComponent

class RootComponent(
    componentContext: ComponentContext
) : IRoot, ComponentContext by componentContext {

    override val stack =
        childStack(
            source = StackNavigation(),
            initialStack = { listOf(Config.Main) },
            childFactory = ::createChild,
            handleBackButton = true,
        )

    private fun createChild(config: Config, componentContext: ComponentContext): IRoot.Child =
        when (config) {
            is Config.Main -> IRoot.Child.Main(main(componentContext))
        }

    private fun main(componentContext: ComponentContext): IMain =
        MainComponent(componentContext)

    sealed class Config : Parcelable {
        @Parcelize
        object Main : Config()
    }
}