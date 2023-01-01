package com.dev.playground.presentation.ui.main

import com.dev.playground.presentation.model.base.UiState

class MainContract {

    sealed interface State : UiState {
        object Idle : State
    }

}