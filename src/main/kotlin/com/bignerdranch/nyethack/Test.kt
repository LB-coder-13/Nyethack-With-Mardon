import com.bignerdranch.nyethack.Fedora
import com.bignerdranch.nyethack.Gemstones
import com.bignerdranch.nyethack.Loot
import com.bignerdranch.nyethack.LootBox


fun main(){
    var fedoraBox: LootBox<Fedora> = LootBox(Fedora("a generic-looking fedora", 15))
    var lootBox: LootBox<Loot> = LootBox(Gemstones(150))
    lootBox = fedoraBox


    var fedora = Fedora("Hello", 12)
    var loot: Loot = Gemstones( 43)
    loot = fedora
}
