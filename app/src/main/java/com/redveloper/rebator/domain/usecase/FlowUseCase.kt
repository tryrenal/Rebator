package com.redveloper.rebator.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

abstract class FlowUseCase<T> {
    private val _trigger = MutableStateFlow(true)

    val resultFlow : Flow<T> = _trigger.flatMapLatest {
        perfomAction()
    }

    suspend fun launch(){
        _trigger.emit(!(_trigger.value))
    }

    protected abstract fun perfomAction(): Flow<T>
}