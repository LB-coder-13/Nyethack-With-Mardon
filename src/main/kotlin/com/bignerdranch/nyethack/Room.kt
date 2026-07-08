package com.bignerdranch.nyethack

open class Room(val name: String) {

    protected open val status = "Calm"


    open fun description() = name

    open fun enterRoom() {
        narrate("There is nothing to do here")
    }
}

open class TownSquare : Room("The town square"){
    private var bellSound = "GWONG"
    override val status = "Bustling"
    // test
    override fun enterRoom(){
        narrate("The villagers rally and cheer as the hero enters")
    }
    fun ringBell() {
        narrate("The bell tower announces the hero's presence: $bellSound")
    }
}


open class MonsterRoom(
    name: String,
    var monster: Monster? = Goblin()
) : Room(name) {
    override fun description() =
        super.description() + " (Creature: ${monster?.description ?: "None"})"
    override fun enterRoom() {
        if (monster == null) {
            super.enterRoom()
        } else {
            narrate("Danger is lurking in this room")
        }
    }
}






















