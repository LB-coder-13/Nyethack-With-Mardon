package com.bignerdranch.nyethack

import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.system.exitProcess

lateinit var player: Player // Я обязуюсь присвоить значение этой переменной до первой попытки обратиться к ней

//NyetHack
fun main(){
    narrate("Welcome to NyetHack!")
    val playerName = promptHeroName()
    player = Player(playerName)
    Game.play()
}


private fun promptHeroName(): String{
    narrate("Hero came to the town. What is their name?")

    println("Madrigal")
    return "Madrigal"
}

object Game{
    private val worldMap = listOf(
        listOf(TownSquare(), Tavern(), BackRoom()),
        listOf(MonsterRoom("A Long Corridor"), Room("Generic Room")),
        listOf(Dungeon())
    )

    private var currentRoom: Room = worldMap[0][0]
    private var currentPosition = Coordinate(0,0)
    private var gameOn = true
    private var kills = 0

    init{
        narrate("Welcome, adventurer")
        val mortality = if (player.isImmortal) "an immortal" else "a mortal"
        narrate("${player.name}, $mortality, has ${player.healthPoints} health points")
    }

    fun play(){
        while(gameOn){
            narrate("${player.name}, ${player.title}, is in ${currentRoom.description()}")
            currentRoom.enterRoom()

            print("> Enter your command: ")
            GameInput(readLine()).processCommand()
        }
    }

    fun move(direction: Direction){
        val newPosition = direction.updateCoordinate(currentPosition)
        val newRoom = worldMap.getOrNull(newPosition.y)?.getOrNull(newPosition.x)
        if (newRoom != null) {
            narrate("The hero moves ${direction.name}")
            currentPosition = newPosition
            currentRoom = newRoom
        } else {
            narrate("You cannot move ${direction.name}")
        }
    }

    fun fight(){
        val monsterRoom = currentRoom as? MonsterRoom
        val currentMonster = monsterRoom?.monster
        if (currentMonster == null){
            narrate("There's nothing to fight here")
            return
        }
        while (player.healthPoints > 0 && currentMonster.healthPoints > 0) {
            player.attack(currentMonster)
            if (currentMonster.healthPoints > 0) {
                Thread.sleep(1000)
                currentMonster.attack(player)
            }
            Thread.sleep(1000)
        }
        if(player.healthPoints <= 0) {
            narrate("You have been defeated! Thanks for playing")
            exitProcess(0)
        } else {
            narrate("${currentMonster.name} has been defeated")
            monsterRoom.monster = null
            kills++
        }
    }

    private class GameInput(arg: String?){
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1) {""}

