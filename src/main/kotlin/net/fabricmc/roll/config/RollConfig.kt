package net.fabricmc.roll.config

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config

@Config(name = "roll")
class RollConfig : ConfigData {
    var player_rgb = "#FFFFFF"
    var player_bold = false
    var player_italic = false
    var rolls_rgb = "#B8B8B8"
    var rolls_bold = false
    var rolls_italic = false
    var roll_num_rgb = "#FFAF00"
    var roll_num_bold = true
    var roll_num_italic = false
    var range_rgb = "#B8B8B8"
    var range_bold = false
    var range_italic = true
}