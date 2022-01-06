package net.fabricmc.roll

import com.mojang.brigadier.CommandDispatcher
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.fabricmc.roll.config.RollConfig
import net.minecraft.server.command.ServerCommandSource


@Suppress("unused")


class Roll : ModInitializer {

    private val config: RollConfig = AutoConfig.register(
        RollConfig::class.java
    ) { definition: Config?, configClass: Class<RollConfig?>? -> GsonConfigSerializer(definition, configClass) }.config

    override fun onInitialize() {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource?>, _: Boolean ->
            RollCommand.register(dispatcher, config)
        })
    }
}