        fun processCommand() = when (command.lowercase()){
            "move" ->{
                val direction = Direction.values().firstOrNull{ it.name.equals(argument, ignoreCase = true)}
                if (direction != null)
                    move(direction)
                else
                    narrate("I don't know what direction that is")
            }
            "stats" ->{
                narrate("---- Player Stats ----")
                narrate("HP: ${player.healthPoints}")
                narrate("Kills: $kills")
            }
            "fight" -> fight()
            "cast" ->{
                when (argument){
                    "fireball" -> player.castFireball()
                    else -> narrate("I don't know how to cast that")
                }
            }
            "prophesize" -> player.prophesize()
            "exit", "quit" -> {
                narrate("he hero decides leave this town, towards his next destination.")
                print("Exiting ")
                Thread.sleep(200)
                print("<10%>")// print("< =")
                Thread.sleep(700)
                print("\b\b\b\b\b<23%>")// print("===")
                Thread.sleep(1000)
                print("\b\b\b\b\b<45%>")// print("=")
                Thread.sleep(400)
                print("\b\b\b\b\b<57%>")// print("==")
                Thread.sleep(500)
                print("\b\b\b\b\b<62%>")// print("=")
                Thread.sleep(400)
                print("\b\b\b\b\b<68%>")// print("=")
                Thread.sleep(1200)
                print("\b\b\b\b\b<80%>")// print("= >\n")
                Thread.sleep(800)
                print("\b\b\b\b\b<85%>")// print("= >\n")
                Thread.sleep(800)
                print("\b\b\b\b\b<92%>")// print("= >\n")
                Thread.sleep(200)
                print("\b\b\b\b\b<94%>")// print("= >\n")
                Thread.sleep(150)
                print("\b\b\b\b\b<95%>")// print("= >\n")
                Thread.sleep(150)
                print("\b\b\b\b\b<97%>")// print("= >\n")
                Thread.sleep(100)
                print("\b\b\b\b\b<100%>")// print("= >\n")
                Thread.sleep(1200)
                gameOn = false
            }
            "explore" -> {
                if (currentRoom.name == "The Dungeon"){
                    Dungeon.explore()
                } else {
                    narrate("There is nothing to explore here")
                }
            }
            "map" ->{
                when (currentPosition){
                    Coordinate(0, 0) -> {
                        println("+------------+------+------+--+\n" +
                                "¦            ¦      ¦      ¦  ¦\n" +
                                "¦      X      \\     +--+   +  ¦\n" +
                                "¦            ¦          /     ¦\n" +
                                "¦            +---   ---+---+--+\n" +
                                "+-----  -----+             ¦\n" +
                                "¦             /            ¦\n" +
                                "+--------xxx-+-------------+\n")
                    }
                    Coordinate(1, 0) -> {
                        println("+------------+------+------+--+\n" +
                                "¦            ¦      ¦      ¦  ¦\n" +
                                "¦             \\ X   +--+   +  ¦\n" +
                                "¦            ¦          /     ¦\n" +
                                "¦            +---   ---+---+--+\n" +
                                "+-----  -----+             ¦\n" +
                                "¦             /            ¦\n" +
                                "+--------xxx-+-------------+\n")
                    }
                    Coordinate(2, 0) -> {
                        println("+------------+------+------+--+\n" +
                                "¦            ¦      ¦      ¦  ¦\n" +
                                "¦             \\     +--+   +  ¦\n" +
                                "¦            ¦          / X   ¦\n" +
                                "¦            +---   ---+---+--+\n" +
                                "+-----  -----+             ¦\n" +
                                "¦             /            ¦\n" +
                                "+--------xxx-+-------------+\n")
                    }
                    Coordinate(0, 1) -> {
                        println("+------------+------+------+--+\n" +
                                "¦            ¦      ¦      ¦  ¦\n" +
                                "¦             \\     +--+   +  ¦\n" +
                                "¦            ¦          /     ¦\n" +
                                "¦            +---   ---+---+--+\n" +
                                "+-----  -----+             ¦\n" +
                                "¦  X          /            ¦\n" +
                                "+--------xxx-+-------------+\n")
                    }
                    Coordinate(1, 1) -> {
                        println("+------------+------+------+--+\n" +
                                "¦            ¦      ¦      ¦  ¦\n" +
                                "¦             \\     +--+   +  ¦\n" +
                                "¦            ¦          /     ¦\n" +
                                "¦            +---   ---+---+--+\n" +
                                "+-----  -----+             ¦\n" +
                                "¦             /       X    ¦\n" +
                                "+--------xxx-+-------------+\n")
                    }
                    else -> {
                        println("You are lost...")
                    }
                }
            }
            "ring" -> {
                if (currentRoom.name == "The Town Square"){
                    TownSquare.ringBell()
                } else {
                    narrate("There is no bell to ring in here.")
                }
            }
            "mood" -> changeNarratorMood()
            else -> narrate("I'm not sure what you're trying to do")
        }
    }
}

var narrationModifier: (String) -> String = { it }

inline fun narrate(
    message: String,
    modifier: (String) -> String = { it }
) {
    println(narrationModifier(message))
}

