package com.redveloper.rebator.domain.usecase.user

import com.redveloper.rebator.data.local.preference.UserPreference
import com.redveloper.rebator.domain.entity.User
import com.redveloper.rebator.domain.repository.UserRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.*

class GetUserUseCase (
    private val userRepository: UserRepository,
    private val crDispatcher: CrDispatcher,
    private val userPreference: UserPreference
): FlowUseCase<State<User>>() {
    override fun perfomAction(): Flow<State<User>> {
        return flow<State<User>> {
            emit(State.loading())

            userPreference.getUserID()
                .collect{ userId ->
                    userId?.let {
                        val user = userRepository.getUser(userId)
                        emit(State.Success(user))
                    }
                }

        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }
}