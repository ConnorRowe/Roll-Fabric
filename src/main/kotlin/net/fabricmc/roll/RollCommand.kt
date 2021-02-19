package net.fabricmc.roll

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.network.MessageType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.LiteralText
import kotlin.random.Random

object RollCommand : Command<Any?> {
    override fun run(context: CommandContext<Any?>?): Int {
        return 0
    }

    fun register(dispatcher: CommandDispatcher<ServerCommandSource?>) {
        dispatcher.register(CommandManager.literal("roll").executes(this::roll))
    }

    private fun roll(ctx: CommandContext<ServerCommandSource?>): Int {
        if (ctx.source?.entity is ServerPlayerEntity) {
            // Gets command source
            val serverCmdSrc: ServerCommandSource = ctx.source as ServerCommandSource
            // Gets player
            val player: ServerPlayerEntity = serverCmdSrc.entity as ServerPlayerEntity
            // Rolls the dice
            val rollString = Random.nextInt(0, 100).toString() + "  ~  (0 - 100)"
            // Builds the message
            val msg = LiteralText(player.displayName.asString() + " rolls " + rollString)
            // Gets iterator for all players
            val playerIter = serverCmdSrc.minecraftServer.playerManager.playerList.iterator()
            // Sends the message to everyone
            for (p in playerIter) {
                p.sendMessage(msg, MessageType.CHAT, player.uuid)
            }
        }
        return 0
    }
}