fun changeNarratorMood() {
    val mood: String
    val modifier: (String) -> String
    when (Random.nextInt(1..4)) {
        1 -> {
            mood = "loud"
            modifier = { message ->
                val numExclamationPoints = 3
                message.uppercase() + "!".repeat(numExclamationPoints)
            }
        }

        2 -> {
            mood = "tired"
            modifier = { message ->
                message.lowercase().replace(" ", "... ")
            }
        }

        3 -> {
            mood = "unsure"
            modifier = { message ->
                "$message?"
            }
        }

        else -> {
            mood = "professional"
            modifier = { message ->
                "$message."
            }
        }
    }

    narrationModifier = modifier
    narrate("The narrator begins to feel $mood")
}

fun printIsSourceOfBlessings(any: Any) {
    val isSourceOfBlessings: Boolean = if (any is Player) {
        any.title == "The Blessed"
    } else if (any is Room) {
        (any as Room).name == "The Fount of Blessings" // привидение типа (Небезопасно ClassCastException)
    } else{
        false
    }
    println("$any is a source of blessings: $isSourceOfBlessings")
}

// перегрузка операторов
/*
+ plus
++ inc
+= plusAssign
- minus
-- dec
-= minusAssign
* times
/ div
== equals
> compareTo
[] get
.. rangeTo - Создает объект, представляющий интервал
in contains - Возвращает true, если объект присутствует в коллекции
() invoke - Выполняет функцию, как если бы значение было лямбда-выражением
 */

// Object и классы внутри
/*
Синглтон (singleton) - один объект, существующий на протяжении всего времени работы программы. Создаются через object
1. Объявление объектов - полезны для организации и управления состоянием на протяжении всей работы программы
Просто объявить object и имя класса и обратиться к нему ради создания. Не требует конструктора, только init{}
2. Объекты-выражения - объект класса, немного отличающегося от существующего, и используется лишь однажды

3. Объекты-компаньоны - если мы хотим добавить поведение в класс, чтобы можно было обращаться к нему и через объекты, и
без них. Объявляются внутри другого класса через 'companion' и только один. Класс с объектом-компаньоном может вести
себя как и обычный класс, так и как объект класса.

Вложенные классы - классы, объявленные внутри других классов


Класс данных - спроектированы специально для хранения данных и предлагают много возможностей для работы с ними
Создаются с помощью ключевого слово data. Equals, toString и hashCode переопределены для работы с данными.
Классы данных поддерживают копирование объектов с изменением в них значений

Деструктуризация - классы данных автоматически её поддерживают
class PlayerScore(val experience: Int, val level: Int) {
    operator fun component1() = experience
    operator fun component2() = level
}

fun main(){
    val (experience, level) = PlayerScore(1250, 5)
}

Ограничения классов данных:
должны иметь главный конструктор хотя бы с одним параметром
требуют, чтобы каждый параметр главного конструктора имел пометку var или val
не могут быть объявлены с ключевыми словами abstract, open, sealed, inner
 */

// НАСЛЕДОВАНИЕ
/*
Наследование происходит через ':'
class TownSquare : Room("The town square"){

для наследования класс должен иметь ключевое слово open и для перезаписи его полей они тоже должны быть open
при переписывании в подкассе мы должен использовать слово override для поля (все последующие будут по умолчанию открыты)

protected - делает поле открытым только для наследников

Ссылка суперкласса может поддерживаться всеми подклассам, но мы можем использовать только поля суперкласса.

final - запрещает переопределние

Привидение - использование объекта как другой тип. Может породить ClassCastException при несовместимости типов, не
путать с преобразованием. Для безопасной работы требуется использовать умное привидение:
1. Проверять тип данных перед привидением
2. Оператор безопасного привидения as?, который возвращает null
 */

