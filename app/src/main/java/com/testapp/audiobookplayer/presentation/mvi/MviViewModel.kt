package com.testapp.audiobookplayer.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * A base [ViewModel] implementation for MVI design pattern.
 *
 * @see Store
 */
abstract class MviViewModel<State : UiState, Intent : UiIntent, Effect : UiEffect>(
    emptyState: State,
) : ViewModel(),
    Store<State, Intent, Effect>,
    Reducer<State, Intent> {

    protected val state
        get() = stateFlow.value

    private val stateFlow = MutableStateFlow(emptyState)

    private val effectChannel = Channel<Effect>()

    private val intentFlow = MutableSharedFlow<Intent>()

    init {
        observeIntents()
    }

    override fun observeState(): StateFlow<State> {
        return stateFlow.asStateFlow()
    }

    override fun observeEffect(): Flow<Effect> {
        return effectChannel.receiveAsFlow()
    }

    override fun dispatch(intent: Intent) {
        viewModelScope.launch {
            intentFlow.emit(intent)
        }
    }

    protected inline fun updateState(build: State.() -> State): State {
        return state.build()
    }

    protected inline fun setEffect(build: () -> Effect) {
        setEffect(build())
    }

    protected fun setEffect(effect: Effect) {
        viewModelScope.launch { effectChannel.send(effect) }
    }

    private fun observeIntents() {
        viewModelScope.launch {
            intentFlow.collect { intent ->
                val newState = reduce(state, intent)
                stateFlow.value = newState
            }
        }
    }
}
