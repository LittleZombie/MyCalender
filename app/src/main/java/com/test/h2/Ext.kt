package com.test.h2

import java.io.Serializable

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> Serializable.checkSerializableIsListOf() =
    if (this is List<*> && this.all { it is T })
        this as List<T>
    else null