// ООП
/*
public - доступно вне своей области (доступен по умолчанию)
private - доступный только внутри совей области
protected - доступен наследникам
internal - Функция или свойство будут доступны внутри модуля

Не существует default (внутри пакета)

Для каждого атрибута котлин генерирует до трех компонентов: поле, геттер и при необходимости сеттер. Поле - место, где
хранятся данные. Объявить поле в классе нельзя. Котлин инкапсулирует поля, защищая данные и открывая к ним доступ через
геттеры и сеттеры. Сеттеры генерируется только для изменяемых полей (var). Хоть это и генерируется автоматом, мы можем
самостоятельно их настроить. Для этого следует написать пользовательские get- и set-методы:
val name = "madrigal"
    get() = field.replaceFirstChar { it.uppercase() }

мы можем в любой момент ограничить видимость к атрибуту добавив к его геттеру или сеттеру модификаторы доступа

Для каждого атрибута создаётся поле за исключением вычисляемых свойств, для которых геттер (и сеттер, если свойство
является var) переопределяется без использования поля. В таких случаях Kotlin не генерирует поле.

val title: String
        get() = when {
            name.all { it.isDigit() } -> "The Identifiable"
            name.none { it.isLetter() } -> "The Witness Protection Member"
            name.count { it.lowercase() in "aeiou" } > 4 -> "The Master of Vowels"
            else -> "The Renowned Hero"
        }


Конструкторы - аттрибуты указываются в скобках после имени класса
class Player(
    initialName: String,
    val homeTown: String,
    var healthPoints: Int,
    val isImmortal: Boolean
)
Конструкторы бывают двух видов: главные и дополнительные. Конструктор, который мы определяли выше, — главный.
Дополнительный конструктор должен вызывать либо главный конструктор, передавая ему все требуемые аргументы, либо другой
дополнительный конструктор, который следует тому же правилу

constructor(name: String, homeTown: String) : this(
        initialName = name,
        homeTown = homeTown,
        healthPoints = 100,
        isImmortal = false
    ) {
        if (name.equals("Jason", ignoreCase = true)) {
            healthPoints = 500
        }
    }
this в данном случае ссылается на экземпляр класса, для которого объявлен конструктор.  this вызывает другой
конструктор, определенный внутри класса, — главный конструктор.

Блок инициализации - блок кода, который срабатывает до конструктора. Нужен для проверки перед созданием объекта
init{...}

Поздняя инициализация - объект, должен быть инициализирован позже при работе программы
lateinit var opponentName: String
Можно использовать с переменными верхнего уровня, НО с особой осторожностью.
Используется ТОЛЬКО с var полями

Отложенная (lazy) инициализация - если инициализация требует много ресурсов или поле не требует мгновенной готовности,
его можно отложить до первого обращения к нему.
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
*/

