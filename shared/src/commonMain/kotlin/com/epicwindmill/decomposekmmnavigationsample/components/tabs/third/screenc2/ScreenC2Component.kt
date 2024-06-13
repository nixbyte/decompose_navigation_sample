package com.epicwindmill.decomposekmmnavigationsample.components.tabs.third.screenc2

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackCallback

class ScreenC2Component (
    private val componentContext: ComponentContext,
    private val onFinished: (result: Int) -> Unit
) : IScreenC2, ComponentContext by componentContext {

    init {
        backHandler.register(onBackPressed())
    }

    // Used by iOS
    override fun onBackClicked() {
        // Return a result to the previous component
        onFinished(1234)
    }

    // Used by Android
    private fun onBackPressed(): BackCallback {
        return BackCallback {
            onBackClicked()
        }
    }
}