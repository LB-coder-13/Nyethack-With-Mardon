package com.bignerdranch.nyethack

import javax.swing.tree.RowMapper

fun String.addEnthusiasm(enthusiasmLevel: Int = 1) = this + "!".repeat(enthusiasmLevel)

val String.numVowels
    get() = count { it.lowercase() in "aeiou" }

fun <T : Any> T.print(): T {
    println(this)
    return this
}

// []
operator fun List<List<Room>>.get(coordinate: Coordinate) = getOrNull(coordinate.y)?.getOrNull(coordinate.x)

infix fun Coordinate.move(direction: Direction) = direction.updateCoordinate(this)

fun Room?.orEmptyRoom(name: String = "the middle of nowhere"): Room = this ?: Room(name)