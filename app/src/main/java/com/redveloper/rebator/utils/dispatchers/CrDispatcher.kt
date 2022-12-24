package com.redveloper.rebator.utils.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

interface CrDispatcher {
    fun ui(): MainCoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun network(): CoroutineDispatcher
}