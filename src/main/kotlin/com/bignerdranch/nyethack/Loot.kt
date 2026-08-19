package com.bignerdranch.nyethack

class LootBox<T>(var contents: T) {
    var isOpen = false
        private set

    fun takeLoot(): T? {
        return contents.takeIf { !isOpen }.also { isOpen = true }
    }
}

class Fedora(
    val name: String,
    val value: Int
)

class Gemstones(
    val value: Int
)

class Key(
    val name: String
)