// ФУНКЦИОНАЛЬНОЕ ПРОГРАММИРОВАНИЕ
/*
Функции, которые получают функцию как параметр или возвращают ее, называются функциями высшего порядка

Первая категория функций в функциональном программировании — преобразователи
Функция-преобразователь перебирает элементы коллекции и изменяет каждый из них с помощью функции преобразования,
заданной в аргументе

Наиболее часто используются два преобразователя — map и flatMap

map:
Функция-преобразователь map перебирает элементы коллекции, для которой вызвана, и к каждому применяет функцию
преобразования. Результатом является коллекция преобразованных элементов

flatMap

ВТОРАЯ КАТЕГОРИЯ - фильтры
принимают функцию, которая определяет параметры элемента и отсекает неподходящие элементы.
take — отбрасывает все элементы свыше заданного количества
Похожая функция drop отбрасывает заданное количество элементов от начала коллекции.
filter - получает функцию-предикат, которая проверяет каждый элемент на соответствие условию и возвращает логическое
значение.  Если значение предиката — истина, то элемент будет добавлен в новую коллекцию, возвращаемую filter. Если
значение предиката — ложь, то элемент не войдет в новую коллекцию.

Третья категория функций, используемых вфункциональном программировании, — комбинаторы
Функции-комбинаторы берут несколько коллекций и объединяют их в одну новую.
zip - При слиянии двух списков вызовом zip элементы этих списков объединяются в порядке их перечисления

мы познакомили вас с типами коллекций List, Set и Map. Эти коллекции называются готовыми (eager). При создании
экземпляра любого из этих типов он сразу содержит все значения элементов коллекции и предоставляет доступ к ним.

Есть и другой вид коллекций — отложенные (lazy) означает, что значение не создается до моментапервого обращения к нему

В Kotlin имеется встроенный тип отложенной коллекции Sequence (последовательность)
Последовательности не поддерживают обращение к содержимому по индексам и не отслеживают свой размер

Для последовательности вы объявляете функцию-итератор, которая вызывается каждый раз, когда потребуется новое значение.
generateSequence - Она принимает начальное значение, которое станет отправной точкой для последовательности

профилирование - Kotlin предлагает полезные функции для профилирования производительности кода: measureNanoTime
и measureTimeInMillis. Обе функции принимают лямбда-функцию как аргумент и измеряют скорость выполнения кода внутри
лямбда-функции. measureNanoTime возвращает время в наносекундах, а measureTimeInMillis возвращает время
в миллисекундах.

агрегирование данных - агрегатные функции,  сводящие все содержимое коллекции в одно значение.
Из этой группы наиболее известна функция reduce, но есть и похожая функция — fold.

reduce получает лямбда-выражение, которое имеет два параметра — аккумулятор (текущий накапливаемый результат) и
следующее значение в коллекции.

vararg - public fun <T> listOf(vararg elements: T): List<T> =


Функции области видимости (scope functions) универсальные вспомогательные функции из стандартной библиотеки Kotlin,
которые помогают писать более выразительный и компактный код

apply -Ее можно считать функцией настройки: она позволяет вызвать несколько функций для объектаполучателя и настроить
его для дальнейшего использования. После применения лямбда-выражения apply возвращает настроенный получатель.
val guestList = mutableSetOf<String>().apply{
        if(isHappyHour) "Sidney"
        if(isOpenMicNight) "Janet"
        if(isAfterMidnight) "Jamie"
        val a = "Johny"
        if (contains("Janet") || contains("Jamie")) { add(a) }
    }.toMutableSet()
    // Такое поведение иногда называют ограничением относительной области видимости (relative scoping)


let - определяет переменную в области видимости, от лямбда-выражением, и передает имя получателя в аргументе.
val patrons = listOf<String>("Johny", "Bill", "Michael", "William", "Sophia")
    val greetings = patrons.first().let{
        "$it walks over to you and says hello"
    }

    val greet = "${patrons.first()} walks over to you and says hello. "

    val greet2 = patrons.firstOrNull()?.let { "$it walks over to you and says hello" }
можно реализовать null безопасность


run - функция run ограничивает относительную область видимости, но возвращает результат лямбда-выражения вместо имени
самого получателя, как let
val patrons = mutableListOf<String>("Johny", "Bill", "Michael", "William", "Sophia")

    val greeter = patrons.run{
        shuffle()
        "${first()} is currently approaches you"
    }


with - В отличие от функций обратного вызова, о которых мы
рассказали ранее, with требует, чтобы аргумент передавался в первом параметре
val greeter = with(patrons.random()){ while (length > 20) random() }


also - Как и let, функция also передает имя получателя, для которого она вызывается, в аргументе лямбда-выражения.
Однако у let и also есть важное различие: also возвращает имя получателя вместо лямбда-результата, как и функция apply.

File("file.txt").also{print(it.name)}.readLines().also { fileContent = it }


takeIf - она вычисляет условие, или предикат, заданное
лямбда-выражением, которое возвращает истинное или ложное значение. Если
условие истинно, takeIf возвращает имя получателя. Если условие ложно, она
вернет null.

«Вернуть значение, если условие истинно» — takeIf.
«Вернуть значение, кроме случая, когда условие истинно» — takeUnless.
 */

