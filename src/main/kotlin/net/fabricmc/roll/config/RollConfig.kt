package net.fabricmc.roll.config

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config

@Config(name = "roll")
class RollConfig : ConfigData {
    var baseFormat = "§r§7"
    var playerFormat = "§r"
    var rollNumberFormat = "§6§l"
    var rollRangeFormat = "§7§o"
}