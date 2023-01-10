package com.redveloper.rebator.domain.usecase.auth

import com.redveloper.rebator.data.local.preference.UserPreference
import com.redveloper.rebator.domain.repository.AuthRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LogoutUseCase(
    private val authRepository: AuthRepository,
    private val userPreference: UserPreference,
    private val crDispatcher: CrDispatcher
): FlowUseCase<State<Boolean>>() {

    override fun perfomAction(): Flow<State<Boolean>> {
        return flow<State<Boolean>> {
            emit(State.loading())

            userPreference.deleteUserID()
            authRepository.logout()

            emit(State.success(true))

        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }
}