// Ассоциативный массив, хэш-таблицы, словари, map
/*
Хранит данные парой ключ-значение, вместо обращения по индексу, мы обращаемся к элементу по ключу

Ключи - уникальны (к одному ключу привязано ТОЛЬКО одно значение)
Значение могут быть любыми (подходить по типу)

имя = mapOf(
ключ to значение
ключ.to(значение) // устаревший вариант
Pair(ключ, значение)
...
)

to - инфиксная функция (вызывается без точки и скобок). Преобразует значения слева и справа в пару ключ-значение

Обращение - имяСловаря[имяКлюча], имяСловаря.getValue(имяКлюча), getOrDefault(имяКлюча, дефолтноеЗначение),
getOrElse(имяКлюча) {лямбда, если ключа нет}

mutableMap - изменяемая

Добавление элементов:
имяСловаря += ключ to значение

= - Добавляет или обновляет значение для указанного ключа
patronGold["Mordoc"] = 5.0

+= - Добавляет или обновляет одну, или несколько записей в зависимости от операнда справа, который может быть записью
или ассоциативным массивом

patronGold += "Eli" to 5.0
patronGold += mapOf(
 "Eli" to 7.0,
 "Mordoc" to 1.0,
 "Sophie" to 4.5
)

.put - Добавляет или обновляет значение для указанного ключа (для одного ключа)
putAll - Добавляет все пары «ключ — значение», переданные в аргументе

.getOrPut - Добавляет запись с указанным ключом, если она не существует, и возвращает результат; в противном случае
возвращает существующее значение

.remove - Удаляет запись и возвращает значение

-= - Удаляет одну или несколько записей
patronGold -= listOf("Mordoc", "Sophie")

clear - Удаляет все записи

.toMap() - преобразует коллекцию в словарь. ДОСТУПНА ТОЛЬКО для списков, содержащих Pair. Можно вызвать ТОЛЬКО для
List<Pair<String, Double>>, НО не для List<String>
 */

// ПРИМИТИВНЫЕ МАССИВЫ
/*
ТЕ САМЫЕ МАССИВЫ ИЗ JAVA!!!!

val playerAges: IntArray = intArrayOf(1,23,34,45,3456436)

.toIntArray()

IntArray intArrayOf
DoubleArray doubleArrayOf
LongArray longArrayOf
ShortArray shortArrayOf
ByteArray byteArrayOf
FloatArray floatArrayOf
BooleanArray booleanArrayOf
Array arrayOf
Array компилируется в примитивный массив, способный хранить элементы любого ссылочного типа

перебор элементов:
testMap.forEach { (key, value) -> println("$key $value") }
testMap.forEach { println("${it.key} ${it.value}") }
 */

// МНОЖЕТСВА SET
/*
бывают изменяемыми, имеет ТОЛЬКО уникальыне элементы, неупорядоченный

Проверка нахождения:
1) имяКоллекции.contains(элемент)
2) элемент in имяКоллекции

Нельзя обращаться по индексу через [], но можно через .elementAt
она последовательно перебирает его элементы, пока не достигнет заданного индекса
в большом множестве обращение к элементу с большим индексом медленнее, чем обращение по индексу в списке

не имеет функции добавления по индексу

проверка присутствия элемента выполняется очень быстро независимо от размера множества. Во внутренней реализации Set
использует внутреннее упорядочение элементов, которое позволяет мгновенно находить элементы.

MutableSet - изменяемое множество

add(element) - Добавляет элемент в множество
addAll - Добавляет все элементы другой коллекции в множество
+= - Добавляет элемент или коллекцию элементов в множество
-= - Удаляет элемент или коллекцию элементов из множества
remove - Удаляет элемент из множества
removeAll - Удаляет из множества все элементы, перечисленные в другой коллекции
clear - Удаляет все элементы из множества

мы можем менять коллекции с помощью тех же методово (лист -> множество и т.д.)
 */

//РАБОТА С ФАЙЛАМИ
/*
Открыть файл -> прочитать содержимое -> создать неизменяемый список -> разделить все элементы по символу \n и добавить в список
private val menuData = File("data/tavern-menu-data.txt").readText().split("\n")
 */

