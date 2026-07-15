package com.bignerdranch.nyethack

import com.bignerdranch.nyethack.Game.fight

open class Room(val name: String) {

    protected open val status = "Calm"


    open fun description() = name

    open fun enterRoom() {
        narrate("There is nothing to do here")
    }
}

open class TownSquare : Room("The town square"){
    override val status = "Bustling"

    override fun enterRoom(){
        narrate("The villagers rally and cheer as the hero enters")
    }
    companion object{
        fun ringBell() {
        val bellSound = "GWONG"
        narrate("The bell tower announces the hero's presence: $bellSound")
        }
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

class BackRoom : MonsterRoom("The Back Room"){

    override val status = "Dark"

    override fun enterRoom() {
        narrate("You enter the mysterious back room. There is nothing to do here.")
    }
}

class Dungeon : MonsterRoom("The Dungeon"){

    override val status = "Creepy"

    override fun enterRoom() {
        narrate("You push past the blocked off exit. No one has ever returned alive from here.")
    }
    companion object {
        fun explore() {
            narrate("You decide to go deeper into the dungeon and explore.")
            Thread.sleep(2500)
            narrate("On the sides of the dark corridors, there are rooms with skeletons.")
            Thread.sleep(2500)
            narrate("You suddenly hear faint footsteps ahead, and you stop in fear.")
            Thread.sleep(2000)
            while (true) {
                narrate("Are you going to go towards it? Are you going to run away?")
                val choice = readLine() ?: ""
                if (choice.lowercase().contains("towards")&&(0..1).random()==0) {
                    narrate("You decide to go towards the footsteps. They get louder as you come closer.")
                    Thread.sleep(2500)
                    narrate("Its a villager. He has ragged clothes, and he looks like he was here for a long while.")
                    Thread.sleep(2500)
                    narrate("You ask him: 'Why are you here?' ")
                    Thread.sleep(5000)
                    narrate("He stares at you, and after a while, he answers: I... I just went down exploring...")
                    Thread.sleep(800)
                    narrate("... And im trying to find my way out. I got lost.")
                    Thread.sleep(3500)
                    narrate("After a talk, you decide to go back with him out of the Dungeon.")
                    Thread.sleep(2500)
                    break
                } else if (choice.lowercase().contains("towards")){
                    narrate("You decide to go towards the footsteps. They get louder as you come closer.")
                    Thread.sleep(2500)
                    narrate("Its a big nasty goblin creature. But there is only one option. Fight.")
                    Thread.sleep(2500)
                    fight()
                    Thread.sleep(2500)
                    narrate("Now you start heading towards the exit.")
                    Thread.sleep(4000)
                    break
                }else if (choice.lowercase().contains("run")) {
                    narrate("You start running to the exit.")
                    Thread.sleep(1000)
                    break
                } else {
                    narrate("Im not sure what are you trying to do.")
                    Thread.sleep(2000)
                }
            }
            narrate("You are back in front of the exit of The Dungeon")
            Thread.sleep(3500)
        }
    }
}























