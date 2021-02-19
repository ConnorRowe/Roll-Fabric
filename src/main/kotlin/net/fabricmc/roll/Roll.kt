package net.fabricmc.roll

import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.minecraft.server.command.ServerCommandSource

@Suppress("unused")


class Roll : ModInitializer {

    fun init() {
        println("Roll loaded!")
    }

    override fun onInitialize() {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource?>, dedicated: Boolean ->
            RollCommand.register(dispatcher)
        })
    }
}