//Список
/*
Список - коллекция значений, которая может содержать дубликаты.
val patrons: List<String> = listOf("Eli", "Mordoc", "Sophie")
val имя: List<тип> = listOf(перечисляем элементы)

Чтобы создать коллекцию нужно выполнять 2 действия: создать коллекцию, добавить содержимое
listOf - функция, которая одновременно создаёт список и добавляет указанные значения

обращение к элементам происходит через: [], метод get

.first() .last() - обращаются к первому или последнему элементу списка

.getOrElse - либо возвращает элемент по индексу, либо выполняет лямбду
.getOrNull - его можно использовать и с разными защитами null
.firstOrNull .lastOrNull
.contains - проверяет наличие элемента в списке
.containsAll(listOf(......)) - проверяет наличие нескольких элементов наличие

List - неизменяемый список, после создания нельзя изменить элементы
listOf - возвращает неизменяемый список
Список доступный для чтения

MutableList - изменяемый список
mutableListOf - создает измн список

.toList .toMutableList - для переключения между типами списков

add(index, element) - добавление по индексу (не замена)
+= - добавления элемента или коллекцию в конец списка
-= - удаления элемент или коллекцию
.clear - очистка
removeAll(listOf(...)) - удаляет несколько элементов

.distinct() - удаляет дубликаты в списке

foreach в kotlin:
for(элемент in список){}
список.forEach{эл -> *действия над элементом*}

for(i in начало..конец step шаг){...}
for(i in 0 until список.size){}
for(i in список.size downTo 0


конструктор List:
Конструктор List получает два аргумента: размер (тип Int) и функцию инициализации для заполнения списка.

ДЕСТРУКТОРИЗАЦИЯ:
val (type, name, price) = menuData[index].split(',') - Это объявление присваивает первые три элемента списка,
возвращенного функцией split, строковым значениям с именами type, name и price

Списки, которые содержат изменяемые списки, можно изменять
val x = listOf(mutableListOf(1, 2, 3))
val y = listOf(mutableListOf(1, 2, 3))
x[0].add(4)

var myList: List<Int> = listOf(1, 2, 3)
    (myList as MutableList)[2] = 1000
    println(myList)

В этом примере myList был приведен к типу MutableList, то есть компилятору было приказано рассматривать myList как
изменяемый список, несмотря на то что он был создан с помощью listOf.

List в Kotlin не является строго неизменяемым
Мы сами выбираете, использовать ли его в неизменяемой манере
 */

// АНОНИМНЫЕ ФУНКЦИИ
/*
inline - позволяет заранее скомпилировать функцию, делает её встраиваемой. Оптимизирует программу, т.к. функция уже
скомпилирована.

текст.count(par -> (булевое выражение) ) - (par - каждый символ в строке) считается количество нужных нам букв, если
булевое выражение верно

Лямбда-выражение - НЕЯВНО возвращает результат ПОСЛЕДНЕЙ строки. Неявный возврат
return запрещён - чтобы избежать неоднозначности.

Параметры лямбд пишутся путём перечисления их типов (НЕ ИМЁН)

val имяЛямбды: (типПараметров...) -> ВозврТип = {имяПарметров... -> действия}
val имяЛямбды -> {пар: тип -> действия}

Если в лямбде принимается ТОЛЬКО один параметр, мы можем его не указывать и называть как it
 */

// ИСКЛЮЧЕНИЯ
/*
throw - выдача исключения.
IllegalArgumentException - программа получила ввод, который считает недопустимым

Выражение try-catch - работает также, как и другие выражения (when)

функция проверки предусловий - Они позволяют использовать встроенную функцию для выдачи исключения с произвольным
сообщением. Выдастся специальная версия IllegalArgumentException
require(условие){текст исключения}

существует 6 функций проверки предусловий:
check - Выдает IllegalStateException, если аргумент — false
checkNotNull - Выдает IllegalStateException, если аргумент — null. В противном случае возвращает полученное значение
require - Выдает IllegalArgumentException, если аргумент — false
requireNotNull - Выдает IllegalArgumentException,если аргумент—null. В противном случае возвращает полученное значение
error - Выдает IllegalArgumentException с заданным сообщением, если аргумент — null. В противном случае возвращает
полученное значениe
assert - Выдает AssertionError, если аргумент — false и на этапе компиляции установлен флаг, разрешающий проверку
тестовых утверждений
 */

