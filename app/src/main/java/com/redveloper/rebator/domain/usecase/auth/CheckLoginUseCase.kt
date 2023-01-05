package com.redveloper.rebator.domain.usecase.auth

import com.redveloper.rebator.data.local.preference.UserPreference
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.*

class CheckLoginUseCase(
    private val crDispatcher: CrDispatcher,
    private val userPreference: UserPreference
): FlowUseCase<State<Boolean>>() {

    override fun perfomAction(): Flow<State<Boolean>> {
        return flow<State<Boolean>> {
            emit(State.loading())
            userPreference.getUserID()
                .collect{ uid ->
                    emit(State.success(!uid.isNullOrBlank()))
                }
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.network())
    }
}