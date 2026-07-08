open class Weapon(val name: String, val type: String)

@JvmInline
value class Kilometers(private val kilometers: Double){
    operator fun plus(other: Kilometers) = Kilometers(kilometers+other.kilometers)
}

@JvmInline
value class Miles(private val miles: Double){
    operator fun plus(other: Miles) = Miles(miles+other.miles)

    fun toKilommeters() = miles*1.609
}

fun main(){
    val k: Kilometers = Kilometers(12.0);
}
