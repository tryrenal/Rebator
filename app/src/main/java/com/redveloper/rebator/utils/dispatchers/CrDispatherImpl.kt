package com.redveloper.rebator.utils.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

class CrDispatherImpl : CrDispatcher {
    override fun ui(): MainCoroutineDispatcher {
        return Dispatchers.Main
    }
    override fun network(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    override fun default(): CoroutineDispatcher {
        return Dispatchers.Default
    }
}