// NULL
/*
Типы данных могут быть 2 вида: nullable and non-nullable
К non-nullable невозможно присвоить значение null
String? - nullable тип данных (String и String? - 2 разных типа данных)

В nullable типах данных нельзя просто вызывать функции:
1) проверка null в операторе if. Самый простой, kotlin автоматически приводит в non-nullable тип (smart-casting)
Небезопасно, при использовании переменных уровня файла, т.к. значение может измениться перед использованием (можно
избежать создав временную копию переменной в нашей области видимости). Код может стать громоздким.

2) Оператор безопасного вызова '?.'. Перед вызовом функции, компилятор проверит переменную на значение null. Если
оно есть, то он пропустит вызов функции. Гарантирует, что функция будет вызвана, ТОЛЬКО если переменная имеет значение3)let - создаёт новую область видимости
nullable_variable?.let{ действия }

4)оператор объединения с null (Элвис оператор) - позволяет использовать резервное значение при обнаружении null '?:'

5)Оператор утверждения не-null-значения (non-null assertion operator) - !!, обязывает программу вызвать функцию, даже
если будет null. НЕ РЕКОМЕНДУЕТСЯ, ИСПОЛЬЗОВАТЬ В КРАЙНЕМ СЛУЧАЕ, НЕ БЕЗОПАСНО, НЕПРЕДСКАЗУЕМО, МОЖЕТ БЫТЬ ИСКЛЮЧЕНИЕ
 */

// Строки
/*
$имя_переменной - внутри строки называется интерполяция. Строка с этим называется строковым шаблоном
${Действия} - сложный строковый шаблон. Действие обязано после себя возвращать, какое-то значение

raw-string - строка, которая воспринимается буквально.
.trimIndent для удобной записи на новых строчках
.trimMargin - | считается за начало новой строки.

Строка неизменяема. Если мы её изменяем, старая строка удаляется и на её месте создаётся новая.
 */


// TODO( даже в комментариях это будет высвечиваться)
// нужно для предупреждения, что функционал не доделан

// funs
/*
модификаторДоступа fun имяФункции(
параметр1: типДанных
параметр2: типДанных
...
): возвращаемыТипФункции {
действия
}

Если функция имеет только одно выражение, мы можем испльзовать следующий синтаксис:

Unit - означает что функция не возвращает ничего, но в отличие от void может использоваться с обобщениями

можно имена заносить в обратные кавычки и называть как угодно
 */

// when - обработка нескольких условий
/*
when (Аргумент){
условие -> действие
условие, условие -> действие
else -> {блок кода}
}
 */

// if else
/*
=== - Проверяет, что две ссылки указывают на один экземпляр
!== Проверяет, что две ссылки указывают на разные экземпляры
else if (playerLevel in 2..5) - интервалы (от двух до пяти)
 */

/*
var - изменяемая переменная
var имяПеременной: типДанных = значение

Типы данных:
String (строка)
Char (символ)
Boolean (логический)
Int (целочисленный)
Double (с плавающей запятой)
List (список) - Коллекция значений
Set (множество) - Коллекция уникальных значений
Map (ассоциативный массив) - Коллекция пар «ключ — значение»

val - переменная доступная только для чтения.


Константа времени компиляции объявляется вне какой-либо функции
ее значение присваивается во время компиляции (const val ....) const - значение никогда не должно изменяться
могут иметь значение только этих типов данных:
String Float Byte
Int Long Char
Double Short Boolean

 */