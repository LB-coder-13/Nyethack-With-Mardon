package com.bignerdranch.nyethack

import kotlin.random.Random

class Player(
    initialName: String,
    val homeTown: String = "Neversummer",
    val isImmortal: Boolean,
    override var healthPoints: Int,
    override val diceCount: Int,
    override val diceSides: Int
) : Fightable{

    override var name = initialName
        get() = field.replaceFirstChar { it.uppercase() }
        private set

    override fun takeDamage(damage: Int) {
        if(!isImmortal){
            healthPoints -= damage
        }
    }

    override fun attack(opponent: Fightable) {
        val damageRoll = (0 until  diceCount).sumOf { Random.nextInt(diceSides+1) }
        narrate("$name deals $damageRoll to ${opponent.name}")
        opponent.takeDamage(damageRoll)
    }

    val title: String
        get() = when {
            name.all { it.isDigit() } -> "The Identifiable"
            name.none { it.isLetter() } -> "The Witness Protection Member"
            name.count { it.lowercase() in "aeiou" } > 4 -> "The Master of Vowels"
            else -> "The Renowned Hero"
        }

    val prophecy by lazy {
        narrate("$name embarks on an arduous quest to locate a fortune teller")
        Thread.sleep(3000)
        narrate("The fortune teller bestows a prophecy upon $name")
        "An intrepid hero from $homeTown shall some day " + listOf(
            "form an unlikely bond between two warring factions",
            "take possession of an otherworldly blade",
            "bring the gift of creation back to the world",
            "best the world-eater"
        ).random()
    }

    fun prophesize() {
        narrate("$name thinks about their future")
        narrate("A fortune teller told Madrigal, \"$prophecy\"")
    }

    init {
        require(healthPoints > 0) { "healthPoints must be greater than zero" }
        require(name.isNotBlank()) { "Player must have a name"}
        // IllegalArgumentException - если условия не выполнены
    }

    // дополнительный конструктор
    constructor(name: String) : this(
        initialName = name,
        healthPoints = 100,
        isImmortal = false,
        diceCount = 2,
        diceSides = 6
    ) {
        if (name.equals("Jason", ignoreCase = true)) {
            healthPoints = 500
        }
    }

    fun castFireball(numFireballs: Int = 2){
        narrate("A glass of Fireball springs into existence (x$numFireballs)")
    }

    fun changeName(newName: String) {
        narrate("$name legally changes their name to $newName")
        name = newName
    }

}

