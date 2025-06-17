package com.testapp.audiobookplayer.presentation.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * A contract for immutable data structure representing UI state.
 */
interface UiState

/**
 * A contract for intent originated from UI to update a state.
 */
interface UiIntent

/**
 * A contract for side effect originated from state management.
 */
interface UiEffect

/**
 * A contract for MVI Store that:
 * - holds a state observable via [observeState] that can be updated with [dispatch];
 * - provides side effect flow with [observeEffect].
 */
interface Store<State : UiState, Intent : UiIntent, Effect : UiEffect> {

    fun observeState(): StateFlow<State>

    fun observeEffect(): Flow<Effect>

    fun dispatch(intent: Intent)
}

/**
 * A functional contract for updating [UiState] with [UiIntent]
 */
interface Reducer<S : UiState, I : UiIntent> {

    fun reduce(state: S, intent: I): S
}

inline fun <Intent : UiIntent> Store<*, Intent, *>.dispatch(build: () -> Intent) {
    dispatch(build())
}
