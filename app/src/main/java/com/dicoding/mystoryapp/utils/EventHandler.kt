package com.dicoding.mystoryapp.utils

import java.util.concurrent.atomic.AtomicBoolean

class EventHandler<Out T>(privat val content: T) {
    private val hasBeenHandled = AtomicBoolean(false)

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled.getAndSet(true)) {
            null
        } else {
            content
        }
    }
}